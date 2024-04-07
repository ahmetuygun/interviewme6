package com.interview.me.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PersonalDetail.
 */
@Entity
@Table(name = "personal_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonalDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;


    @Column(name = "country")
    private String country;


    @Column(name = "city")
    private String city;

    @Column(name = "self_summary",  length = 1000)
    private String selfSummary;


    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "phones")
    private String phones;

    @Column(name = "mails")
    private String mails;

    @Column(name = "urls")
    private String urls;

    @Column(name = "current_profession")
    private String currentProfession;

    @Column(name = "gender")
    private String gender;

    @Column(name = "skills", length = 1000)
    private String skills;

    @Column(name = "languages")
    private String languages;

    @Column(name = "current_salary", precision = 21, scale = 2)
    private BigDecimal currentSalary;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "personalDetail", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "personalDetail" }, allowSetters = true)
    private Set<WorkExperienceEntry> works = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "personalDetail", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "personalDetail" }, allowSetters = true)
    private Set<EducationEntry> educations = new HashSet<>();



    @JsonIgnoreProperties(value = { "internalUser", "personalDetail", "subjects", "slots", "appointments" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "personalDetail")
    private Interviewer interviewer;

    @JsonIgnoreProperties(value = { "internalUser", "personalDetail", "membership", "appointments" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "personalDetail")
    private Interviewee interviewee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PersonalDetail id(Long id) {
        this.setId(id);
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getSkills() {
        return skills;
    }


    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }





    public String getCountry() {
        return this.country;
    }

    public PersonalDetail country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getCity() {
        return this.city;
    }

    public PersonalDetail city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSelfSummary() {
        return this.selfSummary;
    }

    public PersonalDetail selfSummary(String selfSummary) {
        this.setSelfSummary(selfSummary);
        return this;
    }

    public void setSelfSummary(String selfSummary) {
        this.selfSummary = selfSummary;
    }




    public Instant getDateOfBirth() {
        return this.dateOfBirth;
    }

    public PersonalDetail dateOfBirth(Instant dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return this.placeOfBirth;
    }

    public PersonalDetail placeOfBirth(String placeOfBirth) {
        this.setPlaceOfBirth(placeOfBirth);
        return this;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getPhones() {
        return this.phones;
    }

    public PersonalDetail phones(String phones) {
        this.setPhones(phones);
        return this;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getMails() {
        return this.mails;
    }

    public PersonalDetail mails(String mails) {
        this.setMails(mails);
        return this;
    }

    public void setMails(String mails) {
        this.mails = mails;
    }

    public String getUrls() {
        return this.urls;
    }

    public PersonalDetail urls(String urls) {
        this.setUrls(urls);
        return this;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public String getCurrentProfession() {
        return this.currentProfession;
    }

    public PersonalDetail currentProfession(String currentProfession) {
        this.setCurrentProfession(currentProfession);
        return this;
    }

    public void setCurrentProfession(String currentProfession) {
        this.currentProfession = currentProfession;
    }

    public String getGender() {
        return this.gender;
    }



    public void setGender(String gender) {
        this.gender = gender;
    }




    public BigDecimal getCurrentSalary() {
        return this.currentSalary;
    }



    public void setCurrentSalary(BigDecimal currentSalary) {
        this.currentSalary = currentSalary;
    }

    public Set<WorkExperienceEntry> getWorks() {
        return this.works;
    }

    public void setWorks(Set<WorkExperienceEntry> workExperienceEntries) {
        if (this.works != null) {
            this.works.forEach(i -> i.setPersonalDetail(null));
        }
        if (workExperienceEntries != null) {
            workExperienceEntries.forEach(i -> i.setPersonalDetail(this));
        }
        this.works = workExperienceEntries;
    }

    public PersonalDetail works(Set<WorkExperienceEntry> workExperienceEntries) {
        this.setWorks(workExperienceEntries);
        return this;
    }

    public PersonalDetail addWork(WorkExperienceEntry workExperienceEntry) {
        this.works.add(workExperienceEntry);
        workExperienceEntry.setPersonalDetail(this);
        return this;
    }

    public PersonalDetail removeWork(WorkExperienceEntry workExperienceEntry) {
        this.works.remove(workExperienceEntry);
        workExperienceEntry.setPersonalDetail(null);
        return this;
    }

    public Set<EducationEntry> getEducations() {
        return this.educations;
    }

    public void setEducations(Set<EducationEntry> educationEntries) {
        if (this.educations != null) {
            this.educations.forEach(i -> i.setPersonalDetail(null));
        }
        if (educationEntries != null) {
            educationEntries.forEach(i -> i.setPersonalDetail(this));
        }
        this.educations = educationEntries;
    }

    public PersonalDetail educations(Set<EducationEntry> educationEntries) {
        this.setEducations(educationEntries);
        return this;
    }

    public PersonalDetail addEducation(EducationEntry educationEntry) {
        this.educations.add(educationEntry);
        educationEntry.setPersonalDetail(this);
        return this;
    }

    public PersonalDetail removeEducation(EducationEntry educationEntry) {
        this.educations.remove(educationEntry);
        educationEntry.setPersonalDetail(null);
        return this;
    }

    public Interviewer getInterviewer() {
        return this.interviewer;
    }

    public void setInterviewer(Interviewer interviewer) {
        if (this.interviewer != null) {
            this.interviewer.setPersonalDetail(null);
        }
        if (interviewer != null) {
            interviewer.setPersonalDetail(this);
        }
        this.interviewer = interviewer;
    }

    public PersonalDetail interviewer(Interviewer interviewer) {
        this.setInterviewer(interviewer);
        return this;
    }

    public Interviewee getInterviewee() {
        return this.interviewee;
    }

    public void setInterviewee(Interviewee interviewee) {
        if (this.interviewee != null) {
            this.interviewee.setPersonalDetail(null);
        }
        if (interviewee != null) {
            interviewee.setPersonalDetail(this);
        }
        this.interviewee = interviewee;
    }

    public PersonalDetail interviewee(Interviewee interviewee) {
        this.setInterviewee(interviewee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalDetail)) {
            return false;
        }
        return getId() != null && getId().equals(((PersonalDetail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalDetail{" +
            "id=" + getId() +
            ", country='" + getCountry() + "'" +
            ", city='" + getCity() + "'" +
            ", selfSummary='" + getSelfSummary() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", placeOfBirth='" + getPlaceOfBirth() + "'" +
            ", phones='" + getPhones() + "'" +
            ", mails='" + getMails() + "'" +
            ", urls='" + getUrls() + "'" +
            ", currentProfession='" + getCurrentProfession() + "'" +
            ", gender='" + getGender() + "'" +

            ", currentSalary=" + getCurrentSalary() +
            "}";
    }
}
