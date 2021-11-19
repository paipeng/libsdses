package com.paipeng.libsdses;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

class RdCardAPITest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void open() {
    }

    @Test
    void reEncoding() throws UnsupportedEncodingException {
        RdCardAPI rdCardAPI = new RdCardAPI();
        rdCardAPI.open();
    }
}