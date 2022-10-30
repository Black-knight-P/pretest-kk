package com.pretest.dott.common.utils;

import com.google.common.base.Charsets;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Key;

public class AES256Util {

    private static String iv;
    private static Key keySpec;

    public static String encrypt(String str) throws GeneralSecurityException {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] encrypted = c.doFinal(str.getBytes(Charsets.UTF_8));
        return new String(Base64.encodeBase64(encrypted));
    }

    public static String decrypt(String str) throws GeneralSecurityException {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] byteStr = Base64.decodeBase64(str.getBytes());
        return new String(c.doFinal(byteStr), Charsets.UTF_8);
    }

    protected static void init(final String injectKey) {
        iv = injectKey.substring(0, 16);
        byte[] keyBytes = new byte[16];
        byte[] b = injectKey.getBytes(Charsets.UTF_8);
        int len = b.length;
        if (len > keyBytes.length) {
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);
        keySpec = new SecretKeySpec(keyBytes, "AES");
    }


    @RequiredArgsConstructor
    @Configuration
    public static class AES256UtilsConfig {
        @Value("${dott.encrypt.key}")
        private String key;

        @Bean
        public InitializingBean aes265UtilInitializer() {
            return () -> AES256Util.init(key);
        }
    }
}
