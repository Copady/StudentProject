package Tests;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

import CocksScheme.User;
import CocksScheme.PKG;


class UserTest {

    @Test
    public void encryptiontestfornulls(){

        PKG pkg = new PKG(2048,512);
        PKG pkg_1 = new PKG(2048,512);

        User usera = new User("a",pkg);
        User userb = new User("b",pkg);

        final BigInteger[] expected = new BigInteger[]{BigInteger.valueOf(-1),BigInteger.valueOf(-1)};

        final BigInteger[] actual = usera.encrypt(userb,0);

        assertEquals(expected[0],actual[0]);
        assertEquals(expected[1],actual[1]);

        final BigInteger[] expected1  = new BigInteger[]{BigInteger.valueOf(-1)};

        final BigInteger[] actual1 = usera.encryptlongmessage(userb,"");

        assertEquals(expected1[0],actual1[0]);


        User userc = new User("c",pkg_1);

        final BigInteger[] expected2  = new BigInteger[]{BigInteger.valueOf(-1),BigInteger.valueOf(-1)};

        final BigInteger[] actual2 = usera.encrypt(userc,1);

        assertEquals(expected2[0],actual2[0]);
        assertEquals(expected2[1],actual2[1]);


        final BigInteger[] expected3  = new BigInteger[]{BigInteger.valueOf(-1)};

        final BigInteger[] actual3 = usera.encryptlongmessage(userc,"a");

        assertEquals(expected3[0],actual3[0]);


        final int e = -2;

        final int a = usera.decrypt(null);

        assertEquals(e,a);


        final String expected_string = "-1";

        final String actual_string = usera.decryptlongmessage(null);

        }

        @Test
        public void encryptiontest(){

            final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            StringBuilder builder = new StringBuilder();

            int max = 576;
            int min = 8;
            int range = max - min + 1;

            int ID_len_1 = (int)(Math.random() * range) + min;
            int ID_len_2 = (int)(Math.random() * range) + min;

            System.out.println(ID_len_1);
            System.out.println(ID_len_2);

            while (ID_len_1-- != 0) {
                int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
                builder.append(ALPHA_NUMERIC_STRING.charAt(character));
            }

            String ID_1 = builder.toString();
            builder = new StringBuilder();

            while (ID_len_2-- != 0) {
                int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
                builder.append(ALPHA_NUMERIC_STRING.charAt(character));
            }

            String ID_2 = builder.toString();

            System.out.println(ID_1);
            System.out.println(ID_2);
            System.out.println(ID_1.length());
            System.out.println(ID_2.length());

            int count;
            int i;
            PKG pkg = new PKG(2048,512);
            User usera = new User(ID_1,pkg);
            User userb = new User(ID_2,pkg);

            for(i = 1; i < 8; i++){
                builder = new StringBuilder();
                count = i;

                System.out.println(count);

                while (count-- != 0) {
                    int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
                    builder.append(ALPHA_NUMERIC_STRING.charAt(character));
                }


                System.out.println(builder.toString());

                final String exp_string = userb.decryptlongmessage(usera.encryptlongmessage(userb,builder.toString()));

                final String act_string = builder.toString();

                assertEquals(exp_string, act_string);
            }




        }

    }

