package com.interview.me.service.criteria;

import com.interview.me.domain.enumeration.StatusType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.interview.me.domain.InterviewAppointment} entity. This class is used
 * in {@link com.interview.me.web.rest.InterviewAppointmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /interview-appointments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InterviewAppointmentCriteria implements Serializable, Criteria {

    /**
     * Class for filtering StatusType
     */
    public static class StatusTypeFilter extends Filter<StatusType> {

        public StatusTypeFilter() {}

        public StatusTypeFilter(StatusTypeFilter filter) {
            super(filter);
        }

        @Override
        public StatusTypeFilter copy() {
            return new StatusTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter startTime;

    private ZonedDateTimeFilter endTime;

    private StringFilter googleMeetUrl;

    private StatusTypeFilter status;

    private LongFilter interviewerId;

    private LongFilter intervieweeId;

    private Boolean distinct;

    public InterviewAppointmentCriteria() {}

    public InterviewAppointmentCriteria(InterviewAppointmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.googleMeetUrl = other.googleMeetUrl == null ? null : other.googleMeetUrl.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.interviewerId = other.interviewerId == null ? null : other.interviewerId.copy();
        this.intervieweeId = other.intervieweeId == null ? null : other.intervieweeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InterviewAppointmentCriteria copy() {
        return new InterviewAppointmentCriteria(this);
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

    public ZonedDateTimeFilter getStartTime() {
        return startTime;
    }

    public ZonedDateTimeFilter startTime() {
        if (startTime == null) {
            startTime = new ZonedDateTimeFilter();
        }
        return startTime;
    }

    public void setStartTime(ZonedDateTimeFilter startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTimeFilter getEndTime() {
        return endTime;
    }

    public ZonedDateTimeFilter endTime() {
        if (endTime == null) {
            endTime = new ZonedDateTimeFilter();
        }
        return endTime;
    }

    public void setEndTime(ZonedDateTimeFilter endTime) {
        this.endTime = endTime;
    }

    public StringFilter getGoogleMeetUrl() {
        return googleMeetUrl;
    }

    public StringFilter googleMeetUrl() {
        if (googleMeetUrl == null) {
            googleMeetUrl = new StringFilter();
        }
        return googleMeetUrl;
    }

    public void setGoogleMeetUrl(StringFilter googleMeetUrl) {
        this.googleMeetUrl = googleMeetUrl;
    }

    public StatusTypeFilter getStatus() {
        return status;
    }

    public StatusTypeFilter status() {
        if (status == null) {
            status = new StatusTypeFilter();
        }
        return status;
    }

    public void setStatus(StatusTypeFilter status) {
        this.status = status;
    }

    public LongFilter getInterviewerId() {
        return interviewerId;
    }

    public LongFilter interviewerId() {
        if (interviewerId == null) {
            interviewerId = new LongFilter();
        }
        return interviewerId;
    }

    public void setInterviewerId(LongFilter interviewerId) {
        this.interviewerId = interviewerId;
    }

    public LongFilter getIntervieweeId() {
        return intervieweeId;
    }

    public LongFilter intervieweeId() {
        if (intervieweeId == null) {
            intervieweeId = new LongFilter();
        }
        return intervieweeId;
    }

    public void setIntervieweeId(LongFilter intervieweeId) {
        this.intervieweeId = intervieweeId;
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
        final InterviewAppointmentCriteria that = (InterviewAppointmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(googleMeetUrl, that.googleMeetUrl) &&
            Objects.equals(status, that.status) &&
            Objects.equals(interviewerId, that.interviewerId) &&
            Objects.equals(intervieweeId, that.intervieweeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, endTime, googleMeetUrl, status, interviewerId, intervieweeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterviewAppointmentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (startTime != null ? "startTime=" + startTime + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (googleMeetUrl != null ? "googleMeetUrl=" + googleMeetUrl + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (interviewerId != null ? "interviewerId=" + interviewerId + ", " : "") +
            (intervieweeId != null ? "intervieweeId=" + intervieweeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
