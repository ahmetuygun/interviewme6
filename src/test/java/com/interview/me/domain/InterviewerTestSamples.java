package com.interview.me.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class InterviewerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Interviewer getInterviewerSample1() {
        return new Interviewer().id(1L);
    }

    public static Interviewer getInterviewerSample2() {
        return new Interviewer().id(2L);
    }

    public static Interviewer getInterviewerRandomSampleGenerator() {
        return new Interviewer().id(longCount.incrementAndGet());
    }
}
