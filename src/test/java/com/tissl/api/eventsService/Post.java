package com.rungway.api.eventsService;

import com.tissl.api.BaseTest;
import com.tissl.api.utils.EndPoint;
import com.tissl.api.utils.JsonKey;
import com.tissl.api.utils.TestData;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class Post extends BaseTest {

    @Test
    public void verifyPostEventsEndpoint() {
        Map<String, String> user = new HashMap<>();
        user.put(JsonKey.RUNGWAY_ID, TestData.RUNGWAY_ID);
        user.put(JsonKey.ACCOUNT_ID, TestData.EDIT_USER_ACCOUNT_ID);

        given().body(user)
                .when()
                .post(EndPoint.EVENTS)
                .then()
                .statusCode(201)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyPostEventsReturns400ForInvalidRungwayId() {
        Map<String, String> user = new HashMap<>();
        user.put(JsonKey.RUNGWAY_ID, TestData.INVALID_ID);
        user.put(JsonKey.ACCOUNT_ID, TestData.EDIT_USER_ACCOUNT_ID);

        given().body(user)
                .when()
                .post(EndPoint.EVENTS)
                .then()
                .statusCode(400)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyPostEventsReturns400ForInvalidAccountId() {
        Map<String, String> user = new HashMap<>();
        user.put(JsonKey.RUNGWAY_ID, TestData.RUNGWAY_ID);
        user.put(JsonKey.ACCOUNT_ID, TestData.INVALID_ID);

        given().body(user)
                .when()
                .post(EndPoint.EVENTS)
                .then()
                .statusCode(400)
                .time(lessThan(TestData.RESPONSE_TIME));
    }
}
