package service;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;

public class FineCalculatorTest {

    @Test
    public void testCalculateFine_OnTime() {
        FineCalculator calculator = new FineCalculator();
        LocalDate dueDate = LocalDate.of(2023, 10, 10);
        LocalDate returnDate = LocalDate.of(2023, 10, 10);
        
        double fine = calculator.calculateFine(dueDate, returnDate);
        assertEquals(0.0, fine, 0.001);
    }

    @Test
    public void testCalculateFine_Early() {
        FineCalculator calculator = new FineCalculator();
        LocalDate dueDate = LocalDate.of(2023, 10, 15);
        LocalDate returnDate = LocalDate.of(2023, 10, 10);
        
        double fine = calculator.calculateFine(dueDate, returnDate);
        assertEquals(0.0, fine, 0.001);
    }

    @Test
    public void testCalculateFine_Late() {
        FineCalculator calculator = new FineCalculator();
        LocalDate dueDate = LocalDate.of(2023, 10, 10);
        LocalDate returnDate = LocalDate.of(2023, 10, 15); // 5 days late
        
        double expectedFine = 5 * FineCalculator.FINE_PER_DAY;
        double actualFine = calculator.calculateFine(dueDate, returnDate);
        assertEquals(expectedFine, actualFine, 0.001);
    }
}
