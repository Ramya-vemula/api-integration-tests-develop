package com.rungway.api.accountService;

import com.tissl.api.BaseTest;
import com.tissl.api.utils.EndPoint;
import com.tissl.api.utils.JsonKey;
import com.tissl.api.utils.ParameterConstant;
import com.tissl.api.utils.TestData;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class Put extends BaseTest {

    @Test
    public void verifyPutAccountById() {

        Map<String, String> user = new HashMap<>();
        user.put(JsonKey.EMAIL, TestData.EDIT_USER_EMAIL);
        user.put(JsonKey.PASSWORD, TestData.USER_PASSWORD);
        user.put(JsonKey.STATUS, TestData.USER_STATUS_DEACTIVATED);

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.EDIT_USER_ACCOUNT_ID)
                .body(user)
                .when()
                .put(EndPoint.ACCOUNT_BY_ID)
                .then()
                .statusCode(200)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyBadRequestForInvalidPasswordTypes() {
        List<String> invalidPasswords = Arrays.asList(
                "rrr",
                "rrrrrrrr",
                "RRRRRRRR",
                "12345678",
                "!@£$%^&*",
                "RRRrrr12",
                "rrr123@£"
        );

        for (String password : invalidPasswords) {
            Map<String, String> user = new HashMap<>();
            user.put(JsonKey.PASSWORD, password);

            given().auth().oauth2(accessToken)
                    .pathParam(ParameterConstant.ACCOUNT_ID, TestData.EDIT_USER_ACCOUNT_ID)
                    .body(user)
                    .when()
                    .put(EndPoint.ACCOUNT_BY_ID)
                    .then()
                    .statusCode(400)
                    .time(lessThan(TestData.RESPONSE_TIME));
        }
    }

    @Test
    public void verifyBadRequestForInvalidStatus() {
        List<String> invalidStatus = Arrays.asList(
                "active",
                "deactivated",
                "ACTIVE1",
                "DEACTIVATED1"
        );
        for (String status : invalidStatus) {

            Map<String, String> user = new HashMap<>();
            user.put(JsonKey.STATUS, status);

            given().auth().oauth2(accessToken)
                    .pathParam(ParameterConstant.ACCOUNT_ID, TestData.EDIT_USER_ACCOUNT_ID)
                    .body(user)
                    .when()
                    .put(EndPoint.ACCOUNT_BY_ID)
                    .then()
                    .statusCode(400)
                    .time(lessThan(TestData.RESPONSE_TIME));

        }
    }

    @Test
    public void verifyBadRequestUpdatingInvalidEmailAddress() {

        List<String> invalidEmails = Arrays.asList(
                "plainaddress",
                "#@%^%#$@#$@#.com",
                "@example.com",
                "Joe Smith <email@example.com>",
                "email.example.com",
                "email@example@example.com",
                "email@example.com (Joe Smith)"
        );

        for (String email : invalidEmails) {
            Map<String, String> user = new HashMap<>();
            user.put(JsonKey.EMAIL, email);

            given().auth().oauth2(accessToken)
                    .pathParam(ParameterConstant.ACCOUNT_ID, TestData.EDIT_USER_ACCOUNT_ID)
                    .body(user)
                    .when()
                    .put(EndPoint.ACCOUNT_BY_ID)
                    .then()
                    .statusCode(400)
                    .time(lessThan(TestData.RESPONSE_TIME));
        }

    }

    @Test
    public void verifyPutAccountByIdReturns404ForInvalidUser() {

        Map<String, String> user = new HashMap<>();
        user.put(JsonKey.PASSWORD, TestData.USER_PASSWORD);

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.INVALID_ID)
                .body(user)
                .when().put(EndPoint.ACCOUNT_BY_ID)
                .then()
                .statusCode(404)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyPutAccountReturns403ForInvalidAccessToken() {

        Map<String, String> user = new HashMap<>();
        user.put(JsonKey.EMAIL, TestData.USER_EMAIL);
        user.put(JsonKey.PASSWORD, TestData.USER_PASSWORD);
        user.put(JsonKey.STATUS, TestData.USER_STATUS_DEACTIVATED);

        given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.EDIT_USER_ACCOUNT_ID)
                .body(user)
                .when()
                .put(EndPoint.ACCOUNT_BY_ID)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    @Ignore
    public void verifyPutPasswordResetByAccountId() {

        List<String> validPasswords = Arrays.asList(
                "Rungway@2018",
                "Rungway+2018",
                "Rungway=2018",
                "Rungway*2018"
        );

        for (String password : validPasswords) {
            Map<String, String> user = new HashMap<>();
            user.put(JsonKey.PASSWORD, password);

            given().pathParam(ParameterConstant.TOKEN_ID, "validTokenId")
                    .body(user)
                    .when()
                    .put(EndPoint.RESET_PASSWORD_BY_ACCOUNT_ID)
                    .then()
                    .statusCode(200)
                    .time(lessThan(TestData.RESPONSE_TIME));
        }
    }

    @Test
    @Ignore
    public void verifyPutPasswordResetByAccountIdForInvalidPassword() {

        List<String> invalidPasswords = Arrays.asList(
                "~!@#$%^&*()-_=+[]{}|;':,./<>?+`",
                "123456789012345678901234567890",
                "AEIOU",
                "aeiou",
                "AEIOU@2018",
                "aeiou@2018",
                "@@@@2018",
                "Aeio@18"
        );

        for (String password : invalidPasswords) {
            Map<String, String> user = new HashMap<>();
            user.put(JsonKey.PASSWORD, password);

            given().pathParam(ParameterConstant.TOKEN_ID, "validTokenId")
                    .body(user)
                    .when()
                    .put(EndPoint.RESET_PASSWORD_BY_ACCOUNT_ID)
                    .then()
                    .statusCode(400)
                    .time(lessThan(TestData.RESPONSE_TIME));
        }
    }

    @Test
    @Ignore
    public void verifyPutPasswordResetByAccountIdReturns403ForExpiredTokenId() {

        Map<String, String> user = new HashMap<>();
        user.put(JsonKey.PASSWORD, TestData.USER_PASSWORD);

        given().pathParam(ParameterConstant.TOKEN_ID, "expiredTokenId")
                .body(user)
                .when()
                .put(EndPoint.RESET_PASSWORD_BY_ACCOUNT_ID)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    @Ignore
    public void verifyPutPasswordResetByAccountIdReturns404ForInvalidTokenId() {

        Map<String, String> user = new HashMap<>();
        user.put(JsonKey.PASSWORD, TestData.USER_PASSWORD);

        given().pathParam(ParameterConstant.TOKEN_ID, "invalidTokenId")
                .body(user)
                .when()
                .put(EndPoint.RESET_PASSWORD_BY_ACCOUNT_ID)
                .then()
                .statusCode(404)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    @Ignore
    public void verifyPutPasswordResetByAccountIdReturns400ForInvalidPasswordType() {

        Map<String, String> user = new HashMap<>();
        user.put(JsonKey.PASSWORD, "invalidPasswordType");

        given().pathParam(ParameterConstant.TOKEN_ID, "validTokenId")
                .body(user)
                .when()
                .put(EndPoint.RESET_PASSWORD_BY_ACCOUNT_ID)
                .then()
                .statusCode(400)
                .time(lessThan(TestData.RESPONSE_TIME));
    }
}
