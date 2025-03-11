package ej7;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ej4.MyTimer;

public class MyTimerTest {
    // Test constants
    private static final long ONE_DAY_MS = 86400000;
    private static final long ONE_HOUR_MS = 3600000;
    private static final long ONE_MINUTE_MS = 60000;
    private static final long ONE_SECOND_MS = 1000;

    private static final long TEST_START = 1000000;
    private static final long TEST_DURATION = ONE_DAY_MS + (2 * ONE_HOUR_MS) + (23 * ONE_SECOND_MS) + 40;
    private static final long TEST_END = TEST_START + TEST_DURATION;

    private MyTimer timerManual;

    @BeforeEach
    void setUp() {
        // Setup a timer with known values for consistent testing
        timerManual = new MyTimer(TEST_START);
        timerManual.stop(TEST_END);
    }

    @Test
    @DisplayName("Test automatic constructor and stop")
    void testAutomaticStartStop() {
        MyTimer timer = new MyTimer();
        // Small delay to ensure time passes
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            fail("Interrupted during sleep");
        }
        timer.stop();

        // Duration should be positive
        assertTrue(timer.getDurationMillis() > 0, "Duration should be positive after stopping timer");
    }

    @Test
    @DisplayName("Test manual constructor and stop")
    void testManualStartStop() {
        assertEquals(TEST_DURATION, timerManual.getDurationMillis(),
                "Manual timer duration should match expected value");
    }

    @Test
    @DisplayName("Test manual start and automatic stop")
    void testMixedStartStopA() {
        MyTimer timer = new MyTimer(TEST_START);
        // Small delay to ensure time passes
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            fail("Interrupted during sleep");
        }
        timer.stop();

        // Duration should be at least the difference between current time and TEST_START
        assertTrue(timer.getDurationMillis() > 0, "Duration should be positive after stopping timer");
    }

    @Test
    @DisplayName("Test automatic start and manual stop")
    void testMixedStartStopB() {
        MyTimer timer = new MyTimer();
        long current = System.currentTimeMillis();
        timer.stop(current + 5000); // 5 seconds in the future

        assertEquals(5000, timer.getDurationMillis(),
                "Duration should be 5000ms as specified in stop");
    }

    @Test
    @DisplayName("Test days calculation")
    void testGetDays() {
        assertEquals(1, timerManual.getDays(),
                "Days should be 1 for the test duration");
    }

    @Test
    @DisplayName("Test hours calculation")
    void testGetHours() {
        assertEquals(2, timerManual.getHours(),
                "Hours should be 2 for the test duration");
    }

    @Test
    @DisplayName("Test minutes calculation")
    void testGetMinutes() {
        assertEquals(0, timerManual.getMinutes(),
                "Minutes should be 0 for the test duration");
    }

    @Test
    @DisplayName("Test seconds calculation")
    void testGetSeconds() {
        // 23.04 seconds
        assertEquals(23.04, timerManual.getSeconds(), 0.001,
                "Seconds should be 23.04 for the test duration");
    }

    @Test
    @DisplayName("Test toString format")
    void testToString() {
        String expected = "(" + TEST_DURATION + " ms) 1 día 2 hs 0 min 23,040 s";
        assertEquals(expected, timerManual.toString(),
                "String representation should match expected format");
    }

    @Test
    @DisplayName("Test stop timer twice throws exception")
    void testStopTwice() {
        MyTimer timer = new MyTimer();
        timer.stop();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            timer.stop();
        });

        assertTrue(exception.getMessage().contains("already stopped"),
                "Exception message should indicate timer is already stopped");
    }

    @Test
    @DisplayName("Test stop with past time throws exception")
    void testStopWithPastTime() {
        MyTimer timer = new MyTimer();
        long start = System.currentTimeMillis() - 1000; // 1 second in the past

        Exception exception = assertThrows(RuntimeException.class, () -> {
            timer.stop(start);
        });

        assertTrue(exception.getMessage().contains("before start"),
                "Exception message should indicate end time is before start time");
    }

    @Test
    @DisplayName("Test operations on unstopped timer throw exceptions")
    void testUnstopped() {
        MyTimer timer = new MyTimer();

        assertThrows(RuntimeException.class, timer::getDurationMillis);
        assertThrows(RuntimeException.class, timer::getDays);
        assertThrows(RuntimeException.class, timer::getHours);
        assertThrows(RuntimeException.class, timer::getMinutes);
        assertThrows(RuntimeException.class, timer::getSeconds);
        assertThrows(RuntimeException.class, timer::toString);
    }

    @Test
    @DisplayName("Test zero duration")
    void testZeroDuration() {
        MyTimer timer = new MyTimer(1000);
        timer.stop(1000);

        assertEquals(0, timer.getDurationMillis());
        assertEquals(0, timer.getDays());
        assertEquals(0, timer.getHours());
        assertEquals(0, timer.getMinutes());
        assertEquals(0.0, timer.getSeconds(), 0.001);
        assertEquals("(0 ms) 0 días 0 hs 0 min 0,000 s", timer.toString());
    }

    @Test
    @DisplayName("Test multiple days")
    void testMultipleDays() {
        long twoAndHalfDays = (2 * ONE_DAY_MS) + (12 * ONE_HOUR_MS);
        MyTimer timer = new MyTimer(1000);
        timer.stop(1000 + twoAndHalfDays);

        assertEquals(2, timer.getDays());
        assertEquals(12, timer.getHours());
        assertEquals(0, timer.getMinutes());
        assertEquals(0.0, timer.getSeconds(), 0.001);
    }
}
