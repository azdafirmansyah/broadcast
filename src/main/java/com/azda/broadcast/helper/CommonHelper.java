package com.azda.broadcast.helper;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.RandomStringUtils;

public class CommonHelper {
    public static String hashedPassword (String plainPassword){
        String sha256hex = Hashing.sha256()
                .hashString(plainPassword, StandardCharsets.UTF_8)
                .toString();
        return sha256hex;
    }

    public static String generateUniqueKey (){
        String randomUnique = RandomStringUtils.randomAlphanumeric(100);
        return randomUnique;
    }
}
