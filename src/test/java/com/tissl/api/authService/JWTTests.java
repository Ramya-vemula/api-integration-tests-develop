package com.rungway.api.authService;

import com.tissl.api.BaseTest;
import com.tissl.api.utils.EndPoint;
import com.tissl.api.utils.Helpers;
import com.tissl.api.utils.JsonKey;
import com.tissl.api.utils.TestData;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class JWTTests extends BaseTest {

    @Test
    public void verify403ForTamperingUserId() {

        given().auth().oauth2(Helpers.getTamperedJWT(accessToken, JsonKey.USER_NAME, TestData.INVALID_ID))
                .when()
                .get(EndPoint.ACCOUNTS)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verify403ForTamperingExpiryTime() {

        given().auth().oauth2(Helpers.getTamperedJWT(accessToken, JsonKey.EXP, TestData.INVALID_ID))
                .when()
                .get(EndPoint.ACCOUNTS)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verify403ForTamperingClientId() {

        given().auth().oauth2(Helpers.getTamperedJWT(accessToken, JsonKey.CLIENT_ID, TestData.INVALID_ID))
                .when()
                .get(EndPoint.ACCOUNTS)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

    @Test
    public void verify403ForTamperingJTI() {

        given().auth().oauth2(Helpers.getTamperedJWT(accessToken, JsonKey.JTI, TestData.INVALID_ID))
                .when()
                .get(EndPoint.ACCOUNTS)
                .then()
                .statusCode(403)
                .time(lessThan(TestData.RESPONSE_TIME));

    }

}
