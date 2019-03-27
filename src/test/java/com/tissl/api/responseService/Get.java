package com.rungway.api.responseService;

import com.tissl.api.BaseTest;
import com.tissl.api.utils.*;
import org.tissl.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class Get extends BaseTest {

    @Test
    public void verifyJsonSchemaForGetResponsesByDiscussionId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .when()
                .get(EndPoint.RESPONSES_BY_DISCUSSION_ID)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("responses.json"))
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetResponsesByDiscussionId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .when()
                .get(EndPoint.RESPONSES_BY_DISCUSSION_ID)
                .then()
                .body(keyPresentAtFirstIndex(JsonKey.CREATED_BY), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.CREATED_AT), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.BODY), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.ANONYMOUS), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.NUM_COMMENTS), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.PRIVATE), is(true))
                .statusCode(200)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetResponsesByDiscussionIdReturns404ForInvalidDiscussionId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.INVALID_ID)
                .when()
                .get(EndPoint.RESPONSES_BY_DISCUSSION_ID)
                .then()
                .statusCode(404)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyGetResponsesByDiscussionIdReturns403ForInvalidAccessToken() {

        given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.INVALID_ID)
                .when()
                .get(EndPoint.RESPONSES_BY_DISCUSSION_ID)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyGetResponsesByResponseId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .pathParam(ParameterConstant.RESPONSE_ID, TestData.RESPONSE_ID)
                .when()
                .get(EndPoint.RESPONSE_BY_RESPONSE_ID)
                .then()
                .body(JsonKey.CREATED_BY, equalTo(TestData.RESPONSE_CREATED_BY))
                .body(JsonKey.CREATED_AT, equalTo(TestData.RESPONSE_CREATED_TIME))
                .body(JsonKey.BODY, equalTo(TestData.RESPONSE_BODY))
                .body(JsonKey.ANONYMOUS, equalTo(false))
                .body(JsonKey.PRIVATE, equalTo(false))
                .statusCode(200)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetResponsesByDiscussionIdReturns404ForInvalidResponseId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .pathParam(ParameterConstant.RESPONSE_ID, TestData.INVALID_ID)
                .when()
                .get(EndPoint.RESPONSE_BY_RESPONSE_ID)
                .then()
                .statusCode(404)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyGetResponsesByResponseIdReturns403ForInvalidAccessToken() {

        given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .pathParam(ParameterConstant.RESPONSE_ID, TestData.RESPONSE_ID)
                .when()
                .get(EndPoint.RESPONSE_BY_RESPONSE_ID)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));

    }


    @Test
    public void verifyGetCommentByResponseIdForValidUser() {

        String jsonBasePath = "_embedded.results[0].";
        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .pathParam(ParameterConstant.RESPONSE_ID, TestData.RESPONSE_ID)
                .when()
                .get(EndPoint.COMMENTS_BY_RESPONSE_ID)
                .then()
                .body(jsonBasePath + JsonKey.CREATED_BY, equalTo(TestData.COMMENT_AUTHOR_ID))
                .body(jsonBasePath + JsonKey.PARENT_ID, equalTo(TestData.RESPONSE_ID))
                .body(jsonBasePath + JsonKey.CREATED_AT, equalTo(TestData.COMMENT_CREATED_TIME))
                .body(jsonBasePath + JsonKey.BODY, equalTo(TestData.COMMENT_BODY))
                .body(jsonBasePath + JsonKey.ANONYMOUS, equalTo(false))
                .body(jsonBasePath + JsonKey.PRIVATE, equalTo(false))
                .statusCode(200)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetCommentByResponseIdReturns404ForInvalidResponseId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .pathParam(ParameterConstant.RESPONSE_ID, TestData.INVALID_ID)
                .when()
                .get(EndPoint.COMMENTS_BY_RESPONSE_ID)
                .then()
                .statusCode(404)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyGetCommentsByResponseIdReturns403ForInvalidAccessToken() {

        given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .pathParam(ParameterConstant.RESPONSE_ID, TestData.RESPONSE_ID)
                .when()
                .get(EndPoint.COMMENTS_BY_RESPONSE_ID)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetResponsesByRungwayId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .when()
                .get(EndPoint.RESPONSES_BY_RUNGWAY_ID)
                .then()
                .body(keyPresentAtFirstIndex(JsonKey.BODY), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.ID), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.DISCUSSION_ID), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.CREATED_AT), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.MODIFIED_AT), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.ANONYMOUS), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.STATE), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.EDITABLE), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.PRIVATE), is(true))
                .body(keyPresentAtFirstIndex(JsonKey.IS_MINE), is(true))
                .statusCode(200)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test(enabled = false)
    public void verifyGetResponsesByRungwayIdReturns401ForNonAdminUser() {

        //TODO: create a test non admin user fro this test
        given().auth().oauth2(getAccessToken("NONADMINUSER", TestData.USER_PASSWORD))
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .when()
                .get(EndPoint.RESPONSES_BY_RUNGWAY_ID)
                .then()
                .statusCode(401)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetResponsesByRungwayIdReturns401ForInvalidRungwayId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.INVALID_ID)
                .when()
                .get(EndPoint.RESPONSES_BY_RUNGWAY_ID)
                .then()
                .statusCode(401)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetResponsesByRungwayIdReturns403ForInvalidAccessToken() {

        given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .when()
                .get(EndPoint.RESPONSES_BY_RUNGWAY_ID)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetApprovedResponsesByRungwayId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.STATES, TestData.STATUS_APPROVED)
                .when()
                .get(EndPoint.RESPONSES_BY_RUNGWAY_ID_BY_STATE)
                .then()
                .statusCode(200)
                .body("_embedded.results[0].state", equalTo(TestData.STATUS_APPROVED))
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyGetToReviewResponsesByRungwayId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.STATES, TestData.STATUS_TO_REVIEW)
                .when()
                .get(EndPoint.RESPONSES_BY_RUNGWAY_ID_BY_STATE)
                .then()
                .statusCode(200)
                .body("_embedded.results[0].state", equalTo(TestData.STATUS_TO_REVIEW))
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verify400ForGetDraftResponsesByRungwayId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.STATES, TestData.STATUS_DRAFT)
                .when()
                .get(EndPoint.RESPONSES_BY_RUNGWAY_ID_BY_STATE)
                .then()
                .statusCode(400)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verify400ForGetDeletedResponsesByRungwayId() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.STATES, TestData.STATUS_DELETED)
                .when()
                .get(EndPoint.RESPONSES_BY_RUNGWAY_ID_BY_STATE)
                .then()
                .statusCode(400)
                .time(lessThan(TestData.RESPONSE_TIME));
    }
}
