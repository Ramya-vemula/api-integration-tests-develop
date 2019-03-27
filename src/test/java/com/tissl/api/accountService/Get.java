package com.rungway.api.accountService;

import com.tissl.api.BaseTest;
import com.tissl.api.utils.EndPoint;
import com.tissl.api.utils.JsonKey;
import com.tissl.api.utils.ParameterConstant;
import com.tissl.api.utils.TestData;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class Get extends BaseTest {

    @Test
    public void verifyJsonSchemaForGetAccounts() {

        given().auth().oauth2(accessToken)
                .when()
                .get(EndPoint.ACCOUNTS)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("getAccountSchema.json"))
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetAccountReturns403ForInvalidAccessToken() {

        given().auth().oauth2(TestData.INVALID_TOKEN)
                .when()
                .get(EndPoint.ACCOUNTS)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyGetAccountByIdReturns403ForInvalidAccessToken() {

        given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.USER_ACCOUNT_ID)
                .when()
                .get(EndPoint.ACCOUNT_BY_ID)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyGetAccountByIdForValidUser() {
        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.USER_ACCOUNT_ID)
                .when()
                .get(EndPoint.ACCOUNT_BY_ID)
                .then()
                .body(JsonKey.EMAIL, equalTo(TestData.USER_EMAIL))
                .body(JsonKey.STATUS, equalTo(TestData.USER_STATUS_ACTIVE))
                .body(JsonKey.ACCOUNT_ID, equalTo(TestData.USER_ACCOUNT_ID))
                .statusCode(200)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetAccountByIdReturns404ForInvalidUser() {
        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.INVALID_ID)
                .when()
                .get(EndPoint.ACCOUNT_BY_ID)
                .then()
                .statusCode(404)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyGetPasswordResetByTokenIdReturns404ForInvalidTokenId() {
        given().pathParam(ParameterConstant.ACCOUNT_ID, TestData.USER_ACCOUNT_ID)
                .pathParam(ParameterConstant.RESET_TOKEN_ID, TestData.INVALID_ID)
                .when()
                .get(EndPoint.RESET_PASSWORD_BY_TOKEN_ID)
                .then()
                .statusCode(404)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyGetPasswordResetByTokenIdReturns404ForInvalidAccountId() {
        given().pathParam(ParameterConstant.ACCOUNT_ID, TestData.INVALID_ID)
                .pathParam(ParameterConstant.RESET_TOKEN_ID, "validResetTokenId")
                .when()
                .get(EndPoint.RESET_PASSWORD_BY_TOKEN_ID)
                .then()
                .statusCode(404)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    @Ignore
    public void verifyGetPasswordResetByTokenIdReturns204ForValidTokenId() {
        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.USER_ACCOUNT_ID)
                .pathParam(ParameterConstant.RESET_TOKEN_ID, "validResetTokenId")
                .when()
                .get(EndPoint.RESET_PASSWORD_BY_TOKEN_ID)
                .then()
                .statusCode(204)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    @Ignore
    public void verifyGetPasswordResetByTokenIdReturns403ForExpiredTokenId() {
        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.USER_ACCOUNT_ID)
                .pathParam(ParameterConstant.RESET_TOKEN_ID, "expiredResetTokenId")
                .when()
                .get(EndPoint.RESET_PASSWORD_BY_TOKEN_ID)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

}
