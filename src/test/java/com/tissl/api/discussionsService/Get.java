package com.rungway.api.discussionsService;

import com.tissl.api.BaseTest;
import com.tissl.api.utils.EndPoint;
import com.tissl.api.utils.JsonKey;
import com.tissl.api.utils.ParameterConstant;
import com.tissl.api.utils.TestData;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class Get extends BaseTest {

    @Test
    public void verifyJsonSchemaForGetDiscussionsByRungwayId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .when()
                .get(EndPoint.DISCUSSIONS_BY_RUNGWAY_ID)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("getDiscussionsByRungwayId.json"))
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyJsonSchemaForGetDiscussionsByRungwayAndDiscussionId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .when()
                .get(EndPoint.DISCUSSIONS_BY_DISCUSSION_ID)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("getDiscussionByDiscussionId.json"))
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetDiscussionsByRungwayIdForValidUser() {
        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .when()
                .get(EndPoint.DISCUSSIONS_BY_RUNGWAY_ID)
                .then()
                .body(keyPresentAtFirstIndex(JsonKey.ID), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.CREATED_AT), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.TITLE), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.BODY), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.ANONYMOUS), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.STATE), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.IS_MINE), is(true))
                .statusCode(200)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetDiscussionsByRungwayIdReturns401ForInvalidRungwayId() {
        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.INVALID_ID)
                .when()
                .get(EndPoint.DISCUSSIONS_BY_RUNGWAY_ID)
                .then()
                .statusCode(401)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetDiscussionsByDiscussionIdForValidUser() {
        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .when()
                .get(EndPoint.DISCUSSIONS_BY_DISCUSSION_ID)
                .then()
                .body(JsonKey.ID, equalTo(TestData.PUBLIC_DISCUSSION_ID))
                .body(keyPresent(JsonKey.CREATED_BY), is(true))
                .body(JsonKey.CREATED_AT, equalTo(TestData.DISCUSSION_CREATED_TIME))
                .body(JsonKey.TITLE, equalTo(TestData.DISCUSSION_TITLE))
                .body(JsonKey.BODY, equalTo(TestData.DISCUSSION_BODY))
                .body(JsonKey.ANONYMOUS, equalTo(false))
                .body(JsonKey.STATE, equalTo(TestData.STATUS_APPROVED))
                .statusCode(200)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetDiscussionsByDiscussionIdReturns404ForInvalidDiscussionId() {
        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.INVALID_ID)
                .when()
                .get(EndPoint.DISCUSSIONS_BY_DISCUSSION_ID)
                .then()
                .statusCode(404)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetDiscussionsByRungwayIdReturns403ForInvalidAccessToken() {

        given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .when()
                .get(EndPoint.DISCUSSIONS_BY_RUNGWAY_ID)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetDiscussionsByDiscussionIdReturns403ForInvalidAccessToken() {

        given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .when()
                .get(EndPoint.DISCUSSIONS_BY_DISCUSSION_ID)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));
    }
}
