package com.github.robsonrjunior.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CityTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static City getCitySample1() {
        City city = new City();
        city.setId(1L);
        city.setName("name1");
        return city;
    }

    public static City getCitySample2() {
        City city = new City();
        city.setId(2L);
        city.setName("name2");
        return city;
    }

    public static City getCityRandomSampleGenerator() {
        City city = new City();
        city.setId(longCount.incrementAndGet());
        city.setName(UUID.randomUUID().toString());
        return city;
    }
}
