package CocksScheme;

import CryptoTools.JacobiSymbol;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class User {
    private String ID=null;
    private BigInteger private_key, hashedID;
    private PKG pkg;

    public User(String ID , PKG pkg){
        if(ID.length()==0 || pkg == null || pkg.getn() == null)
            System.out.println("Invalid ID or pkg");
        else
            {
                this.ID = ID;
                this.pkg = pkg;
                BigInteger[] k = pkg.computeuserhash(ID);
                private_key = k[1];
                hashedID = k[0];
            }
    }

    public BigInteger[] encrypt(User user, int m){

        if (((user.getID()).length()) == 0 || (m!=1 && m!=-1) ){
            System.out.println("CocksScheme.User or message not valid ");
            BigInteger[] d = new BigInteger[]{BigInteger.valueOf(-1),BigInteger.valueOf(-1)};
            return d;}
        else{

            if(this.pkg!=user.getPkg()){
                System.out.println("Users do not have the same CocksScheme.PKG!");
                BigInteger[] d = new BigInteger[]{BigInteger.valueOf(-1),BigInteger.valueOf(-1)};
                return d;
            }

        BigInteger t_1 = new BigInteger(512, new Random());
        BigInteger t_2 = new BigInteger(512, new Random());
        JacobiSymbol j = new JacobiSymbol();

        BigInteger n = pkg.getn();

        while(j.computeJacobiSymbol(t_1,n)!=m || j.computeJacobiSymbol(t_2,n)!=m ||t_1==t_2 ){
            t_1 = new BigInteger(512, new Random());
            t_2 = new BigInteger(512, new Random());

        }

        BigInteger c_1;
        BigInteger c_2;

        BigInteger userhash = user.getHashedID();
        if(userhash.equals(BigInteger.valueOf(-1)))
        {
            System.out.println("Wrong hash");
            BigInteger[] d = new BigInteger[]{BigInteger.valueOf(-1),BigInteger.valueOf(-1)};
            return d;

        }
        else{
        c_1 = t_1.add(userhash.multiply(t_1.modInverse(n)));
        c_2 = t_2.subtract(userhash.multiply(t_2.modInverse(n)));


        BigInteger[] e = new BigInteger[]{c_1,c_2};

        return e;}}

    }

    public int decrypt(BigInteger[] c){
        if(c==null){
            System.out.println("Invalid ciphertext");
            return -2;}

        BigInteger a;

        BigInteger n = pkg.getn();

        if(n==null||hashedID.equals(BigInteger.valueOf(-1))){
            System.out.println("Wrong hash or n");
            return -2;
        }


        if((private_key.modPow(BigInteger.TWO,n)).equals(hashedID))
            a = (c[0].add(private_key.multiply(BigInteger.TWO))).mod(n);
        else
            a =(c[1].add(private_key.multiply(BigInteger.TWO))).mod(n);
        JacobiSymbol j = new JacobiSymbol();

        return j.computeJacobiSymbol(a,n);

    }


    public BigInteger[] encryptlongmessage(User user, String message){
        if (user.getID() == null || (message.getBytes()).length>=8 || (message.getBytes()).length==0){
            System.out.println("CocksScheme.User or message not valid ");
            BigInteger[] d = new BigInteger[]{BigInteger.valueOf(-1)};
            return d;}

        if(user.getPkg()!=this.pkg)    {
            System.out.println("Users do not have the same CocksScheme.PKG!");
            BigInteger[] d = new BigInteger[]{BigInteger.valueOf(-1)};
            return d;
        }

        ArrayList<BigInteger> ciphertext = new ArrayList<BigInteger>();
        BigInteger[] c = new BigInteger[2];
        int len = (message.getBytes()).length;
        byte[] bytes = new byte[len];
        bytes = message.getBytes();
        for (byte b : bytes ){
            for (int i = 8; i >= 1; i--) {
                int p;
                if(((b & 0xff) & (1 << (i - 1))) >0)
                    p = 1;
                else
                    p = -1;

                c = encrypt(user,p);
                ciphertext.add(c[0]);
                ciphertext.add(c[1]);
            }
        }

        BigInteger[] C = new BigInteger[ciphertext.size()];
        C = ciphertext.toArray(C);
        return C;

    }

    public String decryptlongmessage(BigInteger[] ciphertext){
        if(ciphertext==null){
            System.out.println("Invalid ciphertext");
            String e = "-1";
            return e;
        }

        BigInteger n = pkg.getn();

        if(n==null||hashedID.equals(BigInteger.valueOf(-1))){
            System.out.println("Wrong hash or n");
            return "-1";
        }

        if(ciphertext.length %16 !=0){
            System.out.println("Wrong ciphertext");
            return "-1";
        }


        BigInteger c[] = new BigInteger[2];
        int a;
        int b;
        int p;
        StringBuilder s = new StringBuilder();

        for(int i=0;i<ciphertext.length-15;i=i+16){
            b=0;
            p = 7;
            for(int j = i; j<i+16; j=j+2){
                c[0]=ciphertext[j];
                c[1]=ciphertext[j+1];
                a = decrypt(c);
                if(a == 1) b = b + (int)Math.pow(2,p);
                p=p-1;
            }
            s.append((char)b);

        }

        return s.toString();

    }


    public BigInteger getHashedID(){
        return this.hashedID;
    }

    public String getID(){
        return this.ID;
    }

    public PKG getPkg() {return this.pkg;}

}
