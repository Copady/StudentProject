package CocksScheme;

import CryptoTools.JacobiSymbol;
import CryptoTools.SHA_3;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateCrtKey;
//import org.bouncycastle.jcajce.provider.digest.SHA3;

public class PKG {
    private BigInteger p,q,n=null;
    private int sha3_type = 512,l = 2048;


    public PKG(){

    }

    public PKG(int l, int sha3_type ){
        this.l = l;
        this.sha3_type = sha3_type;
        try {
            RSAPrivateCrtKey privatekey = null;
            privatekey = generateKeyPair();
            System.out.println(privatekey);
            p = privatekey.getPrimeP();
            q = privatekey.getPrimeQ();
            n = privatekey.getModulus();

            while((p.mod(BigInteger.valueOf(4))).equals(BigInteger.valueOf(3)) == false || ((q.mod(BigInteger.valueOf(4))).equals(BigInteger.valueOf(3)) == false)){
                privatekey = generateKeyPair();
                p = privatekey.getPrimeP();
                q = privatekey.getPrimeQ();
                n = privatekey.getModulus();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RSAPrivateCrtKey generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(l, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
        RSAPrivateCrtKey private_key = (RSAPrivateCrtKey) pair.getPrivate();

        return private_key;
    }


    public BigInteger[] computeuserhash(String ID){

        if(ID.length()==0 || n==null) {
            System.out.println("Empty ID or nonexistent n");
            BigInteger[] e = new BigInteger[]{BigInteger.valueOf(-1),BigInteger.valueOf(-1)};
            return e;
        }

        JacobiSymbol symbol = new JacobiSymbol();
        String sha3 = SHA_3.sha3(ID);
        BigInteger hashedID = new BigInteger(sha3 , 16);
        byte[] temp = new byte[64];

        while(symbol.computeJacobiSymbol(hashedID,n)!=1){

            temp = SHA_3.hexStringToByteArray(sha3);
            sha3 = SHA_3.sha3(temp);
            hashedID = new BigInteger(sha3, 16);
        }


        BigInteger exp = n.add(BigInteger.valueOf(5));
        exp = exp.subtract(p.add(q));
        exp = exp.divide(BigInteger.valueOf(8));

        BigInteger r = hashedID.modPow(exp,n);
        BigInteger rsquare = r.modPow(BigInteger.TWO,n);


        if((rsquare.equals(hashedID)==false) && (rsquare.equals(n.subtract(hashedID))==false))
        {
            System.out.println("Error while computing key" + ID);
            BigInteger[] e = new BigInteger[]{BigInteger.valueOf(-1),BigInteger.valueOf(-1)};
            return e;
        }

        BigInteger[] k = new BigInteger[]{hashedID,r};

        return k;
}

    public BigInteger getn(){
        return n;
    }

    public int getSha3_type(){
        return sha3_type;
    }

    public int getl(){
        return l;
    }

}
