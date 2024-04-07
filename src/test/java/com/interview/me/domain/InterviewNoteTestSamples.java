package com.interview.me.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class InterviewNoteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static InterviewNote getInterviewNoteSample1() {
        return new InterviewNote().id(1L).noteText("noteText1").rating(1).actionItems("actionItems1").feedback("feedback1");
    }

    public static InterviewNote getInterviewNoteSample2() {
        return new InterviewNote().id(2L).noteText("noteText2").rating(2).actionItems("actionItems2").feedback("feedback2");
    }

    public static InterviewNote getInterviewNoteRandomSampleGenerator() {
        return new InterviewNote()
            .id(longCount.incrementAndGet())
            .noteText(UUID.randomUUID().toString())
            .rating(intCount.incrementAndGet())
            .actionItems(UUID.randomUUID().toString())
            .feedback(UUID.randomUUID().toString());
    }
}
