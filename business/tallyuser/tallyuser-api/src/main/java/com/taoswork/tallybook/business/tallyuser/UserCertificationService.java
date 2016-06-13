package com.taoswork.tallybook.business.tallyuser;

/**
 * Created by gaoyuan on 16-6-11.
 */
public interface UserCertificationService {

    PasswordSetSpec getPasswordSetSpec();

    boolean setPassword(String userId, String encryptedPassword);

    boolean checkPassword(String userId, String rawPassword);

}
