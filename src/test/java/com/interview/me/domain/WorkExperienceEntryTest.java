package com.interview.me.domain;

import static com.interview.me.domain.WorkExperienceEntryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.interview.me.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkExperienceEntryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkExperienceEntry.class);
        WorkExperienceEntry workExperienceEntry1 = getWorkExperienceEntrySample1();
        WorkExperienceEntry workExperienceEntry2 = new WorkExperienceEntry();
        assertThat(workExperienceEntry1).isNotEqualTo(workExperienceEntry2);

        workExperienceEntry2.setId(workExperienceEntry1.getId());
        assertThat(workExperienceEntry1).isEqualTo(workExperienceEntry2);

        workExperienceEntry2 = getWorkExperienceEntrySample2();
        assertThat(workExperienceEntry1).isNotEqualTo(workExperienceEntry2);
    }


}
