package com.tissl.api.accountService;

import com.tissl.api.BaseTest;
import com.tissl.api.utils.EndPoint;
import com.tissl.api.utils.Helpers;
import com.tissl.api.utils.JsonKey;
import com.tissl.api.utils.TestData;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class Post extends BaseTest {

    @Test
    public void verifyPostAccountsEndpoint() {
        String randomEmail = Helpers.randomEmail();
        Map<String, String> user = new HashMap<>();
        user.put(JsonKey.EMAIL, randomEmail);
        user.put(JsonKey.PASSWORD, TestData.USER_PASSWORD);
        user.put(JsonKey.STATUS, TestData.USER_STATUS_ACTIVE);

        Response postResponse = given().auth().oauth2(accessToken)
                .body(user)
                .when()
                .post(EndPoint.ACCOUNTS);

        // verify the status code is successful 201
        postResponse.then().statusCode(201);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyStatus422IsReturnedForExistingUser() {
        Map<String, String> user = new HashMap<>();
        user.put(JsonKey.EMAIL, TestData.USER_EMAIL);
        user.put(JsonKey.PASSWORD, TestData.USER_PASSWORD);
        user.put(JsonKey.STATUS, TestData.USER_STATUS_ACTIVE);

        given().auth().oauth2(accessToken)
                .body(user)
                .when().post(EndPoint.ACCOUNTS)
                .then()
                .statusCode(422)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test(enabled = false)
    //TODO: Not implemented on server side
    public void verifyAccountExistenceForUser() {
        Map<String, String> user = new HashMap<>();
        user.put(JsonKey.EMAIL, TestData.USER_EMAIL);

        given().auth().oauth2(accessToken)
                .body(user)
                .when().post(EndPoint.VERIFY_ACCOUNTS)
                .then()
                .statusCode(204)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test(enabled = false)
    //TODO: Not implemented on server side
    public void verifyAccountDoNotExistForUser() {
        Map<String, String> user = new HashMap<>();
        user.put(JsonKey.EMAIL, TestData.INVALID_USER_EMAIL);
        user.put(JsonKey.PASSWORD, TestData.USER_PASSWORD);

        given().auth().oauth2(accessToken)
                .body(user)
                .when().post(EndPoint.VERIFY_ACCOUNTS)
                .then()
                .statusCode(404)
                .time(lessThan(TestData.RESPONSE_TIME));
    }

    @Test
    public void verifyPostAccountReturns403ForInvalidAccessToken() {

        Map<String, String> user = new HashMap<>();
        user.put(JsonKey.EMAIL, TestData.USER_EMAIL);
        user.put(JsonKey.PASSWORD, TestData.USER_PASSWORD);
        user.put(JsonKey.STATUS, TestData.USER_STATUS_ACTIVE);

        given().auth().oauth2(TestData.INVALID_TOKEN)
                .body(user)
                .post(EndPoint.ACCOUNTS)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyPostResetPasswordEndpoint() {
        Map<String, String> user = new HashMap<>();
        user.put(JsonKey.EMAIL, TestData.USER_EMAIL);

        Response postResponse = given()
                .body(user)
                .when()
                .post(EndPoint.RESET_PASSWORD);

        //verify the status code is successful 204
        postResponse.then().statusCode(204);

        //verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));
    }


}
