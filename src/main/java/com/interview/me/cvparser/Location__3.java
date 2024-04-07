
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
    "formatted_location",
    "postal_code",
    "region",
    "country",
    "country_code",
    "raw_input_location",
    "street",
    "street_number",
    "appartment_number",
    "city"
})
@Generated("jsonschema2pojo")
public class Location__3 implements Serializable
{

    @JsonProperty("formatted_location")
    private String formattedLocation;
    @JsonProperty("postal_code")
    private Object postalCode;
    @JsonProperty("region")
    private String region;
    @JsonProperty("country")
    private String country;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("raw_input_location")
    private String rawInputLocation;
    @JsonProperty("street")
    private Object street;
    @JsonProperty("street_number")
    private Object streetNumber;
    @JsonProperty("appartment_number")
    private Object appartmentNumber;
    @JsonProperty("city")
    private String city;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
    private final static long serialVersionUID = -5288214268702154612L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Location__3() {
    }

    /**
     *
     * @param country
     * @param streetNumber
     * @param city
     * @param countryCode
     * @param street
     * @param formattedLocation
     * @param postalCode
     * @param rawInputLocation
     * @param region
     * @param appartmentNumber
     */
    public Location__3(String formattedLocation, Object postalCode, String region, String country, String countryCode, String rawInputLocation, Object street, Object streetNumber, Object appartmentNumber, String city) {
        super();
        this.formattedLocation = formattedLocation;
        this.postalCode = postalCode;
        this.region = region;
        this.country = country;
        this.countryCode = countryCode;
        this.rawInputLocation = rawInputLocation;
        this.street = street;
        this.streetNumber = streetNumber;
        this.appartmentNumber = appartmentNumber;
        this.city = city;
    }

    @JsonProperty("formatted_location")
    public String getFormattedLocation() {
        return formattedLocation;
    }

    @JsonProperty("formatted_location")
    public void setFormattedLocation(String formattedLocation) {
        this.formattedLocation = formattedLocation;
    }

    @JsonProperty("postal_code")
    public Object getPostalCode() {
        return postalCode;
    }

    @JsonProperty("postal_code")
    public void setPostalCode(Object postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    @JsonProperty("region")
    public void setRegion(String region) {
        this.region = region;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("country_code")
    public String getCountryCode() {
        return countryCode;
    }

    @JsonProperty("country_code")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonProperty("raw_input_location")
    public String getRawInputLocation() {
        return rawInputLocation;
    }

    @JsonProperty("raw_input_location")
    public void setRawInputLocation(String rawInputLocation) {
        this.rawInputLocation = rawInputLocation;
    }

    @JsonProperty("street")
    public Object getStreet() {
        return street;
    }

    @JsonProperty("street")
    public void setStreet(Object street) {
        this.street = street;
    }

    @JsonProperty("street_number")
    public Object getStreetNumber() {
        return streetNumber;
    }

    @JsonProperty("street_number")
    public void setStreetNumber(Object streetNumber) {
        this.streetNumber = streetNumber;
    }

    @JsonProperty("appartment_number")
    public Object getAppartmentNumber() {
        return appartmentNumber;
    }

    @JsonProperty("appartment_number")
    public void setAppartmentNumber(Object appartmentNumber) {
        this.appartmentNumber = appartmentNumber;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
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
        sb.append(Location__3 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("formattedLocation");
        sb.append('=');
        sb.append(((this.formattedLocation == null)?"<null>":this.formattedLocation));
        sb.append(',');
        sb.append("postalCode");
        sb.append('=');
        sb.append(((this.postalCode == null)?"<null>":this.postalCode));
        sb.append(',');
        sb.append("region");
        sb.append('=');
        sb.append(((this.region == null)?"<null>":this.region));
        sb.append(',');
        sb.append("country");
        sb.append('=');
        sb.append(((this.country == null)?"<null>":this.country));
        sb.append(',');
        sb.append("countryCode");
        sb.append('=');
        sb.append(((this.countryCode == null)?"<null>":this.countryCode));
        sb.append(',');
        sb.append("rawInputLocation");
        sb.append('=');
        sb.append(((this.rawInputLocation == null)?"<null>":this.rawInputLocation));
        sb.append(',');
        sb.append("street");
        sb.append('=');
        sb.append(((this.street == null)?"<null>":this.street));
        sb.append(',');
        sb.append("streetNumber");
        sb.append('=');
        sb.append(((this.streetNumber == null)?"<null>":this.streetNumber));
        sb.append(',');
        sb.append("appartmentNumber");
        sb.append('=');
        sb.append(((this.appartmentNumber == null)?"<null>":this.appartmentNumber));
        sb.append(',');
        sb.append("city");
        sb.append('=');
        sb.append(((this.city == null)?"<null>":this.city));
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
        result = ((result* 31)+((this.country == null)? 0 :this.country.hashCode()));
        result = ((result* 31)+((this.streetNumber == null)? 0 :this.streetNumber.hashCode()));
        result = ((result* 31)+((this.city == null)? 0 :this.city.hashCode()));
        result = ((result* 31)+((this.countryCode == null)? 0 :this.countryCode.hashCode()));
        result = ((result* 31)+((this.street == null)? 0 :this.street.hashCode()));
        result = ((result* 31)+((this.formattedLocation == null)? 0 :this.formattedLocation.hashCode()));
        result = ((result* 31)+((this.postalCode == null)? 0 :this.postalCode.hashCode()));
        result = ((result* 31)+((this.rawInputLocation == null)? 0 :this.rawInputLocation.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.region == null)? 0 :this.region.hashCode()));
        result = ((result* 31)+((this.appartmentNumber == null)? 0 :this.appartmentNumber.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Location__3) == false) {
            return false;
        }
        Location__3 rhs = ((Location__3) other);
        return ((((((((((((this.country == rhs.country)||((this.country!= null)&&this.country.equals(rhs.country)))&&((this.streetNumber == rhs.streetNumber)||((this.streetNumber!= null)&&this.streetNumber.equals(rhs.streetNumber))))&&((this.city == rhs.city)||((this.city!= null)&&this.city.equals(rhs.city))))&&((this.countryCode == rhs.countryCode)||((this.countryCode!= null)&&this.countryCode.equals(rhs.countryCode))))&&((this.street == rhs.street)||((this.street!= null)&&this.street.equals(rhs.street))))&&((this.formattedLocation == rhs.formattedLocation)||((this.formattedLocation!= null)&&this.formattedLocation.equals(rhs.formattedLocation))))&&((this.postalCode == rhs.postalCode)||((this.postalCode!= null)&&this.postalCode.equals(rhs.postalCode))))&&((this.rawInputLocation == rhs.rawInputLocation)||((this.rawInputLocation!= null)&&this.rawInputLocation.equals(rhs.rawInputLocation))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.region == rhs.region)||((this.region!= null)&&this.region.equals(rhs.region))))&&((this.appartmentNumber == rhs.appartmentNumber)||((this.appartmentNumber!= null)&&this.appartmentNumber.equals(rhs.appartmentNumber))));
    }

}
