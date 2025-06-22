/* Esta classe armazena a estrutura de dados da API (Booking)*/

public class Booking {
    public String firstname;
    public String lastname;
    public int totalprice;
    public boolean depositpaid;

    public class BookingDates {
        public String checkin;
        public String checkout;
    }
    public BookingDates bookingdates;

    public String additionalneeds;
}
