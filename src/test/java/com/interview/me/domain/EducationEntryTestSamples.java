package com.interview.me.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EducationEntryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static EducationEntry getEducationEntrySample1() {
        return new EducationEntry()
            .id(1L)
            .title("title1")
            .establishment("establishment1")
            .description("description1")
            .accreditation("accreditation1");
    }

    public static EducationEntry getEducationEntrySample2() {
        return new EducationEntry()
            .id(2L)
            .title("title2")
            .establishment("establishment2")
            .description("description2")
            .accreditation("accreditation2");
    }

    public static EducationEntry getEducationEntryRandomSampleGenerator() {
        return new EducationEntry()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .establishment(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .accreditation(UUID.randomUUID().toString());
    }
}
