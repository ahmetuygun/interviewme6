
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
    "status",
    "extracted_data",
    "cost"
})
@Generated("jsonschema2pojo")
public class Affinda implements Serializable
{

    @JsonProperty("status")
    private String status;
    @JsonProperty("extracted_data")
    private ExtractedData extractedData;
    @JsonProperty("cost")
    private Double cost;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
    private final static long serialVersionUID = 7222092225924088042L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Affinda() {
    }

    /**
     *
     * @param cost
     * @param extractedData
     * @param status
     */
    public Affinda(String status, ExtractedData extractedData, Double cost) {
        super();
        this.status = status;
        this.extractedData = extractedData;
        this.cost = cost;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("extracted_data")
    public ExtractedData getExtractedData() {
        return extractedData;
    }

    @JsonProperty("extracted_data")
    public void setExtractedData(ExtractedData extractedData) {
        this.extractedData = extractedData;
    }

    @JsonProperty("cost")
    public Double getCost() {
        return cost;
    }

    @JsonProperty("cost")
    public void setCost(Double cost) {
        this.cost = cost;
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
        sb.append(Affinda.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null)?"<null>":this.status));
        sb.append(',');
        sb.append("extractedData");
        sb.append('=');
        sb.append(((this.extractedData == null)?"<null>":this.extractedData));
        sb.append(',');
        sb.append("cost");
        sb.append('=');
        sb.append(((this.cost == null)?"<null>":this.cost));
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
        result = ((result* 31)+((this.cost == null)? 0 :this.cost.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.extractedData == null)? 0 :this.extractedData.hashCode()));
        result = ((result* 31)+((this.status == null)? 0 :this.status.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Affinda) == false) {
            return false;
        }
        Affinda rhs = ((Affinda) other);
        return (((((this.cost == rhs.cost)||((this.cost!= null)&&this.cost.equals(rhs.cost)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.extractedData == rhs.extractedData)||((this.extractedData!= null)&&this.extractedData.equals(rhs.extractedData))))&&((this.status == rhs.status)||((this.status!= null)&&this.status.equals(rhs.status))));
    }

}
