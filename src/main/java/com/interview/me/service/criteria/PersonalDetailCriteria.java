package com.interview.me.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.interview.me.domain.PersonalDetail} entity. This class is used
 * in {@link com.interview.me.web.rest.PersonalDetailResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /personal-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonalDetailCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter rawName;

    private StringFilter middle;

    private StringFilter title;

    private StringFilter prefix;

    private StringFilter suffix;

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

    private StringFilter selfSummary;

    private StringFilter objective;

    private InstantFilter dateOfBirth;

    private StringFilter placeOfBirth;

    private StringFilter phones;

    private StringFilter mails;

    private StringFilter urls;

    private StringFilter currentProfession;

    private StringFilter gender;

    private StringFilter nationality;

    private StringFilter martialStatus;

    private BigDecimalFilter currentSalary;

    private LongFilter workId;

    private LongFilter educationId;

    private LongFilter languageId;

    private LongFilter skillsId;

    private LongFilter interviewerId;

    private LongFilter intervieweeId;

    private Boolean distinct;

    public PersonalDetailCriteria() {}

    public PersonalDetailCriteria(PersonalDetailCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.rawName = other.rawName == null ? null : other.rawName.copy();
        this.middle = other.middle == null ? null : other.middle.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.prefix = other.prefix == null ? null : other.prefix.copy();
        this.suffix = other.suffix == null ? null : other.suffix.copy();
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
        this.selfSummary = other.selfSummary == null ? null : other.selfSummary.copy();
        this.objective = other.objective == null ? null : other.objective.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.placeOfBirth = other.placeOfBirth == null ? null : other.placeOfBirth.copy();
        this.phones = other.phones == null ? null : other.phones.copy();
        this.mails = other.mails == null ? null : other.mails.copy();
        this.urls = other.urls == null ? null : other.urls.copy();
        this.currentProfession = other.currentProfession == null ? null : other.currentProfession.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.nationality = other.nationality == null ? null : other.nationality.copy();
        this.martialStatus = other.martialStatus == null ? null : other.martialStatus.copy();
        this.currentSalary = other.currentSalary == null ? null : other.currentSalary.copy();
        this.workId = other.workId == null ? null : other.workId.copy();
        this.educationId = other.educationId == null ? null : other.educationId.copy();
        this.languageId = other.languageId == null ? null : other.languageId.copy();
        this.skillsId = other.skillsId == null ? null : other.skillsId.copy();
        this.interviewerId = other.interviewerId == null ? null : other.interviewerId.copy();
        this.intervieweeId = other.intervieweeId == null ? null : other.intervieweeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PersonalDetailCriteria copy() {
        return new PersonalDetailCriteria(this);
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

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getRawName() {
        return rawName;
    }

    public StringFilter rawName() {
        if (rawName == null) {
            rawName = new StringFilter();
        }
        return rawName;
    }

    public void setRawName(StringFilter rawName) {
        this.rawName = rawName;
    }

    public StringFilter getMiddle() {
        return middle;
    }

    public StringFilter middle() {
        if (middle == null) {
            middle = new StringFilter();
        }
        return middle;
    }

    public void setMiddle(StringFilter middle) {
        this.middle = middle;
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

    public StringFilter getPrefix() {
        return prefix;
    }

    public StringFilter prefix() {
        if (prefix == null) {
            prefix = new StringFilter();
        }
        return prefix;
    }

    public void setPrefix(StringFilter prefix) {
        this.prefix = prefix;
    }

    public StringFilter getSuffix() {
        return suffix;
    }

    public StringFilter suffix() {
        if (suffix == null) {
            suffix = new StringFilter();
        }
        return suffix;
    }

    public void setSuffix(StringFilter suffix) {
        this.suffix = suffix;
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

    public StringFilter getSelfSummary() {
        return selfSummary;
    }

    public StringFilter selfSummary() {
        if (selfSummary == null) {
            selfSummary = new StringFilter();
        }
        return selfSummary;
    }

    public void setSelfSummary(StringFilter selfSummary) {
        this.selfSummary = selfSummary;
    }

    public StringFilter getObjective() {
        return objective;
    }

    public StringFilter objective() {
        if (objective == null) {
            objective = new StringFilter();
        }
        return objective;
    }

    public void setObjective(StringFilter objective) {
        this.objective = objective;
    }

    public InstantFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public InstantFilter dateOfBirth() {
        if (dateOfBirth == null) {
            dateOfBirth = new InstantFilter();
        }
        return dateOfBirth;
    }

    public void setDateOfBirth(InstantFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public StringFilter getPlaceOfBirth() {
        return placeOfBirth;
    }

    public StringFilter placeOfBirth() {
        if (placeOfBirth == null) {
            placeOfBirth = new StringFilter();
        }
        return placeOfBirth;
    }

    public void setPlaceOfBirth(StringFilter placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public StringFilter getPhones() {
        return phones;
    }

    public StringFilter phones() {
        if (phones == null) {
            phones = new StringFilter();
        }
        return phones;
    }

    public void setPhones(StringFilter phones) {
        this.phones = phones;
    }

    public StringFilter getMails() {
        return mails;
    }

    public StringFilter mails() {
        if (mails == null) {
            mails = new StringFilter();
        }
        return mails;
    }

    public void setMails(StringFilter mails) {
        this.mails = mails;
    }

    public StringFilter getUrls() {
        return urls;
    }

    public StringFilter urls() {
        if (urls == null) {
            urls = new StringFilter();
        }
        return urls;
    }

    public void setUrls(StringFilter urls) {
        this.urls = urls;
    }

    public StringFilter getCurrentProfession() {
        return currentProfession;
    }

    public StringFilter currentProfession() {
        if (currentProfession == null) {
            currentProfession = new StringFilter();
        }
        return currentProfession;
    }

    public void setCurrentProfession(StringFilter currentProfession) {
        this.currentProfession = currentProfession;
    }

    public StringFilter getGender() {
        return gender;
    }

    public StringFilter gender() {
        if (gender == null) {
            gender = new StringFilter();
        }
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public StringFilter getNationality() {
        return nationality;
    }

    public StringFilter nationality() {
        if (nationality == null) {
            nationality = new StringFilter();
        }
        return nationality;
    }

    public void setNationality(StringFilter nationality) {
        this.nationality = nationality;
    }

    public StringFilter getMartialStatus() {
        return martialStatus;
    }

    public StringFilter martialStatus() {
        if (martialStatus == null) {
            martialStatus = new StringFilter();
        }
        return martialStatus;
    }

    public void setMartialStatus(StringFilter martialStatus) {
        this.martialStatus = martialStatus;
    }

    public BigDecimalFilter getCurrentSalary() {
        return currentSalary;
    }

    public BigDecimalFilter currentSalary() {
        if (currentSalary == null) {
            currentSalary = new BigDecimalFilter();
        }
        return currentSalary;
    }

    public void setCurrentSalary(BigDecimalFilter currentSalary) {
        this.currentSalary = currentSalary;
    }

    public LongFilter getWorkId() {
        return workId;
    }

    public LongFilter workId() {
        if (workId == null) {
            workId = new LongFilter();
        }
        return workId;
    }

    public void setWorkId(LongFilter workId) {
        this.workId = workId;
    }

    public LongFilter getEducationId() {
        return educationId;
    }

    public LongFilter educationId() {
        if (educationId == null) {
            educationId = new LongFilter();
        }
        return educationId;
    }

    public void setEducationId(LongFilter educationId) {
        this.educationId = educationId;
    }

    public LongFilter getLanguageId() {
        return languageId;
    }

    public LongFilter languageId() {
        if (languageId == null) {
            languageId = new LongFilter();
        }
        return languageId;
    }

    public void setLanguageId(LongFilter languageId) {
        this.languageId = languageId;
    }

    public LongFilter getSkillsId() {
        return skillsId;
    }

    public LongFilter skillsId() {
        if (skillsId == null) {
            skillsId = new LongFilter();
        }
        return skillsId;
    }

    public void setSkillsId(LongFilter skillsId) {
        this.skillsId = skillsId;
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
        final PersonalDetailCriteria that = (PersonalDetailCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(rawName, that.rawName) &&
            Objects.equals(middle, that.middle) &&
            Objects.equals(title, that.title) &&
            Objects.equals(prefix, that.prefix) &&
            Objects.equals(suffix, that.suffix) &&
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
            Objects.equals(selfSummary, that.selfSummary) &&
            Objects.equals(objective, that.objective) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(placeOfBirth, that.placeOfBirth) &&
            Objects.equals(phones, that.phones) &&
            Objects.equals(mails, that.mails) &&
            Objects.equals(urls, that.urls) &&
            Objects.equals(currentProfession, that.currentProfession) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(nationality, that.nationality) &&
            Objects.equals(martialStatus, that.martialStatus) &&
            Objects.equals(currentSalary, that.currentSalary) &&
            Objects.equals(workId, that.workId) &&
            Objects.equals(educationId, that.educationId) &&
            Objects.equals(languageId, that.languageId) &&
            Objects.equals(skillsId, that.skillsId) &&
            Objects.equals(interviewerId, that.interviewerId) &&
            Objects.equals(intervieweeId, that.intervieweeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            firstName,
            lastName,
            rawName,
            middle,
            title,
            prefix,
            suffix,
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
            selfSummary,
            objective,
            dateOfBirth,
            placeOfBirth,
            phones,
            mails,
            urls,
            currentProfession,
            gender,
            nationality,
            martialStatus,
            currentSalary,
            workId,
            educationId,
            languageId,
            skillsId,
            interviewerId,
            intervieweeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalDetailCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (rawName != null ? "rawName=" + rawName + ", " : "") +
            (middle != null ? "middle=" + middle + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (prefix != null ? "prefix=" + prefix + ", " : "") +
            (suffix != null ? "suffix=" + suffix + ", " : "") +
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
            (selfSummary != null ? "selfSummary=" + selfSummary + ", " : "") +
            (objective != null ? "objective=" + objective + ", " : "") +
            (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
            (placeOfBirth != null ? "placeOfBirth=" + placeOfBirth + ", " : "") +
            (phones != null ? "phones=" + phones + ", " : "") +
            (mails != null ? "mails=" + mails + ", " : "") +
            (urls != null ? "urls=" + urls + ", " : "") +
            (currentProfession != null ? "currentProfession=" + currentProfession + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (nationality != null ? "nationality=" + nationality + ", " : "") +
            (martialStatus != null ? "martialStatus=" + martialStatus + ", " : "") +
            (currentSalary != null ? "currentSalary=" + currentSalary + ", " : "") +
            (workId != null ? "workId=" + workId + ", " : "") +
            (educationId != null ? "educationId=" + educationId + ", " : "") +
            (languageId != null ? "languageId=" + languageId + ", " : "") +
            (skillsId != null ? "skillsId=" + skillsId + ", " : "") +
            (interviewerId != null ? "interviewerId=" + interviewerId + ", " : "") +
            (intervieweeId != null ? "intervieweeId=" + intervieweeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
