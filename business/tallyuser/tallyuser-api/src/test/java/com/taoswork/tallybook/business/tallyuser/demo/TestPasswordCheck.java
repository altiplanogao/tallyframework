package com.taoswork.tallybook.business.tallyuser.demo;

import com.taoswork.tallybook.business.tallyuser.*;
import java.util.HashMap;

import java.util.Map;
import org.junit.Test;


/**
 * Created by gaoyuan on 16-6-13.
 */
public class TestPasswordCheck {
    @Test
    public void testPasswordSettingChecking(){
        CredentialRepo cp = new CredentialRepo();
        Map<String, String> passwords = new HashMap<String, String>();
        passwords.put("aaa", "4576tg");
        passwords.put("bbb", "trdfvbj");
        passwords.put("ccc", "tvgh l");
        passwords.put("abc", "dch");
        passwords.put("bca", "oihnjh");
        passwords.put("cab", "rtc o");

        PasswordSetSpec sspec = cp.getPasswordSetSpec();

//        sspec.publicKey.
        for(Map.Entry<String, String> entry : passwords.entrySet()){
            //cp.setPassword(entry.getKey(), );
        }
    }
}
