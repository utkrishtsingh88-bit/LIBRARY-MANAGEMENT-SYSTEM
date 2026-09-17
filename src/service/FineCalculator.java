package service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Utility for calculating fines for late book returns.
 */
public class FineCalculator {
    public static final double FINE_PER_DAY = 2.0;

    /**
     * Calculates the fine based on the due date and actual return date.
     * @param dueDate the expected return date
     * @param returnDate the actual return date
     * @return the fine amount
     */
    public double calculateFine(LocalDate dueDate, LocalDate returnDate) {
        if (returnDate.isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
            return daysLate * FINE_PER_DAY;
        }
        return 0.0;
    }
}
