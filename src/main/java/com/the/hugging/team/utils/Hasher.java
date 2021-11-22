package com.the.hugging.team.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Hasher {
    public static String hash(String text){
        return BCrypt.hashpw(text, BCrypt.gensalt());
    }

    public static boolean check(String text, String hashedText) {
        return BCrypt.checkpw(text, hashedText);
    }
}
