package es.ucm.fdi.pad.android01;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculatorAddTest {
    @Test
    public void checkCalculatorAdd() {

        CalculatorAdd calculator = new CalculatorAdd();

        final double DELTA = 0.0001;


        assertEquals("Suma de 5 + 10", 15.0, calculator.addNumbers(5.0, 10.0), 0.0);


        assertEquals("Suma de -8 + 3", -5.0, calculator.addNumbers(-8.0, 3.0), 0.0);


        assertEquals("Suma de 1.5 + 2.5", 4.0, calculator.addNumbers(1.5, 2.5), 0.0);


        assertEquals("Suma de 0.1 + 0.2", 0.3, calculator.addNumbers(0.1, 0.2), DELTA);
    }
}
