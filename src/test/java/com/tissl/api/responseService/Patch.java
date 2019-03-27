package com.rungway.api.responseService;

import com.tissl.api.BaseTest;
import com.tissl.api.utils.EndPoint;
import com.tissl.api.utils.JsonKey;
import com.tissl.api.utils.ParameterConstant;
import com.tissl.api.utils.TestData;
import io.restassured.response.Response;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Patch extends BaseTest {

    @Test
    public void verifyPatchByResponseIdEndpoint() {

        Map<String, String> responsesBody = new HashMap<>();
        String testMessage = TestData.TEST_MESSAGE;
        responsesBody.put(JsonKey.BODY, testMessage);
        Response patchResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .pathParam(ParameterConstant.RESPONSE_ID, TestData.EDIT_RESPONSE_ID)
                .body(responsesBody)
                .when()
                .patch(EndPoint.RESPONSE_BY_RESPONSE_ID);

        // verify the status code is 200
        patchResponse.then().statusCode(200);

        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));

        //verify updated body text
        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .pathParam(ParameterConstant.RESPONSE_ID, TestData.EDIT_RESPONSE_ID)
                .when()
                .get(EndPoint.RESPONSE_BY_RESPONSE_ID)
                .then()
                .body(JsonKey.BODY, equalTo(testMessage))
                .body(JsonKey.ANONYMOUS, equalTo(false))
                .body(JsonKey.NUM_COMMENTS, equalTo(0))
                .body(JsonKey.PRIVATE, equalTo(false))
                .statusCode(200)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyPatchResponseBodyIsNotEmpty() {

        Map<String, String> responsesBody = new HashMap<>();
        String testMessage = TestData.TEST_MESSAGE;
        responsesBody.put(JsonKey.BODY, testMessage);
        Response patchResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .pathParam(ParameterConstant.RESPONSE_ID, TestData.EDIT_RESPONSE_ID)
                .body(responsesBody)
                .when()
                .patch(EndPoint.RESPONSE_BY_RESPONSE_ID);

        // verify the body
        patchResponse.then().body(JsonKey.BODY, equalTo(testMessage));
        patchResponse.then().body(JsonKey.ID, equalTo(TestData.EDIT_RESPONSE_ID));
        patchResponse.then().body(keyPresent(JsonKey.CREATED_BY), is(true));
        patchResponse.then().body(keyPresent(JsonKey.CREATED_AT), is(true));
        patchResponse.then().body(JsonKey.ANONYMOUS, equalTo(false));
        patchResponse.then().body(JsonKey.PRIVATE, equalTo(false));


        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verify401IsReturnedForInvalidRungwayId() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        Response patchResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.INVALID_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .pathParam(ParameterConstant.RESPONSE_ID, TestData.EDIT_RESPONSE_ID)
                .body(responsesBody)
                .when()
                .patch(EndPoint.RESPONSE_BY_RESPONSE_ID);

        // verify the status code is 401
        patchResponse.then().statusCode(401);

        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verify404IsReturnedForInvalidDiscussionId() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        Response patchResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.INVALID_ID)
                .pathParam(ParameterConstant.RESPONSE_ID, TestData.EDIT_RESPONSE_ID)
                .body(responsesBody)
                .when()
                .patch(EndPoint.RESPONSE_BY_RESPONSE_ID);

        // verify the status code is 404
        patchResponse.then().statusCode(404);

        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verify404IsReturnedForInvalidResponseId() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        Response patchResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .pathParam(ParameterConstant.RESPONSE_ID, TestData.INVALID_ID)
                .body(responsesBody)
                .when()
                .patch(EndPoint.RESPONSE_BY_RESPONSE_ID);

        // verify the status code is 404
        patchResponse.then().statusCode(404);

        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyPatchResponseByDiscussionIdReturns403ForInvalidAccessToken() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        Response patchResponse = given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .pathParam(ParameterConstant.RESPONSE_ID, TestData.RESPONSE_ID)
                .body(responsesBody)
                .when()
                .patch(EndPoint.RESPONSE_BY_RESPONSE_ID);

        // verify the status code is 403
        patchResponse.then().statusCode(403);

        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyLoggedInUserCanEditTheirOwnResponse() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        Response patchResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .pathParam(ParameterConstant.RESPONSE_ID, TestData.EDIT_RESPONSE_ID)
                .body(responsesBody)
                .when()
                .patch(EndPoint.RESPONSE_BY_RESPONSE_ID);

        // verify the status code is 200
        patchResponse.then().statusCode(200);

        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyLoggedInUserCannotEditOthersResponse() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        Response patchResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .pathParam(ParameterConstant.RESPONSE_ID, TestData.OTHERS_RESPONSE_ID)
                .body(responsesBody)
                .when()
                .patch(EndPoint.RESPONSE_BY_RESPONSE_ID);

        // verify the status code is 403
        patchResponse.then().statusCode(403);

        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    @Ignore
    public void verifyPatchResponseByDiscussionIdReturns401ForUnauthorisedUser() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        Response patchResponse = given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.RUNGWAY_ID, "UnauthorisedRungwayId")
                .pathParam(ParameterConstant.DISCUSSION_ID, "UnauthorisedDiscussionId")
                .pathParam(ParameterConstant.RESPONSE_ID, "UnauthorisedDiscussionId")
                .body(responsesBody)
                .when()
                .patch(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is 401
        patchResponse.then().statusCode(401);

        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));

    }

}
