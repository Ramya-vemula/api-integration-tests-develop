package com.tissl.api.utils;

import io.restassured.path.json.JsonPath;
import org.apache.commons.codec.binary.Base64;

import java.util.Random;

public final class Helpers {

    private Helpers() {
        super();
    }

    public static String randomEmail() {
        Random random = new Random();
        int number = random.nextInt(1000);
        String randomString = String.format("%06d", number);
        return "trash+api" + randomString + "@tissl.co.uk";
    }

    public static String getTamperedJWT(final String token, final String key, final String newValue) {
        String[] splitString = token.split("\\.");
        String base64EncodedHeader = splitString[0];
        String base64EncodedBody = splitString[1];
        String base64EncodedSignature = splitString[2];

        Base64 base64Url = new Base64(true);

        // JWT body
        String body = new String(base64Url.decode(base64EncodedBody));

        JsonPath jp = new JsonPath(body);
        String tamperedJWTBody = null;

        switch (key) {

            case "exp":
                String exp = jp.get("exp").toString();
                tamperedJWTBody = body.replace(exp, newValue);
                break;

            case "user_name":
                String user_name = jp.get("user_name").toString();
                tamperedJWTBody = body.replace(user_name, newValue);
                break;

            case "client_id":
                String client_id = jp.get("client_id").toString();
                tamperedJWTBody = body.replace(client_id, newValue);
                break;

            case "jti":
                String jti = jp.get("jti").toString();
                tamperedJWTBody = body.replace(jti, newValue);
                break;

            default:
                System.out.println("Passed in parameter *" + key + "* do not exist");
                break;

        }
        // encode the JWT body
        String tamperedJWT = Base64.encodeBase64String(tamperedJWTBody.getBytes());

        //replace with new JWT and encode
        String newJWTToken = base64EncodedHeader + "." + tamperedJWT + "." + base64EncodedSignature;
        return newJWTToken;
    }

}
