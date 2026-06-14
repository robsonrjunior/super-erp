package com.github.robsonrjunior.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CompanyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Company getCompanySample1() {
        Company company = new Company();
        company.setId(1L);
        company.setLegalName("legalName1");
        company.setTradeName("tradeName1");
        company.setCnpj("cnpj1");
        company.setStateRegistration("stateRegistration1");
        company.setEmail("email1");
        company.setPhone("phone1");
        return company;
    }

    public static Company getCompanySample2() {
        Company company = new Company();
        company.setId(2L);
        company.setLegalName("legalName2");
        company.setTradeName("tradeName2");
        company.setCnpj("cnpj2");
        company.setStateRegistration("stateRegistration2");
        company.setEmail("email2");
        company.setPhone("phone2");
        return company;
    }

    public static Company getCompanyRandomSampleGenerator() {
        Company company = new Company();
        company.setId(longCount.incrementAndGet());
        company.setLegalName(UUID.randomUUID().toString());
        company.setTradeName(UUID.randomUUID().toString());
        company.setCnpj(UUID.randomUUID().toString());
        company.setStateRegistration(UUID.randomUUID().toString());
        company.setEmail(UUID.randomUUID().toString());
        company.setPhone(UUID.randomUUID().toString());
        return company;
    }
}
