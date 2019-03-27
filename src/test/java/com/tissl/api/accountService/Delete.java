package com.rungway.api.accountService;

import com.tissl.api.BaseTest;
import com.tissl.api.utils.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class Delete extends BaseTest {

    @Test
    public void verifyDeleteAccountById() {
        String randomEmail = Helpers.randomEmail();
        Map<String, String> user = new HashMap<>();
        user.put(JsonKey.EMAIL, randomEmail);
        user.put(JsonKey.PASSWORD, TestData.USER_PASSWORD);
        user.put(JsonKey.STATUS, TestData.USER_STATUS_ACTIVE);

        //create an account
        Response postResponse = given().auth().oauth2(accessToken)
                .body(user)
                .when()
                .post(EndPoint.ACCOUNTS);

        //verify the status code is successful 201
        postResponse.then().statusCode(201);

        //get above accountId delete account
        String accountId = postResponse.jsonPath().get(JsonKey.ACCOUNT_ID);

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.ACCOUNT_ID, accountId)
                .when()
                .delete(EndPoint.ACCOUNT_BY_ID)
                .then()
                .statusCode(202)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyDeleteAccountReturns404ForInvalidUser() {

        given().auth().oauth2(accessToken)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.INVALID_ID)
                .when()
                .delete(EndPoint.ACCOUNT_BY_ID)
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verifyDeleteAccountReturns403ForInvalidAccessToken() {

        given().auth().oauth2(TestData.INVALID_TOKEN)
                .pathParam(ParameterConstant.ACCOUNT_ID, TestData.USER_ACCOUNT_ID)
                .when()
                .delete(EndPoint.ACCOUNT_BY_ID)
                .then()
                .statusCode(403)
                .contentType(ContentType.JSON)
                .time(lessThan(TestData.RESPONSE_TIME));

    }


}
