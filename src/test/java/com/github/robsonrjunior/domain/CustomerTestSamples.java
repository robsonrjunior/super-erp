package com.github.robsonrjunior.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CustomerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Customer getCustomerSample1() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLegalName("legalName1");
        customer.setTradeName("tradeName1");
        customer.setTaxId("taxId1");
        customer.setEmail("email1");
        customer.setPhone("phone1");
        return customer;
    }

    public static Customer getCustomerSample2() {
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setLegalName("legalName2");
        customer.setTradeName("tradeName2");
        customer.setTaxId("taxId2");
        customer.setEmail("email2");
        customer.setPhone("phone2");
        return customer;
    }

    public static Customer getCustomerRandomSampleGenerator() {
        Customer customer = new Customer();
        customer.setId(longCount.incrementAndGet());
        customer.setLegalName(UUID.randomUUID().toString());
        customer.setTradeName(UUID.randomUUID().toString());
        customer.setTaxId(UUID.randomUUID().toString());
        customer.setEmail(UUID.randomUUID().toString());
        customer.setPhone(UUID.randomUUID().toString());
        return customer;
    }
}
