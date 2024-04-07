
package com.interview.me.cvparser;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
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
    "total_years_experience",
    "entries"
})
@Generated("jsonschema2pojo")
public class WorkExperience implements Serializable
{

    @JsonProperty("total_years_experience")
    private Object totalYearsExperience;
    @JsonProperty("entries")
    private List<Entry__1> entries;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
    private final static long serialVersionUID = -731468726638691182L;

    /**
     * No args constructor for use in serialization
     *
     */
    public WorkExperience() {
    }

    /**
     *
     * @param entries
     * @param totalYearsExperience
     */
    public WorkExperience(Object totalYearsExperience, List<Entry__1> entries) {
        super();
        this.totalYearsExperience = totalYearsExperience;
        this.entries = entries;
    }

    @JsonProperty("total_years_experience")
    public Object getTotalYearsExperience() {
        return totalYearsExperience;
    }

    @JsonProperty("total_years_experience")
    public void setTotalYearsExperience(Object totalYearsExperience) {
        this.totalYearsExperience = totalYearsExperience;
    }

    @JsonProperty("entries")
    public List<Entry__1> getEntries() {
        return entries;
    }

    @JsonProperty("entries")
    public void setEntries(List<Entry__1> entries) {
        this.entries = entries;
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
        sb.append(WorkExperience.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("totalYearsExperience");
        sb.append('=');
        sb.append(((this.totalYearsExperience == null)?"<null>":this.totalYearsExperience));
        sb.append(',');
        sb.append("entries");
        sb.append('=');
        sb.append(((this.entries == null)?"<null>":this.entries));
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
        result = ((result* 31)+((this.totalYearsExperience == null)? 0 :this.totalYearsExperience.hashCode()));
        result = ((result* 31)+((this.entries == null)? 0 :this.entries.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof WorkExperience) == false) {
            return false;
        }
        WorkExperience rhs = ((WorkExperience) other);
        return ((((this.totalYearsExperience == rhs.totalYearsExperience)||((this.totalYearsExperience!= null)&&this.totalYearsExperience.equals(rhs.totalYearsExperience)))&&((this.entries == rhs.entries)||((this.entries!= null)&&this.entries.equals(rhs.entries))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
