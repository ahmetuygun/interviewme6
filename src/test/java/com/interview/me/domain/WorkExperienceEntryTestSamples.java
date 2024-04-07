package com.interview.me.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WorkExperienceEntryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static WorkExperienceEntry getWorkExperienceEntrySample1() {
        return new WorkExperienceEntry()
            .id(1L)
            .title("title1")
            .company("company1")
            .description("description1")
            .industry("industry1")
            .formattedLocation("formattedLocation1")
            .postalCode("postalCode1")
            .region("region1")
            .country("country1")
            .countryCode("countryCode1")
            .rawInputLocation("rawInputLocation1")
            .street("street1")
            .streetNumber("streetNumber1")
            .apartmentNumber("apartmentNumber1")
            .city("city1");
    }

    public static WorkExperienceEntry getWorkExperienceEntrySample2() {
        return new WorkExperienceEntry()
            .id(2L)
            .title("title2")
            .company("company2")
            .description("description2")
            .industry("industry2")
            .formattedLocation("formattedLocation2")
            .postalCode("postalCode2")
            .region("region2")
            .country("country2")
            .countryCode("countryCode2")
            .rawInputLocation("rawInputLocation2")
            .street("street2")
            .streetNumber("streetNumber2")
            .apartmentNumber("apartmentNumber2")
            .city("city2");
    }

    public static WorkExperienceEntry getWorkExperienceEntryRandomSampleGenerator() {
        return new WorkExperienceEntry()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .company(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .industry(UUID.randomUUID().toString())
            .formattedLocation(UUID.randomUUID().toString())
            .postalCode(UUID.randomUUID().toString())
            .region(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString())
            .countryCode(UUID.randomUUID().toString())
            .rawInputLocation(UUID.randomUUID().toString())
            .street(UUID.randomUUID().toString())
            .streetNumber(UUID.randomUUID().toString())
            .apartmentNumber(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString());
    }
}
