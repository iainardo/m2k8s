package com.manning.liveproject.m2k8s.rest.api;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class ProfileTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Profile.class).verify();
    }
}
