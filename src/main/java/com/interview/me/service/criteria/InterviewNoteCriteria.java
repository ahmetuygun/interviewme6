package com.interview.me.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.interview.me.domain.InterviewNote} entity. This class is used
 * in {@link com.interview.me.web.rest.InterviewNoteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /interview-notes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InterviewNoteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter noteText;

    private IntegerFilter rating;

    private StringFilter actionItems;

    private StringFilter feedback;

    private Boolean distinct;

    public InterviewNoteCriteria() {}

    public InterviewNoteCriteria(InterviewNoteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.noteText = other.noteText == null ? null : other.noteText.copy();
        this.rating = other.rating == null ? null : other.rating.copy();
        this.actionItems = other.actionItems == null ? null : other.actionItems.copy();
        this.feedback = other.feedback == null ? null : other.feedback.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InterviewNoteCriteria copy() {
        return new InterviewNoteCriteria(this);
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

    public StringFilter getNoteText() {
        return noteText;
    }

    public StringFilter noteText() {
        if (noteText == null) {
            noteText = new StringFilter();
        }
        return noteText;
    }

    public void setNoteText(StringFilter noteText) {
        this.noteText = noteText;
    }

    public IntegerFilter getRating() {
        return rating;
    }

    public IntegerFilter rating() {
        if (rating == null) {
            rating = new IntegerFilter();
        }
        return rating;
    }

    public void setRating(IntegerFilter rating) {
        this.rating = rating;
    }

    public StringFilter getActionItems() {
        return actionItems;
    }

    public StringFilter actionItems() {
        if (actionItems == null) {
            actionItems = new StringFilter();
        }
        return actionItems;
    }

    public void setActionItems(StringFilter actionItems) {
        this.actionItems = actionItems;
    }

    public StringFilter getFeedback() {
        return feedback;
    }

    public StringFilter feedback() {
        if (feedback == null) {
            feedback = new StringFilter();
        }
        return feedback;
    }

    public void setFeedback(StringFilter feedback) {
        this.feedback = feedback;
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
        final InterviewNoteCriteria that = (InterviewNoteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(noteText, that.noteText) &&
            Objects.equals(rating, that.rating) &&
            Objects.equals(actionItems, that.actionItems) &&
            Objects.equals(feedback, that.feedback) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, noteText, rating, actionItems, feedback, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterviewNoteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (noteText != null ? "noteText=" + noteText + ", " : "") +
            (rating != null ? "rating=" + rating + ", " : "") +
            (actionItems != null ? "actionItems=" + actionItems + ", " : "") +
            (feedback != null ? "feedback=" + feedback + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
