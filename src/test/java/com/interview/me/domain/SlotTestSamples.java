package com.interview.me.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SlotTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Slot getSlotSample1() {
        return new Slot().id(1L);
    }

    public static Slot getSlotSample2() {
        return new Slot().id(2L);
    }

    public static Slot getSlotRandomSampleGenerator() {
        return new Slot().id(longCount.incrementAndGet());
    }
}
