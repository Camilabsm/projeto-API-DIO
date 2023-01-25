package dev.camila;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;
import static org.hamcrest.Matchers.*;


public class BookingTest 
{
    public static Faker faker;
    private static RequestSpecification request;
    private static Booking booking;
    private static BookingDates bookingDates;
    private static User user;

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
        faker = new Faker();
        
        user = new User(faker.name().username(),
        faker.name().firstName(), faker.name().lastName(), faker.internet().safeEmailAddress(),
        faker.internet().password(8,10), faker.phoneNumber().toString());

        bookingDates = new BookingDates("2022-07-25", "2022-07-28");
        booking = new Booking(user.getFirstName(), user.getLastName(), 
        (float)faker.number().randomDouble(2, 50, 100000), true, 
        bookingDates, "");

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter());
    }

    @BeforeEach 
    void setRequest(){
        request = given().config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
        .contentType(ContentType.JSON)
        .auth().basic("admin", "password123");
}

@Test
public void getAllBookingsById_returnOk(){
        Response response = request
                                .when()
                                    .get("/booking")
                                .then()
                                    .extract()
                                    .response();


    Assertions.assertNotNull(response);
    Assertions.assertEquals(200, response.statusCode());
}

@Test
public void  getBookingById_returnOkIfExistingId(){
    Random random = new Random();
    int id = random.nextInt(0, 1000);
    Response response = request
    .when()
        .pathParam("idNumber", id)
        .get("/booking/{idNumber}")
    .then()
        .extract()
        .response();
    
    Assertions.assertNotNull(response);
    Assertions.assertEquals(200, response.statusCode());   
}

@Test
public void  createBooking_WithValidData_returnOk(){

    given().config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
            .contentType(ContentType.JSON)
        .when()
            .body(booking)
            .post("/booking")
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON).and().time(lessThan(2000L));
}

@Test
public void updateBooking_returnOkIfExistingId(){
    Random random = new Random();
    int id2 = random.nextInt(0, 1000);
    String ids = Integer.toString(id2);

        RestAssured.given().baseUri("https://restful-booker.herokuapp.com/booking")
                .basePath(ids)
                .contentType(ContentType.JSON)
                .auth().basic("admin", "password123")
                .body(booking)
                .when()
                    .put()
                .then()
                    .assertThat()
                    .statusCode(200)
                .and()
                    .statusLine(containsString("OK")); 
}

@Test
public void partialUpdateBooking_returnOkIfExistingId(){
    Random random = new Random();
    int id = random.nextInt(0, 1000);
    Response response = request
    .when()
        .auth().basic("admin", "password123")
        .pathParam("idNumber", id)
        .patch("/booking/{idNumber}", booking.depositpaid.equals(false))
    .then()
        .extract()
        .response();
    
    Assertions.assertNotNull(response);
    Assertions.assertEquals(200, response.statusCode());   
}

@Test
public void deleteBooking_returnOkIfExistingId(){
    Response response = request
		.when()
            .auth().basic("admin", "password123")
        .and()
            .delete("/booking/25")
		.then()
            .extract()
            .response();
                      
        Assertions.assertNotNull(response);
        Assertions.assertEquals(201, response.statusCode());
        
}

}

