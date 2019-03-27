package com.rungway.api.authService;

import com.tissl.api.BaseTest;
import com.tissl.api.utils.EndPoint;
import com.tissl.api.utils.JsonKey;
import com.tissl.api.utils.ParameterConstant;
import com.tissl.api.utils.TestData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class Post extends BaseTest {

    @Test
    public void verifyAuthenticationEndpoint() {

        Response postResponse = given()
                .contentType(ContentType.URLENC)
                .formParam(ParameterConstant.GRANT_TYPE, "password")
                .formParam(ParameterConstant.USERNAME, TestData.USER_EMAIL)
                .formParam(ParameterConstant.PASSWORD, TestData.USER_PASSWORD)
                .formParam(ParameterConstant.SCOPE, "read write")
                .when()
                .post(EndPoint.OAUTH);

        // verify the status code is successful 200
        postResponse.then().statusCode(200);

        // verify response time
        postResponse.then().time(lessThan(TestData.RESPONSE_TIME));

        //verify that the access token is not empty
        postResponse.then().body(JsonKey.ACCESS_TOKEN, notNullValue());

        //verify that the token type is bearer
        postResponse.then().body(JsonKey.TOKEN_TYPE, equalTo("bearer"));

        //verify that refresh token type is not empty
        postResponse.then().body(JsonKey.REFRESH_TOKEN, notNullValue());

        //verify that expires_in is 86399
        postResponse.then().body(JsonKey.EXPIRES_IN, equalTo(43199));

        //verify that scope is read write
        postResponse.then().body(JsonKey.SCOPE, equalTo("read write"));

        //verify that jti is not empty
        postResponse.then().body(JsonKey.JTI, notNullValue());
    }

    @Test
    public void verifyStatus400IsReturnedForInvalidGrantType() {
        given().contentType(ContentType.URLENC)
                .formParam(ParameterConstant.GRANT_TYPE, "invalid")
                .formParam(ParameterConstant.USERNAME, TestData.USER_EMAIL)
                .formParam(ParameterConstant.PASSWORD, TestData.USER_PASSWORD)
                .formParam(ParameterConstant.SCOPE, "read write")
                .when()
                .post(EndPoint.OAUTH)
                .then()
                .statusCode(400);
    }

    @Test
    public void verifyStatus401IsReturnedForInvalidUserName() {
        given().contentType(ContentType.URLENC)
                .formParam(ParameterConstant.GRANT_TYPE, "password")
                .formParam(ParameterConstant.USERNAME, TestData.INVALID_USER_EMAIL)
                .formParam(ParameterConstant.PASSWORD, TestData.USER_PASSWORD)
                .formParam(ParameterConstant.SCOPE, "read write")
                .when()
                .post(EndPoint.OAUTH)
                .then()
                .statusCode(401);
    }

    @Test
    public void verifyStatus401IsReturnedForInvalidPassword() {
        given().contentType(ContentType.URLENC)
                .formParam(ParameterConstant.GRANT_TYPE, "password")
                .formParam(ParameterConstant.USERNAME, TestData.USER_EMAIL)
                .formParam(ParameterConstant.PASSWORD, "invalid")
                .formParam(ParameterConstant.SCOPE, "read write")
                .when()
                .post(EndPoint.OAUTH)
                .then()
                .statusCode(401);
    }

    @Test
    public void verifyStatus400IsReturnedForInvalidScope() {
        given().contentType(ContentType.URLENC)
                .formParam(ParameterConstant.GRANT_TYPE, "password")
                .formParam(ParameterConstant.USERNAME, TestData.USER_EMAIL)
                .formParam(ParameterConstant.PASSWORD, TestData.USER_PASSWORD)
                .formParam(ParameterConstant.SCOPE, "invalid")
                .when()
                .post(EndPoint.OAUTH)
                .then()
                .statusCode(400);
    }
}
