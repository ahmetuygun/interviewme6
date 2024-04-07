package com.interview.me.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.interview.me.domain.Interviewee} entity. This class is used
 * in {@link com.interview.me.web.rest.IntervieweeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /interviewees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntervieweeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter internalUserId;

    private LongFilter personalDetailId;

    private LongFilter membershipId;

    private LongFilter appointmentId;

    private Boolean distinct;

    public IntervieweeCriteria() {}

    public IntervieweeCriteria(IntervieweeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.internalUserId = other.internalUserId == null ? null : other.internalUserId.copy();
        this.personalDetailId = other.personalDetailId == null ? null : other.personalDetailId.copy();
        this.membershipId = other.membershipId == null ? null : other.membershipId.copy();
        this.appointmentId = other.appointmentId == null ? null : other.appointmentId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public IntervieweeCriteria copy() {
        return new IntervieweeCriteria(this);
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

    public LongFilter getMembershipId() {
        return membershipId;
    }

    public LongFilter membershipId() {
        if (membershipId == null) {
            membershipId = new LongFilter();
        }
        return membershipId;
    }

    public void setMembershipId(LongFilter membershipId) {
        this.membershipId = membershipId;
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
        final IntervieweeCriteria that = (IntervieweeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(internalUserId, that.internalUserId) &&
            Objects.equals(personalDetailId, that.personalDetailId) &&
            Objects.equals(membershipId, that.membershipId) &&
            Objects.equals(appointmentId, that.appointmentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, internalUserId, personalDetailId, membershipId, appointmentId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntervieweeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (internalUserId != null ? "internalUserId=" + internalUserId + ", " : "") +
            (personalDetailId != null ? "personalDetailId=" + personalDetailId + ", " : "") +
            (membershipId != null ? "membershipId=" + membershipId + ", " : "") +
            (appointmentId != null ? "appointmentId=" + appointmentId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
