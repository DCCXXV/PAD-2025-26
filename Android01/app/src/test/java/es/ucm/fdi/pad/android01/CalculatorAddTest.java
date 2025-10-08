package es.ucm.fdi.pad.android01;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorAddTest {

    private CalculatorAdd calculator;

    @Test
    public void addition_isCorrect() {
        double result = calculator.addNumbers(2.3, 3.8);
        assertEquals(6.1, result, 0.0);

        result = calculator.addNumbers(2, 3);
        assertEquals(5, result, 0.0);
    }

}
