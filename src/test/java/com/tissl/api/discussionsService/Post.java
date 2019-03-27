package com.rungway.api.discussionsService;

import com.tissl.api.BaseTest;
import com.tissl.api.utils.EndPoint;
import com.tissl.api.utils.JsonKey;
import com.tissl.api.utils.ParameterConstant;
import com.tissl.api.utils.TestData;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Post extends BaseTest {

    @Test
    public void verifyPostDiscussionByRungwayIdEndpoint() {

        Map<String, String> responsesBody = new HashMap<>();
        String testMessage = TestData.TEST_MESSAGE;
        responsesBody.put(JsonKey.TITLE, testMessage);
        responsesBody.put(JsonKey.BODY, testMessage);
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.DISCUSSIONS_BY_RUNGWAY_ID);

        //verify the post response body
        postResponse.then()
                .body(JsonKey.TITLE, equalTo(testMessage))
                .body(JsonKey.BODY, equalTo(testMessage))
                .body(JsonKey.CREATED_BY, equalTo(TestData.USER_ACCOUNT_ID))
                .body(keyPresent(JsonKey.CREATED_AT), is(true))
                .body(JsonKey.ANONYMOUS, equalTo(false))
                .body(JsonKey.STATE, equalTo(TestData.STATUS_DRAFT))
                .body(JsonKey.IS_MINE, equalTo(true));

        // verify the status code is 201
        postResponse.then().statusCode(201);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyPostDiscussionByRungwayIdAnonymously() {

        Map<String, String> responsesBody = new HashMap<>();
        String testMessage = TestData.TEST_MESSAGE;
        responsesBody.put(JsonKey.TITLE, testMessage);
        responsesBody.put(JsonKey.BODY, testMessage);
        responsesBody.put(JsonKey.ANONYMOUS, "true");
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.DISCUSSIONS_BY_RUNGWAY_ID);

        //verify the post response body
        postResponse.then()
                .body(JsonKey.TITLE, equalTo(testMessage))
                .body(JsonKey.BODY, equalTo(testMessage))
                .body(JsonKey.CREATED_BY, isEmptyOrNullString())
                .body(keyPresent(JsonKey.CREATED_AT), is(true))
                .body(JsonKey.ANONYMOUS, equalTo(true))
                .body(JsonKey.STATE, equalTo(TestData.STATUS_DRAFT))
                .body(JsonKey.IS_MINE, equalTo(true));

        // verify the status code is 201
        postResponse.then().statusCode(201);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verify401IsReturnedForInvalidRungwayId() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.TITLE, TestData.TEST_MESSAGE);
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.INVALID_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.DISCUSSIONS_BY_RUNGWAY_ID);

        // verify the status code is 401
        postResponse.then().statusCode(401);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyError400WhilePostingDiscussionWithWithInvalidAnonymousValue() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.TITLE, TestData.TEST_MESSAGE);
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        responsesBody.put(JsonKey.ANONYMOUS, TestData.TEST_MESSAGE);
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.DISCUSSIONS_BY_RUNGWAY_ID);

        // verify the status code is 400
        postResponse.then().statusCode(400);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyError400WhilePostingDiscussionWithNoTitle() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.BODY, TestData.TEST_MESSAGE);
        responsesBody.put(JsonKey.ANONYMOUS, TestData.TEST_MESSAGE);
        Response postResponse = given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.DISCUSSIONS_BY_RUNGWAY_ID);

        // verify the status code is 400
        postResponse.then().statusCode(400);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyPostResponseByDiscussionIdReturns403ForInvalidAccessToken() {

        Map<String, String> responsesBody = new HashMap<>();
        responsesBody.put(JsonKey.TITLE, TestData.TEST_MESSAGE);
        Response postResponse = given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.RUNGWAY_ID, TestData.RUNGWAY_ID)
                .body(responsesBody)
                .when()
                .post(EndPoint.DISCUSSIONS_BY_RUNGWAY_ID);

        // verify the status code is 403
        postResponse.then().statusCode(403);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));

    }


}
