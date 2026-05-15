package com.github.robsonrjunior;

import com.github.robsonrjunior.config.AsyncSyncConfiguration;
import com.github.robsonrjunior.config.DatabaseTestcontainer;
import com.github.robsonrjunior.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = {
        SuperErpApp.class,
        JacksonConfiguration.class,
        AsyncSyncConfiguration.class,
        com.github.robsonrjunior.config.JacksonHibernateConfiguration.class,
    }
)
@ImportTestcontainers(DatabaseTestcontainer.class)
public @interface IntegrationTest {}
