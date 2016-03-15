package com.taoswork.tallybook.module.security;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Created by Gao Yuan on 2016/3/15.
 */
public class KeyUtility {
    /**
     *
     * @return
     */
    public static PublicKey getPubKey(String algorithm, String pubKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(
                    Base64.getDecoder().decode(pubKey));
            PublicKey publicKey = keyFactory.generatePublic(bobPubKeySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static PrivateKey getPrivateKey(String algorithm, String priKey) {
        try {
            KeyFactory keyf = KeyFactory.getInstance(algorithm);
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.getDecoder().decode(priKey));
            PrivateKey privateKey = keyf.generatePrivate(priPKCS8);
            return privateKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
}
