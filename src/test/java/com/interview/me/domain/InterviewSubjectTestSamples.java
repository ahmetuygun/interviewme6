package com.interview.me.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InterviewSubjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static InterviewSubject getInterviewSubjectSample1() {
        return new InterviewSubject().id(1L).name("name1").parent("parent1");
    }

    public static InterviewSubject getInterviewSubjectSample2() {
        return new InterviewSubject().id(2L).name("name2").parent("parent2");
    }

    public static InterviewSubject getInterviewSubjectRandomSampleGenerator() {
        return new InterviewSubject()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .parent(UUID.randomUUID().toString());
    }
}
