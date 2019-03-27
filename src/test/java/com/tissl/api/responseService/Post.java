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

public class Post extends BaseTest {

    @Test
    public void verifyPostResponseByDiscussionIdEndpoint() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        responsesBody.put(JsonKey.PUBLIC, "true");
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is successful 201
        postResponse.then().statusCode(201);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verify404IsReturnedForInvalidDiscussionId() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        responsesBody.put(JsonKey.PUBLIC, "true");
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.INVALID_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is 404
        postResponse.then().statusCode(404);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verify401IsReturnedForInvalidRungwayId() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        responsesBody.put(JsonKey.PUBLIC, "true");
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.INVALID_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is 401
        postResponse.then().statusCode(401);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyPostResponseByDiscussionIdReturns403ForInvalidAccessToken() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        responsesBody.put(JsonKey.PUBLIC, "true");
        Response postResponse = given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.INVALID_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is 403
        postResponse.then().statusCode(403);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    @Ignore
    public void verifyPostResponseByDiscussionIdReturns401ForUnauthorisedUser() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        responsesBody.put(JsonKey.PUBLIC, "true");
        Response postResponse = given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.RUNGWAY_ID, "UnauthorisedRungwayId")
                .pathParam(ParameterConstant.DISCUSSION_ID, "UnauthorisedDiscussionId")
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is 401
        postResponse.then().statusCode(401);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyError403WhileTryingToPostPublicResponseForAnonymousDiscussion() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        responsesBody.put(JsonKey.PUBLIC, "true");
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.ANONYMOUS_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is 403
        postResponse.then().statusCode(403);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyError403WhileTryingToRespondPrivatelyForAnonymousDiscussion() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        responsesBody.put(JsonKey.PRIVATE, "true");
        responsesBody.put(JsonKey.ANONYMOUS, "true");
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.ANONYMOUS_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is 403
        postResponse.then().statusCode(403);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyError400WhilePostingNewResponseForPublicDiscussionWithEmptyBody() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, null);
        responsesBody.put(JsonKey.PUBLIC, "true");
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is successful 400
        postResponse.then().statusCode(400);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyError400WhilePostingNewResponseForAnonymousDiscussionWithEmptyBody() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, null);
        responsesBody.put(JsonKey.PUBLIC, "true");
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.ANONYMOUS_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is successful 400
        postResponse.then().statusCode(400);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyOnlyAnonymousResponseCanBePostedForAnonymousDiscussion() {

        Map<String, String> responsesBody = new HashMap<>();
        String testMessage = TestData.TEST_MESSAGE;
        responsesBody.put(JsonKey.BODY, testMessage);
        responsesBody.put(JsonKey.ANONYMOUS, "true");
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.ANONYMOUS_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is 201
        postResponse.then().statusCode(201);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));

        // verify the body
        postResponse.then().body(JsonKey.BODY, equalTo(testMessage));
        postResponse.then().body("any {it.key=='id'}", is(true));
        postResponse.then().body("any {it.key=='created_at'}", is(true));
        postResponse.then().body(JsonKey.ANONYMOUS, equalTo(true));
        postResponse.then().body(JsonKey.PRIVATE, equalTo(false));
    }

    @Test
    public void verifyOnlyPublicResponseCanBePostedForAnonymousDiscussion() {

        Map<String, String> responsesBody = new HashMap<>();
        String testMessage = TestData.TEST_MESSAGE;
        responsesBody.put(JsonKey.BODY, testMessage);
        responsesBody.put(JsonKey.ANONYMOUS, "true");
        responsesBody.put(JsonKey.PRIVATE, "false");
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.ANONYMOUS_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is 201
        postResponse.then().statusCode(201);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));

        // verify the body
        postResponse.then().body(JsonKey.BODY, equalTo(testMessage));
        postResponse.then().body("any {it.key=='id'}", is(true));
        postResponse.then().body("any {it.key=='created_at'}", is(true));
        postResponse.then().body(JsonKey.ANONYMOUS, equalTo(true));
        postResponse.then().body(JsonKey.PRIVATE, equalTo(false));
    }

    @Test
    public void verifyCommentCanBePostedForResponsesOnAnonymousDiscussion() {

        Map<String, String> responsesBody = new HashMap<>();
        String testMessage = TestData.TEST_MESSAGE;
        responsesBody.put(JsonKey.BODY, testMessage);
        responsesBody.put(JsonKey.ANONYMOUS, "true");
        responsesBody.put(JsonKey.PRIVATE, "false");
        responsesBody.put(JsonKey.PARENT_ID, "");
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.ANONYMOUS_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is 201
        postResponse.then().statusCode(201);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));

        // verify the body
        postResponse.then().body(JsonKey.BODY, equalTo(testMessage));
        postResponse.then().body("any {it.key=='id'}", is(true));
        postResponse.then().body("any {it.key=='created_at'}", is(true));
        postResponse.then().body(JsonKey.ANONYMOUS, equalTo(true));
        postResponse.then().body(JsonKey.PRIVATE, equalTo(false));
    }

    @Test
    public void verifyError403TryingToPostAnonymousResponseOrCommentForOthersDiscussion() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        responsesBody.put(JsonKey.ANONYMOUS, "true");
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is 403
        postResponse.then().statusCode(403);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyPostPublicResponseOrCommentForOthersDiscussionMultipleTimes() {

        Map<String, String> responsesBody = new HashMap<>();
        String testMessage = TestData.TEST_MESSAGE;
        responsesBody.put(JsonKey.BODY, testMessage);
        responsesBody.put(JsonKey.ANONYMOUS, "false");
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is 201
        postResponse.then().statusCode(201);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));

        // verify the body
        postResponse.then().body(JsonKey.BODY, equalTo(testMessage));
        postResponse.then().body("any {it.key=='id'}", is(true));
        postResponse.then().body("any {it.key=='created_by'}", is(true));
        postResponse.then().body("any {it.key=='created_at'}", is(true));
        postResponse.then().body(JsonKey.ANONYMOUS, equalTo(false));
        postResponse.then().body(JsonKey.PRIVATE, equalTo(false));

        //post response again, verify if this is successful
        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID)
                .then()
                .statusCode(201)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test(description = "BUG")
    @Ignore
    public void verifyPrivateResponseCanBeOnlyPostedOnceForOthersDiscussionMultipleTimes() {

        Map<String, String> responsesBody = new HashMap<>();
        String testMessage = TestData.TEST_MESSAGE;
        responsesBody.put(JsonKey.BODY, testMessage);
        responsesBody.put(JsonKey.PRIVATE, "true");
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is 201
        postResponse.then().statusCode(201);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));

        // verify the body
        postResponse.then().body(JsonKey.BODY, equalTo(testMessage));
        postResponse.then().body("any {it.key=='id'}", is(true));
        postResponse.then().body("any {it.key=='created_by'}", is(true));
        postResponse.then().body("any {it.key=='created_at'}", is(true));
        postResponse.then().body(JsonKey.ANONYMOUS, equalTo(false));
        postResponse.then().body(JsonKey.PRIVATE, equalTo(false));

        //post response again, verify if this is successful
        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test(description = "parent_id issue")
    @Ignore
    public void verifyICanOnlyPostCommentForPrivateResponseICreated() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        responsesBody.put(JsonKey.PRIVATE, "true");
        responsesBody.put(JsonKey.PARENT_ID, TestData.RESPONSE_ID);
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .pathParam(ParameterConstant.RESPONSE_ID, TestData.RESPONSE_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is 201
        postResponse.then().statusCode(201);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test(description = "parent_id issue")
    @Ignore
    public void verifyError401TryingToPostCommentForOthersResponse() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        responsesBody.put(JsonKey.PRIVATE, "true");
        responsesBody.put(JsonKey.PARENT_ID, TestData.OTHERS_RESPONSE_ID);
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .pathParam(ParameterConstant.DISCUSSION_ID, TestData.PUBLIC_DISCUSSION_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.RESPONSES_BY_DISCUSSION_ID);

        // verify the status code is 401
        postResponse.then().statusCode(401);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

}
