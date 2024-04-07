package com.interview.me.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MembershipLevel.
 */
@Entity
@Table(name = "membership_level")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MembershipLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "session_amount")
    private Integer sessionAmount;

    @Column(name = "price")
    private Double price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "membership")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "internalUser", "personalDetail", "membership", "appointments" }, allowSetters = true)
    private Set<Interviewee> interviewees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MembershipLevel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public MembershipLevel name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public MembershipLevel description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSessionAmount() {
        return this.sessionAmount;
    }

    public MembershipLevel sessionAmount(Integer sessionAmount) {
        this.setSessionAmount(sessionAmount);
        return this;
    }

    public void setSessionAmount(Integer sessionAmount) {
        this.sessionAmount = sessionAmount;
    }

    public Double getPrice() {
        return this.price;
    }

    public MembershipLevel price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Interviewee> getInterviewees() {
        return this.interviewees;
    }

    public void setInterviewees(Set<Interviewee> interviewees) {
        if (this.interviewees != null) {
            this.interviewees.forEach(i -> i.setMembership(null));
        }
        if (interviewees != null) {
            interviewees.forEach(i -> i.setMembership(this));
        }
        this.interviewees = interviewees;
    }

    public MembershipLevel interviewees(Set<Interviewee> interviewees) {
        this.setInterviewees(interviewees);
        return this;
    }

    public MembershipLevel addInterviewee(Interviewee interviewee) {
        this.interviewees.add(interviewee);
        interviewee.setMembership(this);
        return this;
    }

    public MembershipLevel removeInterviewee(Interviewee interviewee) {
        this.interviewees.remove(interviewee);
        interviewee.setMembership(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MembershipLevel)) {
            return false;
        }
        return getId() != null && getId().equals(((MembershipLevel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MembershipLevel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", sessionAmount=" + getSessionAmount() +
            ", price=" + getPrice() +
            "}";
    }
}
