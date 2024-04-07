package com.interview.me.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.interview.me.domain.enumeration.StatusType;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

/**
 * A Appointment.
 */
@Entity
@Table(name = "appointment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "day")
    private Integer day;

    @Column(name = "slot")
    private String slot;

    @Column(name = "appointment_uid")
    private String appointmentUid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "appointments" }, allowSetters = true)
    private Interviewer interviewer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "appointments" }, allowSetters = true)
    private Interviewee interviewee;


    @Column(name = "token", length = 1000)
    private String token;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Appointment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusType getStatus() {
        return this.status;
    }

    public Appointment status(StatusType status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Integer getYear() {
        return this.year;
    }

    public Appointment year(Integer year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return this.month;
    }

    public Appointment month(Integer month) {
        this.setMonth(month);
        return this;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return this.day;
    }

    public Appointment day(Integer day) {
        this.setDay(day);
        return this;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getSlot() {
        return this.slot;
    }

    public Appointment slot(String slot) {
        this.setSlot(slot);
        return this;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getAppointmentUid() {
        return this.appointmentUid;
    }

    public Appointment appointmentUid(String appointmentUid) {
        this.setAppointmentUid(appointmentUid);
        return this;
    }

    public void setAppointmentUid(String appointmentUid) {
        this.appointmentUid = appointmentUid;
    }

    public Interviewer getInterviewer() {
        return this.interviewer;
    }

    public void setInterviewer(Interviewer interviewer) {
        this.interviewer = interviewer;
    }

    public Appointment interviewer(Interviewer interviewer) {
        this.setInterviewer(interviewer);
        return this;
    }

    public Interviewee getInterviewee() {
        return this.interviewee;
    }

    public void setInterviewee(Interviewee interviewee) {
        this.interviewee = interviewee;
    }

    public Appointment interviewee(Interviewee interviewee) {
        this.setInterviewee(interviewee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appointment)) {
            return false;
        }
        return getId() != null && getId().equals(((Appointment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Appointment{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", year=" + getYear() +
            ", month=" + getMonth() +
            ", day=" + getDay() +
            ", slot='" + getSlot() + "'" +
            ", appointmentUid='" + getAppointmentUid() + "'" +
            "}";
    }
}
