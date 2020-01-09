package Tests;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;


import CryptoTools.*;

class SHA_3Test {

    @Test
    public void TestSHA3_512(){
        File file =
                new File("SHA3_512ShortMsg.rsp");
        try {
            Scanner sc = new Scanner(file);
            String Len = new String();
            String Msg = new String();
            String MD = new String();
            byte[] Message = new byte[64];
            String MessageDigest = new String();

            int line_number = 0;
            try {
                while (sc.hasNextLine()) {
                    line_number = line_number + 1;
                    if (line_number > 11) {
                        Len = (sc.nextLine()).substring(6);
                        Len.trim();

                        Msg = (sc.nextLine()).substring(6);
                        Msg.trim();
                        Message = SHA_3.hexStringToByteArray(Msg);


                        MD =  (sc.nextLine()).substring(5);
                        MD.trim();

                        sc.nextLine();

                        final String expected = MD;

                        final String actual = SHA_3.sha3(Message);

                        assertEquals(expected,actual);

                    } else sc.nextLine();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}