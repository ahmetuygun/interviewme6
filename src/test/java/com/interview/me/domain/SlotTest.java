package com.interview.me.domain;

import static com.interview.me.domain.InterviewerTestSamples.*;
import static com.interview.me.domain.SlotTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.interview.me.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SlotTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Slot.class);
        Slot slot1 = getSlotSample1();
        Slot slot2 = new Slot();
        assertThat(slot1).isNotEqualTo(slot2);

        slot2.setId(slot1.getId());
        assertThat(slot1).isEqualTo(slot2);

        slot2 = getSlotSample2();
        assertThat(slot1).isNotEqualTo(slot2);
    }

    @Test
    void interviewerTest() throws Exception {
        Slot slot = getSlotRandomSampleGenerator();
        Interviewer interviewerBack = getInterviewerRandomSampleGenerator();

        slot.setInterviewer(interviewerBack);
        assertThat(slot.getInterviewer()).isEqualTo(interviewerBack);

        slot.interviewer(null);
        assertThat(slot.getInterviewer()).isNull();
    }
}
