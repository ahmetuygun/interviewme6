
package com.interview.me.cvparser;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "title",
    "start_date",
    "end_date",
    "location",
    "establishment",
    "description",
    "gpa",
    "accreditation"
})
@Generated("jsonschema2pojo")
public class Entry__2 implements Serializable
{

    @JsonProperty("title")
    private Object title;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("end_date")
    private String endDate;
    @JsonProperty("location")
    private Location__2 location;
    @JsonProperty("establishment")
    private Object establishment;
    @JsonProperty("description")
    private Object description;
    @JsonProperty("gpa")
    private Object gpa;
    @JsonProperty("accreditation")
    private String accreditation;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
    private final static long serialVersionUID = -2358319353431316693L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Entry__2() {
    }

    /**
     *
     * @param endDate
     * @param description
     * @param gpa
     * @param location
     * @param establishment
     * @param accreditation
     * @param title
     * @param startDate
     */
    public Entry__2(Object title, String startDate, String endDate, Location__2 location, Object establishment, Object description, Object gpa, String accreditation) {
        super();
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.establishment = establishment;
        this.description = description;
        this.gpa = gpa;
        this.accreditation = accreditation;
    }

    @JsonProperty("title")
    public Object getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(Object title) {
        this.title = title;
    }

    @JsonProperty("start_date")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("start_date")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("end_date")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("end_date")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("location")
    public Location__2 getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(Location__2 location) {
        this.location = location;
    }

    @JsonProperty("establishment")
    public Object getEstablishment() {
        return establishment;
    }

    @JsonProperty("establishment")
    public void setEstablishment(Object establishment) {
        this.establishment = establishment;
    }

    @JsonProperty("description")
    public Object getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(Object description) {
        this.description = description;
    }

    @JsonProperty("gpa")
    public Object getGpa() {
        return gpa;
    }

    @JsonProperty("gpa")
    public void setGpa(Object gpa) {
        this.gpa = gpa;
    }

    @JsonProperty("accreditation")
    public String getAccreditation() {
        return accreditation;
    }

    @JsonProperty("accreditation")
    public void setAccreditation(String accreditation) {
        this.accreditation = accreditation;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Entry__2 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("startDate");
        sb.append('=');
        sb.append(((this.startDate == null)?"<null>":this.startDate));
        sb.append(',');
        sb.append("endDate");
        sb.append('=');
        sb.append(((this.endDate == null)?"<null>":this.endDate));
        sb.append(',');
        sb.append("location");
        sb.append('=');
        sb.append(((this.location == null)?"<null>":this.location));
        sb.append(',');
        sb.append("establishment");
        sb.append('=');
        sb.append(((this.establishment == null)?"<null>":this.establishment));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("gpa");
        sb.append('=');
        sb.append(((this.gpa == null)?"<null>":this.gpa));
        sb.append(',');
        sb.append("accreditation");
        sb.append('=');
        sb.append(((this.accreditation == null)?"<null>":this.accreditation));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.endDate == null)? 0 :this.endDate.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.gpa == null)? 0 :this.gpa.hashCode()));
        result = ((result* 31)+((this.location == null)? 0 :this.location.hashCode()));
        result = ((result* 31)+((this.establishment == null)? 0 :this.establishment.hashCode()));
        result = ((result* 31)+((this.accreditation == null)? 0 :this.accreditation.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.title == null)? 0 :this.title.hashCode()));
        result = ((result* 31)+((this.startDate == null)? 0 :this.startDate.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Entry__2) == false) {
            return false;
        }
        Entry__2 rhs = ((Entry__2) other);
        return ((((((((((this.endDate == rhs.endDate)||((this.endDate!= null)&&this.endDate.equals(rhs.endDate)))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.gpa == rhs.gpa)||((this.gpa!= null)&&this.gpa.equals(rhs.gpa))))&&((this.location == rhs.location)||((this.location!= null)&&this.location.equals(rhs.location))))&&((this.establishment == rhs.establishment)||((this.establishment!= null)&&this.establishment.equals(rhs.establishment))))&&((this.accreditation == rhs.accreditation)||((this.accreditation!= null)&&this.accreditation.equals(rhs.accreditation))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.title == rhs.title)||((this.title!= null)&&this.title.equals(rhs.title))))&&((this.startDate == rhs.startDate)||((this.startDate!= null)&&this.startDate.equals(rhs.startDate))));
    }

}
