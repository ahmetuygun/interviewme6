
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
    "total_years_education",
    "entries"
})
@Generated("jsonschema2pojo")
public class Education__1 implements Serializable
{

    @JsonProperty("total_years_education")
    private Object totalYearsEducation;
    @JsonProperty("entries")
    private List<Entry__2> entries;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
    private final static long serialVersionUID = -150140811813425330L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Education__1() {
    }

    /**
     *
     * @param entries
     * @param totalYearsEducation
     */
    public Education__1(Object totalYearsEducation, List<Entry__2> entries) {
        super();
        this.totalYearsEducation = totalYearsEducation;
        this.entries = entries;
    }

    @JsonProperty("total_years_education")
    public Object getTotalYearsEducation() {
        return totalYearsEducation;
    }

    @JsonProperty("total_years_education")
    public void setTotalYearsEducation(Object totalYearsEducation) {
        this.totalYearsEducation = totalYearsEducation;
    }

    @JsonProperty("entries")
    public List<Entry__2> getEntries() {
        return entries;
    }

    @JsonProperty("entries")
    public void setEntries(List<Entry__2> entries) {
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
        sb.append(Education__1 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("totalYearsEducation");
        sb.append('=');
        sb.append(((this.totalYearsEducation == null)?"<null>":this.totalYearsEducation));
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
        result = ((result* 31)+((this.entries == null)? 0 :this.entries.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.totalYearsEducation == null)? 0 :this.totalYearsEducation.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Education__1) == false) {
            return false;
        }
        Education__1 rhs = ((Education__1) other);
        return ((((this.entries == rhs.entries)||((this.entries!= null)&&this.entries.equals(rhs.entries)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.totalYearsEducation == rhs.totalYearsEducation)||((this.totalYearsEducation!= null)&&this.totalYearsEducation.equals(rhs.totalYearsEducation))));
    }

}
