package com.interview.me.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.interview.me.domain.EducationEntry} entity. This class is used
 * in {@link com.interview.me.web.rest.EducationEntryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /education-entries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EducationEntryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private StringFilter establishment;

    private StringFilter description;

    private DoubleFilter gpa;

    private StringFilter accreditation;

    private LongFilter personalDetailId;

    private Boolean distinct;

    public EducationEntryCriteria() {}

    public EducationEntryCriteria(EducationEntryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.establishment = other.establishment == null ? null : other.establishment.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.gpa = other.gpa == null ? null : other.gpa.copy();
        this.accreditation = other.accreditation == null ? null : other.accreditation.copy();
        this.personalDetailId = other.personalDetailId == null ? null : other.personalDetailId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EducationEntryCriteria copy() {
        return new EducationEntryCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public LocalDateFilter startDate() {
        if (startDate == null) {
            startDate = new LocalDateFilter();
        }
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public LocalDateFilter endDate() {
        if (endDate == null) {
            endDate = new LocalDateFilter();
        }
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public StringFilter getEstablishment() {
        return establishment;
    }

    public StringFilter establishment() {
        if (establishment == null) {
            establishment = new StringFilter();
        }
        return establishment;
    }

    public void setEstablishment(StringFilter establishment) {
        this.establishment = establishment;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public DoubleFilter getGpa() {
        return gpa;
    }

    public DoubleFilter gpa() {
        if (gpa == null) {
            gpa = new DoubleFilter();
        }
        return gpa;
    }

    public void setGpa(DoubleFilter gpa) {
        this.gpa = gpa;
    }

    public StringFilter getAccreditation() {
        return accreditation;
    }

    public StringFilter accreditation() {
        if (accreditation == null) {
            accreditation = new StringFilter();
        }
        return accreditation;
    }

    public void setAccreditation(StringFilter accreditation) {
        this.accreditation = accreditation;
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
        final EducationEntryCriteria that = (EducationEntryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(establishment, that.establishment) &&
            Objects.equals(description, that.description) &&
            Objects.equals(gpa, that.gpa) &&
            Objects.equals(accreditation, that.accreditation) &&
            Objects.equals(personalDetailId, that.personalDetailId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, startDate, endDate, establishment, description, gpa, accreditation, personalDetailId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EducationEntryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (establishment != null ? "establishment=" + establishment + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (gpa != null ? "gpa=" + gpa + ", " : "") +
            (accreditation != null ? "accreditation=" + accreditation + ", " : "") +
            (personalDetailId != null ? "personalDetailId=" + personalDetailId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
