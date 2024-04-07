
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
    "personal_infos",
    "education",
    "work_experience",
    "languages",
    "skills",
    "certifications",
    "courses",
    "publications",
    "interests"
})
@Generated("jsonschema2pojo")
public class ExtractedData__1 implements Serializable
{

    @JsonProperty("personal_infos")
    private PersonalInfos__1 personalInfos;
    @JsonProperty("education")
    private Education__1 education;
    @JsonProperty("work_experience")
    private WorkExperience__1 workExperience;
    @JsonProperty("languages")
    private List<Language__1> languages;
    @JsonProperty("skills")
    private List<Skill__1> skills;
    @JsonProperty("certifications")
    private List<Certification__1> certifications;
    @JsonProperty("courses")
    private List<Object> courses;
    @JsonProperty("publications")
    private List<Object> publications;
    @JsonProperty("interests")
    private List<Object> interests;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
    private final static long serialVersionUID = -8284081690381167592L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ExtractedData__1() {
    }

    /**
     *
     * @param skills
     * @param courses
     * @param education
     * @param languages
     * @param workExperience
     * @param personalInfos
     * @param certifications
     * @param interests
     * @param publications
     */
    public ExtractedData__1(PersonalInfos__1 personalInfos, Education__1 education, WorkExperience__1 workExperience, List<Language__1> languages, List<Skill__1> skills, List<Certification__1> certifications, List<Object> courses, List<Object> publications, List<Object> interests) {
        super();
        this.personalInfos = personalInfos;
        this.education = education;
        this.workExperience = workExperience;
        this.languages = languages;
        this.skills = skills;
        this.certifications = certifications;
        this.courses = courses;
        this.publications = publications;
        this.interests = interests;
    }

    @JsonProperty("personal_infos")
    public PersonalInfos__1 getPersonalInfos() {
        return personalInfos;
    }

    @JsonProperty("personal_infos")
    public void setPersonalInfos(PersonalInfos__1 personalInfos) {
        this.personalInfos = personalInfos;
    }

    @JsonProperty("education")
    public Education__1 getEducation() {
        return education;
    }

    @JsonProperty("education")
    public void setEducation(Education__1 education) {
        this.education = education;
    }

    @JsonProperty("work_experience")
    public WorkExperience__1 getWorkExperience() {
        return workExperience;
    }

    @JsonProperty("work_experience")
    public void setWorkExperience(WorkExperience__1 workExperience) {
        this.workExperience = workExperience;
    }

    @JsonProperty("languages")
    public List<Language__1> getLanguages() {
        return languages;
    }

    @JsonProperty("languages")
    public void setLanguages(List<Language__1> languages) {
        this.languages = languages;
    }

    @JsonProperty("skills")
    public List<Skill__1> getSkills() {
        return skills;
    }

    @JsonProperty("skills")
    public void setSkills(List<Skill__1> skills) {
        this.skills = skills;
    }

    @JsonProperty("certifications")
    public List<Certification__1> getCertifications() {
        return certifications;
    }

    @JsonProperty("certifications")
    public void setCertifications(List<Certification__1> certifications) {
        this.certifications = certifications;
    }

    @JsonProperty("courses")
    public List<Object> getCourses() {
        return courses;
    }

    @JsonProperty("courses")
    public void setCourses(List<Object> courses) {
        this.courses = courses;
    }

    @JsonProperty("publications")
    public List<Object> getPublications() {
        return publications;
    }

    @JsonProperty("publications")
    public void setPublications(List<Object> publications) {
        this.publications = publications;
    }

    @JsonProperty("interests")
    public List<Object> getInterests() {
        return interests;
    }

    @JsonProperty("interests")
    public void setInterests(List<Object> interests) {
        this.interests = interests;
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
        sb.append(ExtractedData__1 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("personalInfos");
        sb.append('=');
        sb.append(((this.personalInfos == null)?"<null>":this.personalInfos));
        sb.append(',');
        sb.append("education");
        sb.append('=');
        sb.append(((this.education == null)?"<null>":this.education));
        sb.append(',');
        sb.append("workExperience");
        sb.append('=');
        sb.append(((this.workExperience == null)?"<null>":this.workExperience));
        sb.append(',');
        sb.append("languages");
        sb.append('=');
        sb.append(((this.languages == null)?"<null>":this.languages));
        sb.append(',');
        sb.append("skills");
        sb.append('=');
        sb.append(((this.skills == null)?"<null>":this.skills));
        sb.append(',');
        sb.append("certifications");
        sb.append('=');
        sb.append(((this.certifications == null)?"<null>":this.certifications));
        sb.append(',');
        sb.append("courses");
        sb.append('=');
        sb.append(((this.courses == null)?"<null>":this.courses));
        sb.append(',');
        sb.append("publications");
        sb.append('=');
        sb.append(((this.publications == null)?"<null>":this.publications));
        sb.append(',');
        sb.append("interests");
        sb.append('=');
        sb.append(((this.interests == null)?"<null>":this.interests));
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
        result = ((result* 31)+((this.skills == null)? 0 :this.skills.hashCode()));
        result = ((result* 31)+((this.courses == null)? 0 :this.courses.hashCode()));
        result = ((result* 31)+((this.education == null)? 0 :this.education.hashCode()));
        result = ((result* 31)+((this.languages == null)? 0 :this.languages.hashCode()));
        result = ((result* 31)+((this.workExperience == null)? 0 :this.workExperience.hashCode()));
        result = ((result* 31)+((this.personalInfos == null)? 0 :this.personalInfos.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.certifications == null)? 0 :this.certifications.hashCode()));
        result = ((result* 31)+((this.interests == null)? 0 :this.interests.hashCode()));
        result = ((result* 31)+((this.publications == null)? 0 :this.publications.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ExtractedData__1) == false) {
            return false;
        }
        ExtractedData__1 rhs = ((ExtractedData__1) other);
        return (((((((((((this.skills == rhs.skills)||((this.skills!= null)&&this.skills.equals(rhs.skills)))&&((this.courses == rhs.courses)||((this.courses!= null)&&this.courses.equals(rhs.courses))))&&((this.education == rhs.education)||((this.education!= null)&&this.education.equals(rhs.education))))&&((this.languages == rhs.languages)||((this.languages!= null)&&this.languages.equals(rhs.languages))))&&((this.workExperience == rhs.workExperience)||((this.workExperience!= null)&&this.workExperience.equals(rhs.workExperience))))&&((this.personalInfos == rhs.personalInfos)||((this.personalInfos!= null)&&this.personalInfos.equals(rhs.personalInfos))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.certifications == rhs.certifications)||((this.certifications!= null)&&this.certifications.equals(rhs.certifications))))&&((this.interests == rhs.interests)||((this.interests!= null)&&this.interests.equals(rhs.interests))))&&((this.publications == rhs.publications)||((this.publications!= null)&&this.publications.equals(rhs.publications))));
    }

}
