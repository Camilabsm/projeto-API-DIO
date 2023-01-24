package dev.camila;

public class Booking 
{
   String firstname;
   String lastname;
   float totalprice;
   Boolean depositpaid;
   BookingDates bookingdates;
   String additionalneeds;

   public Booking(String firstname, String lastname, float totalprice, Boolean depositpaid, BookingDates bookingdates,
        String additionalneeds) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.totalprice = totalprice;
    this.depositpaid = depositpaid;
    this.bookingdates = bookingdates;
    this.additionalneeds = additionalneeds;
}

public String getFirstname() {
    return firstname;
}

public void setFirstname(String firstname) {
    this.firstname = firstname;
}

public String getLastname() {
    return lastname;
}

public void setLastname(String lastname) {
    this.lastname = lastname;
}

public float getTotalprice() {
    return totalprice;
}

public void setTotalprice(float totalprice) {
    this.totalprice = totalprice;
}

public Boolean getDepositpaid() {
    return depositpaid;
}

public void setDepositpaid(Boolean depositpaid) {
    this.depositpaid = depositpaid;
}

public BookingDates getBookingdates() {
    return bookingdates;
}

public void setBookingdates(BookingDates bookingdates) {
    this.bookingdates = bookingdates;
}

public String getAdditionalneeds() {
    return additionalneeds;
}

public void setAdditionalneeds(String additionalneeds) {
    this.additionalneeds = additionalneeds;
}



   


}
