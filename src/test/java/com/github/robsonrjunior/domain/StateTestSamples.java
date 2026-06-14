package com.github.robsonrjunior.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StateTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static State getStateSample1() {
        State state = new State();
        state.setId(1L);
        state.setName("name1");
        state.setCode("code1");
        return state;
    }

    public static State getStateSample2() {
        State state = new State();
        state.setId(2L);
        state.setName("name2");
        state.setCode("code2");
        return state;
    }

    public static State getStateRandomSampleGenerator() {
        State state = new State();
        state.setId(longCount.incrementAndGet());
        state.setName(UUID.randomUUID().toString());
        state.setCode(UUID.randomUUID().toString());
        return state;
    }
}
