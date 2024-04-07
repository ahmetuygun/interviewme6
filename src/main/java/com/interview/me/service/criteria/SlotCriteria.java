package com.interview.me.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.interview.me.domain.Slot} entity. This class is used
 * in {@link com.interview.me.web.rest.SlotResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /slots?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SlotCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter slot;

    private BooleanFilter isAvailable;

    private LongFilter interviewerId;

    private Boolean distinct;

    public SlotCriteria() {}

    public SlotCriteria(SlotCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.slot = other.slot == null ? null : other.slot.copy();
        this.isAvailable = other.isAvailable == null ? null : other.isAvailable.copy();
        this.interviewerId = other.interviewerId == null ? null : other.interviewerId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SlotCriteria copy() {
        return new SlotCriteria(this);
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

    public ZonedDateTimeFilter getSlot() {
        return slot;
    }

    public ZonedDateTimeFilter slot() {
        if (slot == null) {
            slot = new ZonedDateTimeFilter();
        }
        return slot;
    }

    public void setSlot(ZonedDateTimeFilter slot) {
        this.slot = slot;
    }

    public BooleanFilter getIsAvailable() {
        return isAvailable;
    }

    public BooleanFilter isAvailable() {
        if (isAvailable == null) {
            isAvailable = new BooleanFilter();
        }
        return isAvailable;
    }

    public void setIsAvailable(BooleanFilter isAvailable) {
        this.isAvailable = isAvailable;
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
        final SlotCriteria that = (SlotCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(slot, that.slot) &&
            Objects.equals(isAvailable, that.isAvailable) &&
            Objects.equals(interviewerId, that.interviewerId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, slot, isAvailable, interviewerId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SlotCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (slot != null ? "slot=" + slot + ", " : "") +
            (isAvailable != null ? "isAvailable=" + isAvailable + ", " : "") +
            (interviewerId != null ? "interviewerId=" + interviewerId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
