
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
    "affinda",
    "eden-ai"
})
@Generated("jsonschema2pojo")
public class Example implements Serializable
{

    @JsonProperty("affinda")
    private Affinda affinda;
    @JsonProperty("eden-ai")
    private EdenAi edenAi;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
    private final static long serialVersionUID = 1474460705139564210L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Example() {
    }

    /**
     *
     * @param affinda
     * @param edenAi
     */
    public Example(Affinda affinda, EdenAi edenAi) {
        super();
        this.affinda = affinda;
        this.edenAi = edenAi;
    }

    @JsonProperty("affinda")
    public Affinda getAffinda() {
        return affinda;
    }

    @JsonProperty("affinda")
    public void setAffinda(Affinda affinda) {
        this.affinda = affinda;
    }

    @JsonProperty("eden-ai")
    public EdenAi getEdenAi() {
        return edenAi;
    }

    @JsonProperty("eden-ai")
    public void setEdenAi(EdenAi edenAi) {
        this.edenAi = edenAi;
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
        sb.append(Example.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("affinda");
        sb.append('=');
        sb.append(((this.affinda == null)?"<null>":this.affinda));
        sb.append(',');
        sb.append("edenAi");
        sb.append('=');
        sb.append(((this.edenAi == null)?"<null>":this.edenAi));
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
        result = ((result* 31)+((this.affinda == null)? 0 :this.affinda.hashCode()));
        result = ((result* 31)+((this.edenAi == null)? 0 :this.edenAi.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Example) == false) {
            return false;
        }
        Example rhs = ((Example) other);
        return ((((this.affinda == rhs.affinda)||((this.affinda!= null)&&this.affinda.equals(rhs.affinda)))&&((this.edenAi == rhs.edenAi)||((this.edenAi!= null)&&this.edenAi.equals(rhs.edenAi))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
