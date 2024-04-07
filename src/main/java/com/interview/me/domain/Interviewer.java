package com.interview.me.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Interviewer.
 */
@Entity
@Table(name = "interviewer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Interviewer implements Serializable {

    private static final long serialVersionUID = 1L;

    public Interviewer(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User internalUser;

    @JsonIgnoreProperties(value = { "works", "educations", "interviewer", "interviewee" }, allowSetters = true)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(unique = true)
    private PersonalDetail personalDetail;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "interviewer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "interviewer" }, allowSetters = true)
    private Set<InterviewSubject> subjects = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "interviewer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "interviewer" }, allowSetters = true)
    private Set<Slot> slots = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "interviewer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "interviewer", "interviewee" }, allowSetters = true)
    private Set<Appointment> appointments = new HashSet<>();

    @Lob
    @Column(name = "cv")
    private byte[] cv;

    @Column(name = "cv_content_type")
    private String cvContentType;



    public Interviewer() {

    }


    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Interviewer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Interviewer photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Interviewer photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public Interviewer internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public PersonalDetail getPersonalDetail() {
        return this.personalDetail;
    }

    public void setPersonalDetail(PersonalDetail personalDetail) {
        this.personalDetail = personalDetail;
    }

    public Interviewer personalDetail(PersonalDetail personalDetail) {
        this.setPersonalDetail(personalDetail);
        return this;
    }

    public Set<InterviewSubject> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(Set<InterviewSubject> interviewSubjects) {
        if (this.subjects != null) {
            this.subjects.forEach(i -> i.setInterviewer(null));
        }
        if (interviewSubjects != null) {
            interviewSubjects.forEach(i -> i.setInterviewer(this));
        }
        this.subjects = interviewSubjects;
    }

    public Interviewer subjects(Set<InterviewSubject> interviewSubjects) {
        this.setSubjects(interviewSubjects);
        return this;
    }

    public Interviewer addSubjects(InterviewSubject interviewSubject) {
        this.subjects.add(interviewSubject);
        interviewSubject.setInterviewer(this);
        return this;
    }

    public Interviewer removeSubjects(InterviewSubject interviewSubject) {
        this.subjects.remove(interviewSubject);
        interviewSubject.setInterviewer(null);
        return this;
    }

    public Set<Slot> getSlots() {
        return this.slots;
    }

    public void setSlots(Set<Slot> slots) {
        if (this.slots != null) {
            this.slots.forEach(i -> i.setInterviewer(null));
        }
        if (slots != null) {
            slots.forEach(i -> i.setInterviewer(this));
        }
        this.slots = slots;
    }

    public Interviewer slots(Set<Slot> slots) {
        this.setSlots(slots);
        return this;
    }

    public Interviewer addSlots(Slot slot) {
        this.slots.add(slot);
        slot.setInterviewer(this);
        return this;
    }

    public Interviewer removeSlots(Slot slot) {
        this.slots.remove(slot);
        slot.setInterviewer(null);
        return this;
    }

    public byte[] getCv() {
        return cv;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }

    public String getCvContentType() {
        return cvContentType;
    }

    public void setCvContentType(String cvContentType) {
        this.cvContentType = cvContentType;
    }


    public Set<Appointment> getAppointments() {
        return this.appointments;
    }

    public void setAppointments(Set<Appointment> interviewAppointments) {
        if (this.appointments != null) {
            this.appointments.forEach(i -> i.setInterviewer(null));
        }
        if (interviewAppointments != null) {
            interviewAppointments.forEach(i -> i.setInterviewer(this));
        }
        this.appointments = interviewAppointments;
    }

    public Interviewer appointments(Set<Appointment> interviewAppointments) {
        this.setAppointments(interviewAppointments);
        return this;
    }

    public Interviewer addAppointment(Appointment interviewAppointment) {
        this.appointments.add(interviewAppointment);
        interviewAppointment.setInterviewer(this);
        return this;
    }

    public Interviewer removeAppointment(Appointment interviewAppointment) {
        this.appointments.remove(interviewAppointment);
        interviewAppointment.setInterviewer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Interviewer)) {
            return false;
        }
        return getId() != null && getId().equals(((Interviewer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Interviewer{" +
            "id=" + getId() +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            "}";
    }
}
