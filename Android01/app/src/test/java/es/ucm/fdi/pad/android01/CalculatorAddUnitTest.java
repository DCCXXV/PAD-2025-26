package es.ucm.fdi.pad.android01;

import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorAddUnitTest {

    @Test
    public void checkCalculatorAdd() {
        double result1 = add(2, 3);
        assertEquals(5.0, result1, 0.0001);

        double result2 = add(4, 2.5);
        assertEquals(6.5, result2, 0.0001);

        double result3 = add(3.14, 2.18);
        assertEquals(5.32, result3, 0.0001);

        double result4 = add(-5, 3.5);
        assertEquals(-1.5, result4, 0.0001);
    }

    private double add(double a, double b) {
        return a + b;
    }
}