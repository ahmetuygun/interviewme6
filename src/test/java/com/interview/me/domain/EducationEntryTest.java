package com.interview.me.domain;

import static com.interview.me.domain.EducationEntryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.interview.me.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EducationEntryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducationEntry.class);
        EducationEntry educationEntry1 = getEducationEntrySample1();
        EducationEntry educationEntry2 = new EducationEntry();
        assertThat(educationEntry1).isNotEqualTo(educationEntry2);

        educationEntry2.setId(educationEntry1.getId());
        assertThat(educationEntry1).isEqualTo(educationEntry2);

        educationEntry2 = getEducationEntrySample2();
        assertThat(educationEntry1).isNotEqualTo(educationEntry2);
    }


}
