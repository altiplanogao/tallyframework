package com.taoswork.tallybook.business.tallyuser;

import java.math.BigInteger;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.security.spec.RSAPublicKeySpec;
import java.security.KeyFactory;

/**
 * Created by gaoyuan on 16-6-13.
 */
final public class PasswordSetSpec {
    public PasswordSetSpec(BigInteger modulus, BigInteger exponent, int version) {
        publicKey = getPublicKey(modulus, exponent);
        this.version = version;
    }

    final public RSAPublicKey publicKey;

    final public int version;

    private static RSAPublicKey getPublicKey(BigInteger modulus, BigInteger exponent) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
