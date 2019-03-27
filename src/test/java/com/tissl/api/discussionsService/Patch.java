package com.rungway.api.discussionsService;

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
    public void verifyPatchByDiscussionIdEndpoint() {

        Map<String, String> responsesBody = new HashMap<>();
        String testMessage = TestData.TEST_MESSAGE;
        responsesBody.put(JsonKey.TITLE, testMessage);
        responsesBody.put(JsonKey.BODY, testMessage);
        Response patchResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.NO_RESPONSE_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .patch(EndPoint.DISCUSSIONS_BY_DISCUSSION_ID);

        // verify the status code is 200
        patchResponse.then().statusCode(200);

        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));

        //verify updated body text
        patchResponse.then()
                .body(JsonKey.TITLE, equalTo(testMessage))
                .body(JsonKey.BODY, equalTo(testMessage))
                .body(JsonKey.ID, equalTo(TestData.NO_RESPONSE_DISCUSSION_ID))
                .body(keyPresent(JsonKey.CREATED_AT), is(true))
                .body(JsonKey.ANONYMOUS, equalTo(false))
                .body(JsonKey.STATE, equalTo(TestData.STATUS_TO_REVIEW))
                .body(JsonKey.IS_MINE, equalTo(true));
    }

    @Test
    public void verifyPatchDiscussionTitleAndBodyCannotBeEmpty() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.TITLE, null);
        responsesBody.put(JsonKey.BODY, null);
        Response patchResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.EDIT_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .patch(EndPoint.DISCUSSIONS_BY_DISCUSSION_ID);

        // verify the status code is 400
        patchResponse.then().statusCode(400);

        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyPatchDiscussionWithTitleAndEmptyBody() {

        Map<String, String> responsesBody = new HashMap<>();
        String testMessage = TestData.TEST_MESSAGE;
        responsesBody.put(JsonKey.TITLE, testMessage);
        responsesBody.put(JsonKey.BODY, null);
        Response patchResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.NO_RESPONSE_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .patch(EndPoint.DISCUSSIONS_BY_DISCUSSION_ID);

        // verify the status code is 200
        patchResponse.then().statusCode(200);

        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verify404IsReturnedForInvalidRungwayId() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.TITLE, TestData.TEST_MESSAGE);
        Response patchResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.INVALID_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .patch(EndPoint.DISCUSSIONS_BY_DISCUSSION_ID);

        // verify the status code is 404
        patchResponse.then().statusCode(404);

        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verify404IsReturnedForInvalidDiscussionId() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.TITLE, TestData.TEST_MESSAGE);
        Response patchResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.INVALID_ID)
                .body(responsesBody)
                .when()
                .patch(EndPoint.DISCUSSIONS_BY_DISCUSSION_ID);

        // verify the status code is 404
        patchResponse.then().statusCode(404);

        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }


    @Test
    public void verifyPatchDiscussionByDiscussionIdReturns403ForInvalidAccessToken() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.TITLE, TestData.TEST_MESSAGE);
        Response patchResponse = given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .patch(EndPoint.DISCUSSIONS_BY_DISCUSSION_ID);

        // verify the status code is 403
        patchResponse.then().statusCode(403);

        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyLoggedInUserCannotEditOthersDiscussion() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.TITLE, TestData.TEST_MESSAGE);
        Response patchResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.OTHERS_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .patch(EndPoint.DISCUSSIONS_BY_DISCUSSION_ID);

        // verify the status code is 403
        patchResponse.then().statusCode(403);

        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    @Ignore
    public void verifyPatchDiscussionByDiscussionIdReturns401ForUnauthorisedUser() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        Response patchResponse = given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.RUNGWAY_ID, "UnauthorisedRungwayId")
                .pathParam(ParameterConstant.DISCUSSION_ID, "UnauthorisedDiscussionId")
                .body(responsesBody)
                .when()
                .patch(EndPoint.DISCUSSIONS_BY_DISCUSSION_ID);

        // verify the status code is 401
        patchResponse.then().statusCode(401);

        // verify response time
        patchResponse.then().time(lessThan(TestData.RESPONSE_TIME));

    }

}
