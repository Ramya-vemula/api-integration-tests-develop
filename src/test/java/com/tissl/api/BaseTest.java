package com.tissl.api;

import com.tissl.api.utils.EndPoint;
import com.tissl.api.utils.JsonKey;
import com.tissl.api.utils.ParameterConstant;
import com.tissl.api.utils.TestData;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BaseTest {

    public static String accessToken;

    public String jsonPathFirstIndex = "_embedded.results[0].";

    public String keyPresent(String key) {
        return "any {it.key=='" + key + "'}";
    }

    public String keyPresentAtFirstIndex(String key) {
        return jsonPathFirstIndex + keyPresent(key);
    }

    public String jsonPathAtIndex(String path, int index) {
        return path + "[" + index + "]";
    }

    public String getAccessToken(final String userName, final String password) {
        String response =
                given()
                        .formParam(ParameterConstant.GRANT_TYPE, "password")
                        .formParam(ParameterConstant.USERNAME, userName)
                        .formParam(ParameterConstant.PASSWORD, password)
                        .formParam(ParameterConstant.SCOPE, "read write")
                        .post("/oauth/token")
                        .asString();

        JsonPath jsonPath = new JsonPath(response);
        String accessToken = jsonPath.getString("access_token");
        Assert.assertNotNull(accessToken);
        return accessToken;
    }

    public String getRefreshToken(final String userName, final String password) {
        String response =
                given()
                        .formParam(ParameterConstant.GRANT_TYPE, "password")
                        .formParam(ParameterConstant.USERNAME, userName)
                        .formParam(ParameterConstant.PASSWORD, password)
                        .formParam(ParameterConstant.SCOPE, "read write")
                        .post("/oauth/token")
                        .asString();

        JsonPath jsonPath = new JsonPath(response);
        String refreshToken = jsonPath.getString(JsonKey.REFRESH_TOKEN);
        Assert.assertNotNull(refreshToken);
        return refreshToken;
    }

    @BeforeSuite
    public void setup() {
        RestAssured.baseURI = TestData.getProperty("BASE_URL");
        accessToken = getAccessToken(TestData.USER_EMAIL, TestData.USER_PASSWORD);
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();

    }

    @AfterSuite(alwaysRun = true)
    public void deleteUsersAfterEveryTestRun() {
        String json = given().auth().oauth2(accessToken).get(EndPoint.ACCOUNTS).asString();
        JsonPath jp = new JsonPath(json);
        jp.setRoot("_embedded.results");
        List<String> accountIds = jp.getList("findAll {it.email =~ /trash/}.account_id");
        for (String account : accountIds) {
            given().auth().oauth2(accessToken)
                    .contentType(ContentType.JSON)
                    .pathParam("accountId", account)
                    .when()
                    .delete(EndPoint.ACCOUNT_BY_ID)
                    .then()
                    .statusCode(202);
        }
    }
}
