package com.bearwaves.eos4j;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EOSTest {
    @BeforeAll
    static void beforeAll() {
        assertTrue(EOS.loadLibraries());
    }

    @Test
    void initializesCorrectly() throws EOSException {
        Exception exception = assertThrows(EOSException.class, () -> {
            EOS.initialize(new EOS.InitializeOptions("", "1.0.0"));
        });
        assertEquals("EOS Error code: EOS_InvalidParameters", exception.getMessage());
        EOS.initialize(new EOS.InitializeOptions("product name", "1.0.0"));
    }
}