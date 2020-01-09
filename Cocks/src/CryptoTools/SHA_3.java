package CryptoTools;

import java.security.MessageDigest;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;

public class SHA_3 {

    public static String sha3(byte[] input){
        final DigestSHA3 sha3 = new SHA3.Digest512();

        sha3.update(input);

        return SHA_3.hashToString(sha3);
    }



    public static String sha3(final String input) {
        final DigestSHA3 sha3 = new SHA3.Digest512();

        sha3.update(input.getBytes());

        return SHA_3.hashToString(sha3);
    }

    public static String hashToString(MessageDigest hash) {
        return hashToString(hash.digest());
    }

    public static String hashToString(byte[] hash) {
        StringBuffer buff = new StringBuffer();

        for (byte b : hash) {
            buff.append(String.format("%02x", b & 0xFF));
        }

        return buff.toString();
    }


    public static byte[] hexStringToByteArray(String s) {
        if(s.equals("00"))
            return new byte[]{0};

        int len = s.length();
        byte[] data = new byte[len/2];

        for (int i = 0; i < len; i += 2) {
            data[i/2] = (byte) ((Character.digit(s.charAt(i), 16) * 16)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}



