package com.bearwaves.eos4j;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EOSTest {
    @BeforeAll
    static void beforeAll() throws EOSException {
        assertTrue(EOS.loadLibraries());
        Exception exception = assertThrows(EOSException.class, () -> {
            EOS.initialize(new EOS.InitializeOptions("", "1.0.0"));
        });
        assertEquals("EOS Error code: EOS_InvalidParameters", exception.getMessage());
        EOS.initialize(new EOS.InitializeOptions("product name", "1.0.0"));
    }

    @AfterAll
    static void afterAll() throws EOSException {
        EOS.shutdown();
    }

    @Test
    void createsAndReleasesCorrectly() throws EOSException {
        Exception exception = assertThrows(EOSException.class, () -> {
            new EOSPlatform(new EOSPlatform.Options("", "", "", "", ""));
        });
        assertEquals("Failed to create platform handle", exception.getMessage());

        EOSPlatform platform = new EOSPlatform(new EOSPlatform.Options(
                System.getenv("EOS4J_TEST_PRODUCT_ID"),
                System.getenv("EOS4J_TEST_SANDBOX_ID"),
                System.getenv("EOS4J_TEST_DEPLOYMENT_ID"),
                System.getenv("EOS4J_TEST_CLIENT_ID"),
                System.getenv("EOS4J_TEST_CLIENT_SECRET")
        ));

        EOSLogging.setLogLevel(EOSLogging.LogCategory.ALL_CATEGORIES, EOSLogging.LogLevel.VERY_VERBOSE);
        EOSLogging.setCallback(message -> System.out.println(message.category + ": " + message.message));

        platform.getAuthHandle();
        platform.release();
    }
}
