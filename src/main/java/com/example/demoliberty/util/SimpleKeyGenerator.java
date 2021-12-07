package com.example.demoliberty.util;

import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;
import java.security.Key;

@ApplicationScoped
public class SimpleKeyGenerator implements KeyGenerator {

    @Override
    public Key generateKey() {
        byte[] key = "simplekey".getBytes();
        return new SecretKeySpec(key, 0, key.length, "DES");
    }
}
