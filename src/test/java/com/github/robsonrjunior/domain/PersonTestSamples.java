package com.github.robsonrjunior.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PersonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Person getPersonSample1() {
        Person person = new Person();
        person.setId(1L);
        person.setFullName("fullName1");
        person.setCpf("cpf1");
        person.setEmail("email1");
        person.setPhone("phone1");
        return person;
    }

    public static Person getPersonSample2() {
        Person person = new Person();
        person.setId(2L);
        person.setFullName("fullName2");
        person.setCpf("cpf2");
        person.setEmail("email2");
        person.setPhone("phone2");
        return person;
    }

    public static Person getPersonRandomSampleGenerator() {
        Person person = new Person();
        person.setId(longCount.incrementAndGet());
        person.setFullName(UUID.randomUUID().toString());
        person.setCpf(UUID.randomUUID().toString());
        person.setEmail(UUID.randomUUID().toString());
        person.setPhone(UUID.randomUUID().toString());
        return person;
    }
}
