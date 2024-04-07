
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
    "name",
    "address",
    "self_summary",
    "objective",
    "date_of_birth",
    "place_of_birth",
    "phones",
    "mails",
    "urls",
    "fax",
    "current_profession",
    "gender",
    "nationality",
    "martial_status",
    "current_salary"
})
@Generated("jsonschema2pojo")
public class PersonalInfos implements Serializable
{

    @JsonProperty("name")
    private Name name;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("self_summary")
    private String selfSummary;
    @JsonProperty("objective")
    private String objective;
    @JsonProperty("date_of_birth")
    private Object dateOfBirth;
    @JsonProperty("place_of_birth")
    private String placeOfBirth;
    @JsonProperty("phones")
    private List<String> phones;
    @JsonProperty("mails")
    private List<String> mails;
    @JsonProperty("urls")
    private List<String> urls;
    @JsonProperty("fax")
    private List<Object> fax;
    @JsonProperty("current_profession")
    private String currentProfession;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("nationality")
    private Object nationality;
    @JsonProperty("martial_status")
    private Object martialStatus;
    @JsonProperty("current_salary")
    private Object currentSalary;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
    private final static long serialVersionUID = 7535491822844124539L;

    /**
     * No args constructor for use in serialization
     *
     */
    public PersonalInfos() {
    }

    /**
     *
     * @param placeOfBirth
     * @param address
     * @param gender
     * @param currentSalary
     * @param phones
     * @param dateOfBirth
     * @param objective
     * @param selfSummary
     * @param mails
     * @param urls
     * @param nationality
     * @param name
     * @param fax
     * @param currentProfession
     * @param martialStatus
     */
    public PersonalInfos(Name name, Address address, String selfSummary, String objective, Object dateOfBirth, String placeOfBirth, List<String> phones, List<String> mails, List<String> urls, List<Object> fax, String currentProfession, String gender, Object nationality, Object martialStatus, Object currentSalary) {
        super();
        this.name = name;
        this.address = address;
        this.selfSummary = selfSummary;
        this.objective = objective;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.phones = phones;
        this.mails = mails;
        this.urls = urls;
        this.fax = fax;
        this.currentProfession = currentProfession;
        this.gender = gender;
        this.nationality = nationality;
        this.martialStatus = martialStatus;
        this.currentSalary = currentSalary;
    }

    @JsonProperty("name")
    public Name getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(Name name) {
        this.name = name;
    }

    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
    }

    @JsonProperty("self_summary")
    public String getSelfSummary() {
        return selfSummary;
    }

    @JsonProperty("self_summary")
    public void setSelfSummary(String selfSummary) {
        this.selfSummary = selfSummary;
    }

    @JsonProperty("objective")
    public String getObjective() {
        return objective;
    }

    @JsonProperty("objective")
    public void setObjective(String objective) {
        this.objective = objective;
    }

    @JsonProperty("date_of_birth")
    public Object getDateOfBirth() {
        return dateOfBirth;
    }

    @JsonProperty("date_of_birth")
    public void setDateOfBirth(Object dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @JsonProperty("place_of_birth")
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    @JsonProperty("place_of_birth")
    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    @JsonProperty("phones")
    public List<String> getPhones() {
        return phones;
    }

    @JsonProperty("phones")
    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    @JsonProperty("mails")
    public List<String> getMails() {
        return mails;
    }

    @JsonProperty("mails")
    public void setMails(List<String> mails) {
        this.mails = mails;
    }

    @JsonProperty("urls")
    public List<String> getUrls() {
        return urls;
        }

    @JsonProperty("urls")
    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    @JsonProperty("fax")
    public List<Object> getFax() {
        return fax;
    }

    @JsonProperty("fax")
    public void setFax(List<Object> fax) {
        this.fax = fax;
    }

    @JsonProperty("current_profession")
    public String getCurrentProfession() {
        return currentProfession;
    }

    @JsonProperty("current_profession")
    public void setCurrentProfession(String currentProfession) {
        this.currentProfession = currentProfession;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("nationality")
    public Object getNationality() {
        return nationality;
    }

    @JsonProperty("nationality")
    public void setNationality(Object nationality) {
        this.nationality = nationality;
    }

    @JsonProperty("martial_status")
    public Object getMartialStatus() {
        return martialStatus;
    }

    @JsonProperty("martial_status")
    public void setMartialStatus(Object martialStatus) {
        this.martialStatus = martialStatus;
    }

    @JsonProperty("current_salary")
    public Object getCurrentSalary() {
        return currentSalary;
    }

    @JsonProperty("current_salary")
    public void setCurrentSalary(Object currentSalary) {
        this.currentSalary = currentSalary;
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
        sb.append(PersonalInfos.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("address");
        sb.append('=');
        sb.append(((this.address == null)?"<null>":this.address));
        sb.append(',');
        sb.append("selfSummary");
        sb.append('=');
        sb.append(((this.selfSummary == null)?"<null>":this.selfSummary));
        sb.append(',');
        sb.append("objective");
        sb.append('=');
        sb.append(((this.objective == null)?"<null>":this.objective));
        sb.append(',');
        sb.append("dateOfBirth");
        sb.append('=');
        sb.append(((this.dateOfBirth == null)?"<null>":this.dateOfBirth));
        sb.append(',');
        sb.append("placeOfBirth");
        sb.append('=');
        sb.append(((this.placeOfBirth == null)?"<null>":this.placeOfBirth));
        sb.append(',');
        sb.append("phones");
        sb.append('=');
        sb.append(((this.phones == null)?"<null>":this.phones));
        sb.append(',');
        sb.append("mails");
        sb.append('=');
        sb.append(((this.mails == null)?"<null>":this.mails));
        sb.append(',');
        sb.append("urls");
        sb.append('=');
        sb.append(((this.urls == null)?"<null>":this.urls));
        sb.append(',');
        sb.append("fax");
        sb.append('=');
        sb.append(((this.fax == null)?"<null>":this.fax));
        sb.append(',');
        sb.append("currentProfession");
        sb.append('=');
        sb.append(((this.currentProfession == null)?"<null>":this.currentProfession));
        sb.append(',');
        sb.append("gender");
        sb.append('=');
        sb.append(((this.gender == null)?"<null>":this.gender));
        sb.append(',');
        sb.append("nationality");
        sb.append('=');
        sb.append(((this.nationality == null)?"<null>":this.nationality));
        sb.append(',');
        sb.append("martialStatus");
        sb.append('=');
        sb.append(((this.martialStatus == null)?"<null>":this.martialStatus));
        sb.append(',');
        sb.append("currentSalary");
        sb.append('=');
        sb.append(((this.currentSalary == null)?"<null>":this.currentSalary));
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
        result = ((result* 31)+((this.placeOfBirth == null)? 0 :this.placeOfBirth.hashCode()));
        result = ((result* 31)+((this.address == null)? 0 :this.address.hashCode()));
        result = ((result* 31)+((this.gender == null)? 0 :this.gender.hashCode()));
        result = ((result* 31)+((this.currentSalary == null)? 0 :this.currentSalary.hashCode()));
        result = ((result* 31)+((this.phones == null)? 0 :this.phones.hashCode()));
        result = ((result* 31)+((this.dateOfBirth == null)? 0 :this.dateOfBirth.hashCode()));
        result = ((result* 31)+((this.objective == null)? 0 :this.objective.hashCode()));
        result = ((result* 31)+((this.selfSummary == null)? 0 :this.selfSummary.hashCode()));
        result = ((result* 31)+((this.mails == null)? 0 :this.mails.hashCode()));
        result = ((result* 31)+((this.urls == null)? 0 :this.urls.hashCode()));
        result = ((result* 31)+((this.nationality == null)? 0 :this.nationality.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.fax == null)? 0 :this.fax.hashCode()));
        result = ((result* 31)+((this.currentProfession == null)? 0 :this.currentProfession.hashCode()));
        result = ((result* 31)+((this.martialStatus == null)? 0 :this.martialStatus.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PersonalInfos) == false) {
            return false;
        }
        PersonalInfos rhs = ((PersonalInfos) other);
        return (((((((((((((((((this.placeOfBirth == rhs.placeOfBirth)||((this.placeOfBirth!= null)&&this.placeOfBirth.equals(rhs.placeOfBirth)))&&((this.address == rhs.address)||((this.address!= null)&&this.address.equals(rhs.address))))&&((this.gender == rhs.gender)||((this.gender!= null)&&this.gender.equals(rhs.gender))))&&((this.currentSalary == rhs.currentSalary)||((this.currentSalary!= null)&&this.currentSalary.equals(rhs.currentSalary))))&&((this.phones == rhs.phones)||((this.phones!= null)&&this.phones.equals(rhs.phones))))&&((this.dateOfBirth == rhs.dateOfBirth)||((this.dateOfBirth!= null)&&this.dateOfBirth.equals(rhs.dateOfBirth))))&&((this.objective == rhs.objective)||((this.objective!= null)&&this.objective.equals(rhs.objective))))&&((this.selfSummary == rhs.selfSummary)||((this.selfSummary!= null)&&this.selfSummary.equals(rhs.selfSummary))))&&((this.mails == rhs.mails)||((this.mails!= null)&&this.mails.equals(rhs.mails))))&&((this.urls == rhs.urls)||((this.urls!= null)&&this.urls.equals(rhs.urls))))&&((this.nationality == rhs.nationality)||((this.nationality!= null)&&this.nationality.equals(rhs.nationality))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.fax == rhs.fax)||((this.fax!= null)&&this.fax.equals(rhs.fax))))&&((this.currentProfession == rhs.currentProfession)||((this.currentProfession!= null)&&this.currentProfession.equals(rhs.currentProfession))))&&((this.martialStatus == rhs.martialStatus)||((this.martialStatus!= null)&&this.martialStatus.equals(rhs.martialStatus))));
    }

}
