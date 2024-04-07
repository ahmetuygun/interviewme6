package com.interview.me.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.interview.me.domain.WorkExperienceEntry} entity. This class is used
 * in {@link com.interview.me.web.rest.WorkExperienceEntryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /work-experience-entries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkExperienceEntryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private StringFilter company;

    private StringFilter description;

    private StringFilter industry;

    private StringFilter formattedLocation;

    private StringFilter postalCode;

    private StringFilter region;

    private StringFilter country;

    private StringFilter countryCode;

    private StringFilter rawInputLocation;

    private StringFilter street;

    private StringFilter streetNumber;

    private StringFilter apartmentNumber;

    private StringFilter city;

    private LongFilter personalDetailId;

    private Boolean distinct;

    public WorkExperienceEntryCriteria() {}

    public WorkExperienceEntryCriteria(WorkExperienceEntryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.company = other.company == null ? null : other.company.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.industry = other.industry == null ? null : other.industry.copy();
        this.formattedLocation = other.formattedLocation == null ? null : other.formattedLocation.copy();
        this.postalCode = other.postalCode == null ? null : other.postalCode.copy();
        this.region = other.region == null ? null : other.region.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.countryCode = other.countryCode == null ? null : other.countryCode.copy();
        this.rawInputLocation = other.rawInputLocation == null ? null : other.rawInputLocation.copy();
        this.street = other.street == null ? null : other.street.copy();
        this.streetNumber = other.streetNumber == null ? null : other.streetNumber.copy();
        this.apartmentNumber = other.apartmentNumber == null ? null : other.apartmentNumber.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.personalDetailId = other.personalDetailId == null ? null : other.personalDetailId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public WorkExperienceEntryCriteria copy() {
        return new WorkExperienceEntryCriteria(this);
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

    public InstantFilter getStartDate() {
        return startDate;
    }

    public InstantFilter startDate() {
        if (startDate == null) {
            startDate = new InstantFilter();
        }
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public InstantFilter endDate() {
        if (endDate == null) {
            endDate = new InstantFilter();
        }
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
    }

    public StringFilter getCompany() {
        return company;
    }

    public StringFilter company() {
        if (company == null) {
            company = new StringFilter();
        }
        return company;
    }

    public void setCompany(StringFilter company) {
        this.company = company;
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

    public StringFilter getIndustry() {
        return industry;
    }

    public StringFilter industry() {
        if (industry == null) {
            industry = new StringFilter();
        }
        return industry;
    }

    public void setIndustry(StringFilter industry) {
        this.industry = industry;
    }

    public StringFilter getFormattedLocation() {
        return formattedLocation;
    }

    public StringFilter formattedLocation() {
        if (formattedLocation == null) {
            formattedLocation = new StringFilter();
        }
        return formattedLocation;
    }

    public void setFormattedLocation(StringFilter formattedLocation) {
        this.formattedLocation = formattedLocation;
    }

    public StringFilter getPostalCode() {
        return postalCode;
    }

    public StringFilter postalCode() {
        if (postalCode == null) {
            postalCode = new StringFilter();
        }
        return postalCode;
    }

    public void setPostalCode(StringFilter postalCode) {
        this.postalCode = postalCode;
    }

    public StringFilter getRegion() {
        return region;
    }

    public StringFilter region() {
        if (region == null) {
            region = new StringFilter();
        }
        return region;
    }

    public void setRegion(StringFilter region) {
        this.region = region;
    }

    public StringFilter getCountry() {
        return country;
    }

    public StringFilter country() {
        if (country == null) {
            country = new StringFilter();
        }
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getCountryCode() {
        return countryCode;
    }

    public StringFilter countryCode() {
        if (countryCode == null) {
            countryCode = new StringFilter();
        }
        return countryCode;
    }

    public void setCountryCode(StringFilter countryCode) {
        this.countryCode = countryCode;
    }

    public StringFilter getRawInputLocation() {
        return rawInputLocation;
    }

    public StringFilter rawInputLocation() {
        if (rawInputLocation == null) {
            rawInputLocation = new StringFilter();
        }
        return rawInputLocation;
    }

    public void setRawInputLocation(StringFilter rawInputLocation) {
        this.rawInputLocation = rawInputLocation;
    }

    public StringFilter getStreet() {
        return street;
    }

    public StringFilter street() {
        if (street == null) {
            street = new StringFilter();
        }
        return street;
    }

    public void setStreet(StringFilter street) {
        this.street = street;
    }

    public StringFilter getStreetNumber() {
        return streetNumber;
    }

    public StringFilter streetNumber() {
        if (streetNumber == null) {
            streetNumber = new StringFilter();
        }
        return streetNumber;
    }

    public void setStreetNumber(StringFilter streetNumber) {
        this.streetNumber = streetNumber;
    }

    public StringFilter getApartmentNumber() {
        return apartmentNumber;
    }

    public StringFilter apartmentNumber() {
        if (apartmentNumber == null) {
            apartmentNumber = new StringFilter();
        }
        return apartmentNumber;
    }

    public void setApartmentNumber(StringFilter apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public StringFilter getCity() {
        return city;
    }

    public StringFilter city() {
        if (city == null) {
            city = new StringFilter();
        }
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
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
        final WorkExperienceEntryCriteria that = (WorkExperienceEntryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(company, that.company) &&
            Objects.equals(description, that.description) &&
            Objects.equals(industry, that.industry) &&
            Objects.equals(formattedLocation, that.formattedLocation) &&
            Objects.equals(postalCode, that.postalCode) &&
            Objects.equals(region, that.region) &&
            Objects.equals(country, that.country) &&
            Objects.equals(countryCode, that.countryCode) &&
            Objects.equals(rawInputLocation, that.rawInputLocation) &&
            Objects.equals(street, that.street) &&
            Objects.equals(streetNumber, that.streetNumber) &&
            Objects.equals(apartmentNumber, that.apartmentNumber) &&
            Objects.equals(city, that.city) &&
            Objects.equals(personalDetailId, that.personalDetailId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            startDate,
            endDate,
            company,
            description,
            industry,
            formattedLocation,
            postalCode,
            region,
            country,
            countryCode,
            rawInputLocation,
            street,
            streetNumber,
            apartmentNumber,
            city,
            personalDetailId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkExperienceEntryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (company != null ? "company=" + company + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (industry != null ? "industry=" + industry + ", " : "") +
            (formattedLocation != null ? "formattedLocation=" + formattedLocation + ", " : "") +
            (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
            (region != null ? "region=" + region + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (countryCode != null ? "countryCode=" + countryCode + ", " : "") +
            (rawInputLocation != null ? "rawInputLocation=" + rawInputLocation + ", " : "") +
            (street != null ? "street=" + street + ", " : "") +
            (streetNumber != null ? "streetNumber=" + streetNumber + ", " : "") +
            (apartmentNumber != null ? "apartmentNumber=" + apartmentNumber + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (personalDetailId != null ? "personalDetailId=" + personalDetailId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
