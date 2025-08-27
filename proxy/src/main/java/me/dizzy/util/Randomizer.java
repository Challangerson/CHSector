package me.dizzy.util;

import java.util.Random;

public class Randomizer {
    public static String generateRandomWord(int size) {
        String characterPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[]{}|;:,.<>?";

        Random random = new Random();
        StringBuilder randomWord = new StringBuilder();

        for (int i = 0; i < size; i++) {
            int index = random.nextInt(characterPool.length());
            randomWord.append(characterPool.charAt(index));
        }

        return randomWord.toString();
    }

    public static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
