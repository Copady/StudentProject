import java.math.BigInteger;

import CocksScheme.PKG;
import CocksScheme.User;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import CocksScheme.*;


public class Main {


    public static void main(String[] args) {


       java.security.Security.addProvider(new BouncyCastleProvider());
       PKG pkg = new PKG(2048,512);

       User usera = new User("a", pkg);
       User userb = new User("bca", pkg);

       BigInteger[] c = usera.encrypt(userb,-1);
       System.out.println(userb.decrypt(c));

       BigInteger[] e = userb.encrypt(usera, 1);
       System.out.println(usera.decrypt(e));

       BigInteger[] c1 = usera.encrypt(userb, 1);
       System.out.println(userb.decrypt(c1));

       BigInteger[] c2 = userb.encrypt(usera, -1);
       System.out.println(usera.decrypt(c2));


       BigInteger[] C = usera.encryptlongmessage(userb, "xyz");


       System.out.println(userb.decryptlongmessage(C));


       PKG pkg1 = new PKG(2048,512);

       User userc = new User("c",pkg1);

       c = usera.encrypt(userc,-1);

       User userd  = new User("",pkg1);

       User usere = new User("e",null);

       PKG pkg2 = new PKG();

       User userf = new User("f",pkg2);




       usera = null;
       userb = null;
       userc = null;
       userd = null;
       usere = null;
       pkg = null;
       pkg1 = null;



    }
}
