package dev.camila;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;


public class AuthTest {
    
     private static RequestSpecification request;
    
    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
    }

    @BeforeEach 
    void setRequest(){
        request = given().config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
        .contentType(ContentType.JSON)
        .auth().basic("admin", "password123");
}

    @Test
    public void tokenGeneration_returnOk(){
        Response response = request
        .when()
            .post("/auth")
        .then()
            .extract()
            .response();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.statusCode());
    }

}
