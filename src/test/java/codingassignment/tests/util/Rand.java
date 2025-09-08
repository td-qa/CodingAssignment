package codingassignment.tests.util;

import java.security.SecureRandom;

public final class Rand {
    private static final char[] ALPHANUM =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final SecureRandom RNG = new SecureRandom();

    private Rand() {
    }

    public static String randomString(int len) {
        char[] out = new char[len];
        for (int i = 0; i < len; i++) out[i] = ALPHANUM[RNG.nextInt(ALPHANUM.length)];
        return new String(out);
    }
}
