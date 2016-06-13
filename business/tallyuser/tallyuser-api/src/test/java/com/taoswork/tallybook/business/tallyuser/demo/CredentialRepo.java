package com.taoswork.tallybook.business.tallyuser.demo;

import com.taoswork.tallybook.business.tallyuser.PasswordSetSpec;
import com.taoswork.tallybook.business.tallyuser.UserCertificationService;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;

import java.util.Map;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
/**
 * Created by gaoyuan on 16-6-13.
 */
public class CredentialRepo implements UserCertificationService {
    private final Map<String, String> encodedPws = new HashMap<String, String>();

    public CredentialRepo() {
        passwordEncoder = new StandardPasswordEncoder("tally-VR(*GF05K*");
        setAlgorithm = "RSA";

        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(setAlgorithm);
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            setPublicKey = publicKey;
            setPrivateKey = privateKey;
        }catch(NoSuchAlgorithmException e){

        }
    }

    private final StandardPasswordEncoder passwordEncoder;

    private final String setAlgorithm;
    private RSAPublicKey setPublicKey;
    private RSAPrivateKey setPrivateKey;
    private int setVersion;


    @Override
    public PasswordSetSpec getPasswordSetSpec() {
        return new PasswordSetSpec(setPublicKey.getModulus(), setPublicKey.getPublicExponent(), setVersion);
    }

    @Override
    public boolean setPassword(String userId, String encryptedPassword) {
        return false;
    }

    @Override
    public boolean checkPassword(String userId, String rawPassword) {
        String encodedPw = encodedPws.getOrDefault(userId, "");
        return passwordEncoder.matches(rawPassword, encodedPw);
    }
}
