package com.interview.me.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MembershipLevelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MembershipLevel getMembershipLevelSample1() {
        return new MembershipLevel().id(1L).name("name1").description("description1").sessionAmount(1);
    }

    public static MembershipLevel getMembershipLevelSample2() {
        return new MembershipLevel().id(2L).name("name2").description("description2").sessionAmount(2);
    }

    public static MembershipLevel getMembershipLevelRandomSampleGenerator() {
        return new MembershipLevel()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .sessionAmount(intCount.incrementAndGet());
    }
}
