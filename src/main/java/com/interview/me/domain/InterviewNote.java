package com.interview.me.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InterviewNote.
 */
@Entity
@Table(name = "interview_note")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InterviewNote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "note_text")
    private String noteText;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "action_items")
    private String actionItems;

    @Column(name = "feedback")
    private String feedback;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InterviewNote id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoteText() {
        return this.noteText;
    }

    public InterviewNote noteText(String noteText) {
        this.setNoteText(noteText);
        return this;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public Integer getRating() {
        return this.rating;
    }

    public InterviewNote rating(Integer rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getActionItems() {
        return this.actionItems;
    }

    public InterviewNote actionItems(String actionItems) {
        this.setActionItems(actionItems);
        return this;
    }

    public void setActionItems(String actionItems) {
        this.actionItems = actionItems;
    }

    public String getFeedback() {
        return this.feedback;
    }

    public InterviewNote feedback(String feedback) {
        this.setFeedback(feedback);
        return this;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InterviewNote)) {
            return false;
        }
        return getId() != null && getId().equals(((InterviewNote) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterviewNote{" +
            "id=" + getId() +
            ", noteText='" + getNoteText() + "'" +
            ", rating=" + getRating() +
            ", actionItems='" + getActionItems() + "'" +
            ", feedback='" + getFeedback() + "'" +
            "}";
    }
}
