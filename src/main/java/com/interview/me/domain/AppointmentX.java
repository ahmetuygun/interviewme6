package com.interview.me.domain;

public class AppointmentX {
    private Long interviewerId;
    private Long intervieweeId;
    private DateStructure date;
    private String slot;

    // Getters and setters
    public Long getInterviewerId() {
        return interviewerId;
    }

    public void setInterviewerId(Long interviewerId) {
        this.interviewerId = interviewerId;
    }

    public Long getIntervieweeId() {
        return intervieweeId;
    }

    public void setIntervieweeId(Long intervieweeId) {
        this.intervieweeId = intervieweeId;
    }

    public DateStructure getDate() {
        return date;
    }

    public void setDate(DateStructure date) {
        this.date = date;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }
}

