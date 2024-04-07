package com.interview.me.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.interview.me.domain.Interviewer} entity. This class is used
 * in {@link com.interview.me.web.rest.InterviewerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /interviewers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InterviewerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter internalUserId;

    private LongFilter personalDetailId;

    private LongFilter subjectsId;

    private LongFilter slotsId;

    private LongFilter appointmentId;

    private Boolean distinct;

    public InterviewerCriteria() {}

    public InterviewerCriteria(InterviewerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.internalUserId = other.internalUserId == null ? null : other.internalUserId.copy();
        this.personalDetailId = other.personalDetailId == null ? null : other.personalDetailId.copy();
        this.subjectsId = other.subjectsId == null ? null : other.subjectsId.copy();
        this.slotsId = other.slotsId == null ? null : other.slotsId.copy();
        this.appointmentId = other.appointmentId == null ? null : other.appointmentId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InterviewerCriteria copy() {
        return new InterviewerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getInternalUserId() {
        return internalUserId;
    }

    public LongFilter internalUserId() {
        if (internalUserId == null) {
            internalUserId = new LongFilter();
        }
        return internalUserId;
    }

    public void setInternalUserId(LongFilter internalUserId) {
        this.internalUserId = internalUserId;
    }

    public LongFilter getPersonalDetailId() {
        return personalDetailId;
    }

    public LongFilter personalDetailId() {
        if (personalDetailId == null) {
            personalDetailId = new LongFilter();
        }
        return personalDetailId;
    }

    public void setPersonalDetailId(LongFilter personalDetailId) {
        this.personalDetailId = personalDetailId;
    }

    public LongFilter getSubjectsId() {
        return subjectsId;
    }

    public LongFilter subjectsId() {
        if (subjectsId == null) {
            subjectsId = new LongFilter();
        }
        return subjectsId;
    }

    public void setSubjectsId(LongFilter subjectsId) {
        this.subjectsId = subjectsId;
    }

    public LongFilter getSlotsId() {
        return slotsId;
    }

    public LongFilter slotsId() {
        if (slotsId == null) {
            slotsId = new LongFilter();
        }
        return slotsId;
    }

    public void setSlotsId(LongFilter slotsId) {
        this.slotsId = slotsId;
    }

    public LongFilter getAppointmentId() {
        return appointmentId;
    }

    public LongFilter appointmentId() {
        if (appointmentId == null) {
            appointmentId = new LongFilter();
        }
        return appointmentId;
    }

    public void setAppointmentId(LongFilter appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InterviewerCriteria that = (InterviewerCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(internalUserId, that.internalUserId) &&
            Objects.equals(personalDetailId, that.personalDetailId) &&
            Objects.equals(subjectsId, that.subjectsId) &&
            Objects.equals(slotsId, that.slotsId) &&
            Objects.equals(appointmentId, that.appointmentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, internalUserId, personalDetailId, subjectsId, slotsId, appointmentId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterviewerCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (internalUserId != null ? "internalUserId=" + internalUserId + ", " : "") +
            (personalDetailId != null ? "personalDetailId=" + personalDetailId + ", " : "") +
            (subjectsId != null ? "subjectsId=" + subjectsId + ", " : "") +
            (slotsId != null ? "slotsId=" + slotsId + ", " : "") +
            (appointmentId != null ? "appointmentId=" + appointmentId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
