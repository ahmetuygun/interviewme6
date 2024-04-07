package com.interview.me.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EducationEntry.
 */
@Entity
@Table(name = "education_entry")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EducationEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "establishment")
    private String establishment;

    @Column(name = "description")
    private String description;

    @Column(name = "gpa")
    private Double gpa;

    @Column(name = "accreditation")
    private String accreditation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "works", "educations", "languages", "skills", "interviewer", "interviewee" }, allowSetters = true)
    private PersonalDetail personalDetail;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EducationEntry id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public EducationEntry title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public EducationEntry startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public EducationEntry endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getEstablishment() {
        return this.establishment;
    }

    public EducationEntry establishment(String establishment) {
        this.setEstablishment(establishment);
        return this;
    }

    public void setEstablishment(String establishment) {
        this.establishment = establishment;
    }

    public String getDescription() {
        return this.description;
    }

    public EducationEntry description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getGpa() {
        return this.gpa;
    }

    public EducationEntry gpa(Double gpa) {
        this.setGpa(gpa);
        return this;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public String getAccreditation() {
        return this.accreditation;
    }

    public EducationEntry accreditation(String accreditation) {
        this.setAccreditation(accreditation);
        return this;
    }

    public void setAccreditation(String accreditation) {
        this.accreditation = accreditation;
    }

    public PersonalDetail getPersonalDetail() {
        return this.personalDetail;
    }

    public void setPersonalDetail(PersonalDetail personalDetail) {
        this.personalDetail = personalDetail;
    }

    public EducationEntry personalDetail(PersonalDetail personalDetail) {
        this.setPersonalDetail(personalDetail);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EducationEntry)) {
            return false;
        }
        return getId() != null && getId().equals(((EducationEntry) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EducationEntry{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", establishment='" + getEstablishment() + "'" +
            ", description='" + getDescription() + "'" +
            ", gpa=" + getGpa() +
            ", accreditation='" + getAccreditation() + "'" +
            "}";
    }
}
