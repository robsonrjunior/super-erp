package com.github.robsonrjunior.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TenantTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Tenant getTenantSample1() {
        Tenant tenant = new Tenant();
        tenant.setId(1L);
        tenant.setName("name1");
        tenant.setCode("code1");
        return tenant;
    }

    public static Tenant getTenantSample2() {
        Tenant tenant = new Tenant();
        tenant.setId(2L);
        tenant.setName("name2");
        tenant.setCode("code2");
        return tenant;
    }

    public static Tenant getTenantRandomSampleGenerator() {
        Tenant tenant = new Tenant();
        tenant.setId(longCount.incrementAndGet());
        tenant.setName(UUID.randomUUID().toString());
        tenant.setCode(UUID.randomUUID().toString());
        return tenant;
    }
}
