
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
    "first_name",
    "last_name",
    "raw_name",
    "middle",
    "title",
    "prefix",
    "sufix"
})
@Generated("jsonschema2pojo")
public class Name implements Serializable
{

    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("raw_name")
    private String rawName;
    @JsonProperty("middle")
    private String middle;
    @JsonProperty("title")
    private String title;
    @JsonProperty("prefix")
    private Object prefix;
    @JsonProperty("sufix")
    private Object sufix;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
    private final static long serialVersionUID = 4855968482586612261L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Name() {
    }

    /**
     *
     * @param firstName
     * @param lastName
     * @param middle
     * @param prefix
     * @param sufix
     * @param title
     * @param rawName
     */
    public Name(String firstName, String lastName, String rawName, String middle, String title, Object prefix, Object sufix) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.rawName = rawName;
        this.middle = middle;
        this.title = title;
        this.prefix = prefix;
        this.sufix = sufix;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("raw_name")
    public String getRawName() {
        return rawName;
    }

    @JsonProperty("raw_name")
    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    @JsonProperty("middle")
    public String getMiddle() {
        return middle;
    }

    @JsonProperty("middle")
    public void setMiddle(String middle) {
        this.middle = middle;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("prefix")
    public Object getPrefix() {
        return prefix;
    }

    @JsonProperty("prefix")
    public void setPrefix(Object prefix) {
        this.prefix = prefix;
    }

    @JsonProperty("sufix")
    public Object getSufix() {
        return sufix;
    }

    @JsonProperty("sufix")
    public void setSufix(Object sufix) {
        this.sufix = sufix;
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
        sb.append(Name.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("firstName");
        sb.append('=');
        sb.append(((this.firstName == null)?"<null>":this.firstName));
        sb.append(',');
        sb.append("lastName");
        sb.append('=');
        sb.append(((this.lastName == null)?"<null>":this.lastName));
        sb.append(',');
        sb.append("rawName");
        sb.append('=');
        sb.append(((this.rawName == null)?"<null>":this.rawName));
        sb.append(',');
        sb.append("middle");
        sb.append('=');
        sb.append(((this.middle == null)?"<null>":this.middle));
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("prefix");
        sb.append('=');
        sb.append(((this.prefix == null)?"<null>":this.prefix));
        sb.append(',');
        sb.append("sufix");
        sb.append('=');
        sb.append(((this.sufix == null)?"<null>":this.sufix));
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
        result = ((result* 31)+((this.firstName == null)? 0 :this.firstName.hashCode()));
        result = ((result* 31)+((this.lastName == null)? 0 :this.lastName.hashCode()));
        result = ((result* 31)+((this.middle == null)? 0 :this.middle.hashCode()));
        result = ((result* 31)+((this.prefix == null)? 0 :this.prefix.hashCode()));
        result = ((result* 31)+((this.sufix == null)? 0 :this.sufix.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.title == null)? 0 :this.title.hashCode()));
        result = ((result* 31)+((this.rawName == null)? 0 :this.rawName.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Name) == false) {
            return false;
        }
        Name rhs = ((Name) other);
        return (((((((((this.firstName == rhs.firstName)||((this.firstName!= null)&&this.firstName.equals(rhs.firstName)))&&((this.lastName == rhs.lastName)||((this.lastName!= null)&&this.lastName.equals(rhs.lastName))))&&((this.middle == rhs.middle)||((this.middle!= null)&&this.middle.equals(rhs.middle))))&&((this.prefix == rhs.prefix)||((this.prefix!= null)&&this.prefix.equals(rhs.prefix))))&&((this.sufix == rhs.sufix)||((this.sufix!= null)&&this.sufix.equals(rhs.sufix))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.title == rhs.title)||((this.title!= null)&&this.title.equals(rhs.title))))&&((this.rawName == rhs.rawName)||((this.rawName!= null)&&this.rawName.equals(rhs.rawName))));
    }

}
