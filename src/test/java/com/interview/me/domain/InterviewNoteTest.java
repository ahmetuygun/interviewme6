package com.interview.me.domain;

import static com.interview.me.domain.InterviewNoteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.interview.me.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InterviewNoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterviewNote.class);
        InterviewNote interviewNote1 = getInterviewNoteSample1();
        InterviewNote interviewNote2 = new InterviewNote();
        assertThat(interviewNote1).isNotEqualTo(interviewNote2);

        interviewNote2.setId(interviewNote1.getId());
        assertThat(interviewNote1).isEqualTo(interviewNote2);

        interviewNote2 = getInterviewNoteSample2();
        assertThat(interviewNote1).isNotEqualTo(interviewNote2);
    }
}
