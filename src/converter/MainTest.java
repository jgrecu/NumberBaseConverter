package converter;

import org.junit.jupiter.api.Disabled;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class MainTest {

    @org.junit.jupiter.api.Test
    @Disabled
    void convertDecimalToBase() {
        assertEquals("10", Main.convertDecimalToBase(new BigDecimal(2), 2));
        assertEquals("1e", Main.convertDecimalToBase(new BigDecimal(64), 16));
    }

    @org.junit.jupiter.api.Test
    void convertBaseToDecimal() {
        assertEquals(new BigDecimal(2), Main.convertBaseToDecimal("10", 2));
        assertEquals(new BigDecimal(15), Main.convertBaseToDecimal("F", 16));
    }
}