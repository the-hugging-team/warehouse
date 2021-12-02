package com.the.hugging.team.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Hasher {
    public static String hash(String text) {
        return BCrypt.hashpw(text, BCrypt.gensalt());
    }

    public static boolean check(String text, String hashedText) {
        return BCrypt.checkpw(text, hashedText);
    }

    public static String generateRandomPassword(int from, int to) {
        StringBuilder password = new StringBuilder();
        int length = (int) (Math.random() * (to - from) + from);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }
}
