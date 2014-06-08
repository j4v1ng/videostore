import org.junit.Test;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerTest {

    private Customer customer;
    private String customerStatement;

    @Test
    public void shouldContainEmptyRentalRecordWhenCustomerNeverRented() {
        givenACustomerNeverRentedAMovie();
        whenAsksForStatement();
        theRentalRecordIsEmpty();
    }

    @Test
    public void shouldContainPastRentedMoviesInTheRentalRecordWhenTheCustomerRentedSomeMovieInThePast() {
        givenACustomerRentedAMovieInThePast();
        whenAsksForStatement();
        theRentalRecordShouldContainTheNameOfTheMovieAndThePriceCode();
    }

    private void givenACustomerRentedAMovieInThePast() {
        Rental rental = mock(Rental.class);
        when(rental.getMovie()).thenReturn(new Movie("Star Wars", Movie.CHILDRENS));
        this.customer = new Customer("djordje");
        customer.addRental(rental);
    }

    private void givenACustomerNeverRentedAMovie() {
        this.customer = new Customer("djordje");
    }

    private void whenAsksForStatement() {
        this.customerStatement = customer.statement();
    }

    private void theRentalRecordIsEmpty() {
        String secondPartOfStatement = customerStatement.split("\n")[1];
        assertThat(secondPartOfStatement,startsWith("Amount"));
    }

    private void theRentalRecordShouldContainTheNameOfTheMovieAndThePriceCode() {
        String secondPartOfStatement = customerStatement.split("\n")[1];
        assertThat(secondPartOfStatement,is("\tStar Wars\t1.5"));
    }
}
