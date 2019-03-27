package com.tissl.api.rungwayService;

import com.tissl.api.BaseTest;
import com.tissl.api.utils.EndPoint;
import com.tissl.api.utils.JsonKey;
import com.tissl.api.utils.ParameterConstant;
import com.tissl.api.utils.TestData;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class Get extends BaseTest {

    @Test
    public void verifyJsonSchemaForGetRungways() {

        given().auth().oauth2(accessToken)
                .get(EndPoint.RUNGWAYS)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("rungways.json"))
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyRungwaysAccountDetailsAreCorrect() {
        given().auth().oauth2(accessToken)
                .get(EndPoint.RUNGWAYS)
                .then()
                .body(keyPresentAtFirstIndex(JsonKey.ID), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.Name), is(true))
                .statusCode(200)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetRungwaysByIdRequest() {
        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .get(EndPoint.RUNGWAY_BY_ID)
                .then()
                .body(JsonKey.ID, equalTo(TestData.RUNGWAY_ID))
                .body(JsonKey.Name, equalTo(TestData.RUNGWAY_NAME))
                .statusCode(200)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verify401IsReturnedForInvalidRungwayId() {
        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.INVALID_ID)
                .get(EndPoint.RUNGWAY_BY_ID)
                .then()
                .statusCode(401)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyAccessListIsReturnedForValidRungwayId() {
        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .get(EndPoint.GET_ACCESS_LIST_BY_RUNGWAY_ID)
                .then()
                .body(keyPresentAtFirstIndex(JsonKey.ACCOUNT_ID), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.RUNGWAY_ID), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.STATUS), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.APP_ROLE), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.ADMIN_ROLE), is(true))
                .statusCode(200)
                .time(lessThan(TestData.RESPONSE_TIME));

    }


    @Test
    public void verify401IsReturnedForInvalidRungwayIdAccessList() {
        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.INVALID_ID)
                .get(EndPoint.GET_ACCESS_LIST_BY_RUNGWAY_ID)
                .then()
                .statusCode(401)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetRungwaysByAccountId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.USER_ACCOUNT_ID)
                .get(EndPoint.GET_RUNGWAYS_BY_ACCOUNT_ID)
                .then()
                .body(jsonPathAtIndex(JsonKey.RUNGWAY + "." + JsonKey.ID, 0), equalTo(TestData.RUNGWAY_ID))
                .body(jsonPathAtIndex(JsonKey.RUNGWAY + "." + JsonKey.Name, 0), equalTo(TestData.RUNGWAY_NAME))
                .body(jsonPathAtIndex(JsonKey.ACCESS + "." + JsonKey.ACCOUNT_ID, 0), equalTo(TestData.USER_ACCOUNT_ID))
                .body(jsonPathAtIndex(JsonKey.ACCESS + "." + JsonKey.RUNGWAY_ID, 0), equalTo(TestData.RUNGWAY_ID))
                .body(jsonPathAtIndex(JsonKey.ACCESS + "." + JsonKey.STATUS, 0), equalTo(TestData.USER_STATUS_ACTIVE))
                .body(jsonPathAtIndex(JsonKey.ACCESS + "." + JsonKey.APP_ROLE, 0), equalTo(TestData.APP_ROLE_RW))
                .body(jsonPathAtIndex(JsonKey.ACCESS + "." + JsonKey.ADMIN_ROLE, 0), equalTo(TestData.ROLE_ADMIN))
                .statusCode(200)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test(enabled = false)
    //TODO: Not Implemented
    public void verify404IsReturnedForInvalidAccountId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.INVALID_ID)
                .get(EndPoint.GET_RUNGWAYS_BY_ACCOUNT_ID)
                .then()
                .statusCode(404)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetRungwaysReturns403ForInvalidAccessToken() {

        given().auth().oauth2(TestData.INVALID_TOKEN)
                .when()
                .get(EndPoint.RUNGWAYS)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyGetRungwaysByIdReturns403ForInvalidAccessToken() {

        given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .when()
                .get(EndPoint.RUNGWAY_BY_ID)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyGetRungwaysByAccountIdReturns403ForInvalidAccessToken() {

        given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.USER_ACCOUNT_ID)
                .when()
                .get(EndPoint.GET_RUNGWAYS_BY_ACCOUNT_ID)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyGetAccessListByRungwayIdReturns403ForInvalidAccessToken() {

        given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .when()
                .get(EndPoint.GET_ACCESS_LIST_BY_RUNGWAY_ID)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));

    }


}
