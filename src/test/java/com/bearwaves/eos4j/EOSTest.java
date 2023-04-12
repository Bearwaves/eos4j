package com.bearwaves.eos4j;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EOSTest {
    @Test
    void librariesLoadedCorrectly() {
        assertTrue(EOS.loadLibraries());
    }
}