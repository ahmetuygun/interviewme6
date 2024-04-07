package com.interview.me.service.criteria;

import com.interview.me.domain.enumeration.StatusType;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Appointment} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.AppointmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /appointments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppointmentCriteria implements Serializable, Criteria {

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

    private StatusTypeFilter status;

    private IntegerFilter year;

    private IntegerFilter month;

    private IntegerFilter day;

    private StringFilter slot;

    private StringFilter appointmentUid;

    private LongFilter interviewerId;

    private LongFilter intervieweeId;

    private Boolean distinct;

    public AppointmentCriteria() {}

    public AppointmentCriteria(AppointmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.month = other.month == null ? null : other.month.copy();
        this.day = other.day == null ? null : other.day.copy();
        this.slot = other.slot == null ? null : other.slot.copy();
        this.appointmentUid = other.appointmentUid == null ? null : other.appointmentUid.copy();
        this.interviewerId = other.interviewerId == null ? null : other.interviewerId.copy();
        this.intervieweeId = other.intervieweeId == null ? null : other.intervieweeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AppointmentCriteria copy() {
        return new AppointmentCriteria(this);
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

    public IntegerFilter getYear() {
        return year;
    }

    public IntegerFilter year() {
        if (year == null) {
            year = new IntegerFilter();
        }
        return year;
    }

    public void setYear(IntegerFilter year) {
        this.year = year;
    }

    public IntegerFilter getMonth() {
        return month;
    }

    public IntegerFilter month() {
        if (month == null) {
            month = new IntegerFilter();
        }
        return month;
    }

    public void setMonth(IntegerFilter month) {
        this.month = month;
    }

    public IntegerFilter getDay() {
        return day;
    }

    public IntegerFilter day() {
        if (day == null) {
            day = new IntegerFilter();
        }
        return day;
    }

    public void setDay(IntegerFilter day) {
        this.day = day;
    }

    public StringFilter getSlot() {
        return slot;
    }

    public StringFilter slot() {
        if (slot == null) {
            slot = new StringFilter();
        }
        return slot;
    }

    public void setSlot(StringFilter slot) {
        this.slot = slot;
    }

    public StringFilter getAppointmentUid() {
        return appointmentUid;
    }

    public StringFilter appointmentUid() {
        if (appointmentUid == null) {
            appointmentUid = new StringFilter();
        }
        return appointmentUid;
    }

    public void setAppointmentUid(StringFilter appointmentUid) {
        this.appointmentUid = appointmentUid;
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
        final AppointmentCriteria that = (AppointmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(year, that.year) &&
            Objects.equals(month, that.month) &&
            Objects.equals(day, that.day) &&
            Objects.equals(slot, that.slot) &&
            Objects.equals(appointmentUid, that.appointmentUid) &&
            Objects.equals(interviewerId, that.interviewerId) &&
            Objects.equals(intervieweeId, that.intervieweeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, year, month, day, slot, appointmentUid, interviewerId, intervieweeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppointmentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (month != null ? "month=" + month + ", " : "") +
            (day != null ? "day=" + day + ", " : "") +
            (slot != null ? "slot=" + slot + ", " : "") +
            (appointmentUid != null ? "appointmentUid=" + appointmentUid + ", " : "") +
            (interviewerId != null ? "interviewerId=" + interviewerId + ", " : "") +
            (intervieweeId != null ? "intervieweeId=" + intervieweeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
