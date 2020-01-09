package Tests;


import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

import CocksScheme.PKG;

class PKGTest {

    @Test
    public void testPKG(){

        PKG pkg = new PKG();

        final BigInteger[] expected = new BigInteger[]{BigInteger.valueOf(-1),BigInteger.valueOf(-1)};

        final BigInteger[] actual = pkg.computeuserhash("a");

        assertEquals(expected[0],actual[0]);
        assertEquals(expected[1],actual[1]);

        PKG pkg1 = new PKG(2048,512);

        final BigInteger[] expected1 = new BigInteger[]{BigInteger.valueOf(-1),BigInteger.valueOf(-1)};

        final BigInteger[] actual1 = pkg1.computeuserhash("");

        assertEquals(expected1[0],actual1[0]);
        assertEquals(expected1[1],actual1[1]);

    }

}