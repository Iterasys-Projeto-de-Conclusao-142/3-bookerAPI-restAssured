import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.google.gson.Gson;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestBooking extends Common {
    private static int bookingIdResponse;

    /* Método utilitário para criar objetos Booking */
    private Booking createObjBooking(
            String firstName, String lastName, int totalPrice,
            boolean depositPaid, String checkinDate,
            String checkoutDate, String additionalNeeds) {

        Booking booking = new Booking();
        Booking.BookingDates dates = booking.new BookingDates();

        booking.firstname = firstName;
        booking.lastname = lastName;
        booking.totalprice = totalPrice;
        booking.depositpaid = depositPaid;
        booking.bookingdates = dates;
        dates.checkin = checkinDate;
        dates.checkout = checkoutDate;
        booking.additionalneeds = additionalNeeds;

        return booking;
    }

    @BeforeAll
    public static void setupToken() {
        if (TestAuth.token == null || TestAuth.token.isEmpty()) {
            TestAuth.gerarToken();
        }
    }

    @ParameterizedTest
    @Order(1)
    @CsvFileSource(resources = "/csv/bookingData.csv", numLinesToSkip = 1, delimiter = ',')
    public void testCreateBooking(
            String firstName,
            String lastName,
            int totalPrice,
            boolean depositPaid,
            String checkinDate,
            String checkoutDate,
            String additionalNeeds) {

        /* Cria o objeto Booking usando método utilitário */
        Booking createBooking = createObjBooking(firstName, lastName, totalPrice, depositPaid, checkinDate, checkoutDate,
                additionalNeeds);
        String jsonBody = new Gson().toJson(createBooking);

        bookingIdResponse = given()
                .contentType(ct)
                .log().all()
                .body(jsonBody)
            .when()
                .post(baseUrl + "/booking")
            .then()
                .log().all()
                .statusCode(200)
                .body("bookingid", is(notNullValue()))
                .body("booking.firstname", is(firstName))
                .body("booking.lastname", is(lastName))
                .body("booking.totalprice", is(totalPrice))
                .body("booking.depositpaid", is(depositPaid))
                .body("booking.bookingdates.checkin", is(checkinDate))
                .body("booking.bookingdates.checkout", is(checkoutDate))
                .body("booking.additionalneeds", is(additionalNeeds))
                .extract().path("bookingid");

        System.out.println("Booking ID gerado: " + bookingIdResponse);
    }

    @ParameterizedTest
    @Order(2)
    @CsvFileSource(resources = "/csv/bookingUpdateData.csv", numLinesToSkip = 1, delimiter = ',')
    public void testUpdateBooking(String firstName,
            String lastName,
            int totalPrice,
            boolean depositPaid,
            String checkinDate,
            String checkoutDate,
            String additionalNeeds) {

        Booking updateBooking = createObjBooking(firstName, lastName, totalPrice, depositPaid, checkinDate, checkoutDate, additionalNeeds);
        String jsonBody = new Gson().toJson(updateBooking);

        given()
            .contentType(ct)
            .log().all()
            .header("Cookie", "token=" + TestAuth.token)
            .body(jsonBody)
        .when()
                .put(baseUrl + "/booking/" + bookingIdResponse)
        .then()
            .log().all()
            .statusCode(200)
            .body("firstname", is(firstName))
            .body("lastname", is(lastName))
            .body("totalprice", is(totalPrice))
            .body("depositpaid", is(depositPaid))
            .body("bookingdates.checkin", is(checkinDate))
            .body("bookingdates.checkout", is(checkoutDate))
            .body("additionalneeds", is(additionalNeeds))
        ;
    }
}
