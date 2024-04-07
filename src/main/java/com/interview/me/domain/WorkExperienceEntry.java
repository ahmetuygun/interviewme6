package com.interview.me.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkExperienceEntry.
 */
@Entity
@Table(name = "work_experience_entry")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkExperienceEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "company")
    private String company;

    @Column(name = "description")
    private String description;

    @Column(name = "industry")
    private String industry;

    @Column(name = "formatted_location")
    private String formattedLocation;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "region")
    private String region;

    @Column(name = "country")
    private String country;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "raw_input_location")
    private String rawInputLocation;

    @Column(name = "street")
    private String street;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "apartment_number")
    private String apartmentNumber;

    @Column(name = "city")
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "works", "educations", "languages", "skills", "interviewer", "interviewee" }, allowSetters = true)
    private PersonalDetail personalDetail;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WorkExperienceEntry id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public WorkExperienceEntry title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public WorkExperienceEntry startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public WorkExperienceEntry endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getCompany() {
        return this.company;
    }

    public WorkExperienceEntry company(String company) {
        this.setCompany(company);
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return this.description;
    }

    public WorkExperienceEntry description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIndustry() {
        return this.industry;
    }

    public WorkExperienceEntry industry(String industry) {
        this.setIndustry(industry);
        return this;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getFormattedLocation() {
        return this.formattedLocation;
    }

    public WorkExperienceEntry formattedLocation(String formattedLocation) {
        this.setFormattedLocation(formattedLocation);
        return this;
    }

    public void setFormattedLocation(String formattedLocation) {
        this.formattedLocation = formattedLocation;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public WorkExperienceEntry postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getRegion() {
        return this.region;
    }

    public WorkExperienceEntry region(String region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return this.country;
    }

    public WorkExperienceEntry country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public WorkExperienceEntry countryCode(String countryCode) {
        this.setCountryCode(countryCode);
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRawInputLocation() {
        return this.rawInputLocation;
    }

    public WorkExperienceEntry rawInputLocation(String rawInputLocation) {
        this.setRawInputLocation(rawInputLocation);
        return this;
    }

    public void setRawInputLocation(String rawInputLocation) {
        this.rawInputLocation = rawInputLocation;
    }

    public String getStreet() {
        return this.street;
    }

    public WorkExperienceEntry street(String street) {
        this.setStreet(street);
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return this.streetNumber;
    }

    public WorkExperienceEntry streetNumber(String streetNumber) {
        this.setStreetNumber(streetNumber);
        return this;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getApartmentNumber() {
        return this.apartmentNumber;
    }

    public WorkExperienceEntry apartmentNumber(String apartmentNumber) {
        this.setApartmentNumber(apartmentNumber);
        return this;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getCity() {
        return this.city;
    }

    public WorkExperienceEntry city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public PersonalDetail getPersonalDetail() {
        return this.personalDetail;
    }

    public void setPersonalDetail(PersonalDetail personalDetail) {
        this.personalDetail = personalDetail;
    }

    public WorkExperienceEntry personalDetail(PersonalDetail personalDetail) {
        this.setPersonalDetail(personalDetail);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkExperienceEntry)) {
            return false;
        }
        return getId() != null && getId().equals(((WorkExperienceEntry) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkExperienceEntry{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", company='" + getCompany() + "'" +
            ", description='" + getDescription() + "'" +
            ", industry='" + getIndustry() + "'" +
            ", formattedLocation='" + getFormattedLocation() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", region='" + getRegion() + "'" +
            ", country='" + getCountry() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", rawInputLocation='" + getRawInputLocation() + "'" +
            ", street='" + getStreet() + "'" +
            ", streetNumber='" + getStreetNumber() + "'" +
            ", apartmentNumber='" + getApartmentNumber() + "'" +
            ", city='" + getCity() + "'" +
            "}";
    }
}
