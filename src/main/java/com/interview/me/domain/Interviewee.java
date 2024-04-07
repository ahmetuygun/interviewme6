package com.interview.me.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Interviewee.
 */
@Entity
@Table(name = "interviewee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Interviewee implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @JsonIgnoreProperties(value = { "works", "educations", "languages", "skills", "interviewer", "interviewee" }, allowSetters = true)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(unique = true)
    private PersonalDetail personalDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "interviewees" }, allowSetters = true)
    private MembershipLevel membership;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "interviewee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "interviewer", "interviewee" }, allowSetters = true)
    private Set<Appointment> appointments = new HashSet<>();

    @Lob
    @Column(name = "cv")
    private byte[] cv;

    @Column(name = "cv_content_type")
    private String cvContentType;

    public Interviewee(Long id) {
        this.id = id;
    }

    public Interviewee() {

    }

// jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Interviewee id(Long id) {
        this.setId(id);
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

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Interviewee photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Interviewee photoContentType(String photoContentType) {
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

    public Interviewee internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public PersonalDetail getPersonalDetail() {
        return this.personalDetail;
    }

    public void setPersonalDetail(PersonalDetail personalDetail) {
        this.personalDetail = personalDetail;
    }

    public Interviewee personalDetail(PersonalDetail personalDetail) {
        this.setPersonalDetail(personalDetail);
        return this;
    }

    public MembershipLevel getMembership() {
        return this.membership;
    }

    public void setMembership(MembershipLevel membershipLevel) {
        this.membership = membershipLevel;
    }

    public Interviewee membership(MembershipLevel membershipLevel) {
        this.setMembership(membershipLevel);
        return this;
    }

    public Set<Appointment> getAppointments() {
        return this.appointments;
    }

    public void setAppointments(Set<Appointment> interviewAppointments) {
        if (this.appointments != null) {
            this.appointments.forEach(i -> i.setInterviewee(null));
        }
        if (interviewAppointments != null) {
            interviewAppointments.forEach(i -> i.setInterviewee(this));
        }
        this.appointments = interviewAppointments;
    }

    public Interviewee appointments(Set<Appointment> interviewAppointments) {
        this.setAppointments(interviewAppointments);
        return this;
    }

    public Interviewee addAppointment(Appointment interviewAppointment) {
        this.appointments.add(interviewAppointment);
        interviewAppointment.setInterviewee(this);
        return this;
    }

    public Interviewee removeAppointment(Appointment interviewAppointment) {
        this.appointments.remove(interviewAppointment);
        interviewAppointment.setInterviewee(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Interviewee)) {
            return false;
        }
        return getId() != null && getId().equals(((Interviewee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Interviewee{" +
            "id=" + getId() +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            "}";
    }
}
