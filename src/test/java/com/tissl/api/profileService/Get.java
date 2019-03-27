package com.rungway.api.profileService;

import com.tissl.api.BaseTest;
import com.tissl.api.utils.EndPoint;
import com.tissl.api.utils.JsonKey;
import com.tissl.api.utils.ParameterConstant;
import com.tissl.api.utils.TestData;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class Get extends BaseTest {

    @Test
    public void verifyJsonSchemaForGetProfile() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.USER_ACCOUNT_ID)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .get(EndPoint.PROFILE_BY_RUNGWAY_ID_AND_ACCOUNT_ID)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("profileService.json"))
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetProfileForValidUser() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.USER_ACCOUNT_ID)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .get(EndPoint.PROFILE_BY_RUNGWAY_ID_AND_ACCOUNT_ID)
                .then()
                .body(JsonKey.ID, equalTo("a2a29ab7-f8e6-4886-a2ee-542ed3a4e749"))
                .body(JsonKey.FIRST_NAME, equalTo("Rohith"))
                .body(JsonKey.LAST_NAME, equalTo("Vitta"))
                .body(JsonKey.GENDER, equalTo("MALE"))
                .body(JsonKey.BIRTH_YEAR, equalTo(0))
                .body(JsonKey.AVATAR, equalTo("https://www.gravatar.com/avatar/00000000000000000000000000000000"))
                .body(JsonKey.DEPARTMENT, equalTo("QA"))
                .body(JsonKey.REGION, equalTo("London"))
                .body(JsonKey.JOB_TITLE, equalTo("QA"))
                .statusCode(200)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verify404IsReturnedForInvalidAccountId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.INVALID_ID)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .get(EndPoint.PROFILE_BY_RUNGWAY_ID_AND_ACCOUNT_ID)
                .then()
                .statusCode(404)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verify401IsReturnedForInvalidRungwayId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.USER_ACCOUNT_ID)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.INVALID_ID)
                .get(EndPoint.PROFILE_BY_RUNGWAY_ID_AND_ACCOUNT_ID)
                .then()
                .statusCode(401)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetProfileReturns403ForInvalidAccessToken() {

        given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.USER_ACCOUNT_ID)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.INVALID_ID)
                .when()
                .get(EndPoint.PROFILE_BY_RUNGWAY_ID_AND_ACCOUNT_ID)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));

    }


}
