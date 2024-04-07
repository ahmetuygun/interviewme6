package com.interview.me.domain;

import static com.interview.me.domain.InterviewSubjectTestSamples.*;
import static com.interview.me.domain.InterviewerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.interview.me.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InterviewSubjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterviewSubject.class);
        InterviewSubject interviewSubject1 = getInterviewSubjectSample1();
        InterviewSubject interviewSubject2 = new InterviewSubject();
        assertThat(interviewSubject1).isNotEqualTo(interviewSubject2);

        interviewSubject2.setId(interviewSubject1.getId());
        assertThat(interviewSubject1).isEqualTo(interviewSubject2);

        interviewSubject2 = getInterviewSubjectSample2();
        assertThat(interviewSubject1).isNotEqualTo(interviewSubject2);
    }

    @Test
    void interviewerTest() throws Exception {
        InterviewSubject interviewSubject = getInterviewSubjectRandomSampleGenerator();
        Interviewer interviewerBack = getInterviewerRandomSampleGenerator();

        interviewSubject.setInterviewer(interviewerBack);
        assertThat(interviewSubject.getInterviewer()).isEqualTo(interviewerBack);

        interviewSubject.interviewer(null);
        assertThat(interviewSubject.getInterviewer()).isNull();
    }
}
