package com.github.robsonrjunior.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CountryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Country getCountrySample1() {
        Country country = new Country();
        country.setId(1L);
        country.setName("name1");
        country.setIsoCode("isoCode1");
        return country;
    }

    public static Country getCountrySample2() {
        Country country = new Country();
        country.setId(2L);
        country.setName("name2");
        country.setIsoCode("isoCode2");
        return country;
    }

    public static Country getCountryRandomSampleGenerator() {
        Country country = new Country();
        country.setId(longCount.incrementAndGet());
        country.setName(UUID.randomUUID().toString());
        country.setIsoCode(UUID.randomUUID().toString());
        return country;
    }
}
