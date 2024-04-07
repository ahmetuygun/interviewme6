package com.interview.me.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.interview.me.domain.InterviewSubject} entity. This class is used
 * in {@link com.interview.me.web.rest.InterviewSubjectResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /interview-subjects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InterviewSubjectCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter parent;

    private LongFilter interviewerId;

    private Boolean distinct;

    public InterviewSubjectCriteria() {}

    public InterviewSubjectCriteria(InterviewSubjectCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.parent = other.parent == null ? null : other.parent.copy();
        this.interviewerId = other.interviewerId == null ? null : other.interviewerId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InterviewSubjectCriteria copy() {
        return new InterviewSubjectCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getParent() {
        return parent;
    }

    public StringFilter parent() {
        if (parent == null) {
            parent = new StringFilter();
        }
        return parent;
    }

    public void setParent(StringFilter parent) {
        this.parent = parent;
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
        final InterviewSubjectCriteria that = (InterviewSubjectCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(parent, that.parent) &&
            Objects.equals(interviewerId, that.interviewerId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parent, interviewerId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterviewSubjectCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (parent != null ? "parent=" + parent + ", " : "") +
            (interviewerId != null ? "interviewerId=" + interviewerId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
