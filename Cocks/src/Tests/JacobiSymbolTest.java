package Tests;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

import CryptoTools.JacobiSymbol;

class JacobiSymbolTest {

    /*Because Jacobi function is multiplicative in the second argument, it is enough to test if the Legendre Symbol is computed correctly
    and if it is multiplicative
    */

    @Test
    public void TestLegendreSymbol1(){


        Random rnd = new Random();
        BigInteger p = BigInteger.probablePrime( 1024, rnd);
        BigInteger a = new BigInteger(1024, rnd);
        a = a.mod(p);

        JacobiSymbol j = new JacobiSymbol();

        final int expected = 0;

        final int actual = j.computeJacobiSymbol(a.multiply(p),p);

        assertEquals(expected,actual);

        final int expectedminusone = (int)Math.pow(-1,((p.subtract(BigInteger.ONE)).divide(BigInteger.TWO)).intValue());
        final int actualminusone = j.computeJacobiSymbol(BigInteger.valueOf(-1),p);

        assertEquals(expectedminusone,actualminusone);

        final int expectedtwo = (int)Math.pow(-1,(((p.multiply(p)).subtract(BigInteger.ONE)).divide(BigInteger.valueOf(8))).intValue());
        final int actualtwo = j.computeJacobiSymbol(BigInteger.TWO,p);

        assertEquals(expectedtwo,actualtwo);

        BigInteger r = new BigInteger(1024, rnd);
        a = (r.multiply(r)).mod(p);

        final int expectedsquare = 1;

        final int actualsquare = j.computeJacobiSymbol(a,p);

        assertEquals(expectedsquare,actualsquare);


    }

    @Test
    public void multiplicativitycheck(){

        Random rnd = new Random();
        BigInteger p = BigInteger.probablePrime( 1024, rnd);
        BigInteger q = BigInteger.probablePrime( 1024, rnd);

        BigInteger a = new BigInteger(2048, rnd);
        a = a.mod(p.multiply(q));

        JacobiSymbol j = new JacobiSymbol();

        final int expected = j.computeJacobiSymbol(a,p.multiply(q));

        final int actual = j.computeJacobiSymbol(a,p)*j.computeJacobiSymbol(a,q);

        assertEquals(expected,actual);

        final int expectedsquare = j.computeJacobiSymbol(a,p.multiply(p));

        final int actualsquare = j.computeJacobiSymbol(a,p)*j.computeJacobiSymbol(a,p);

        assertEquals(expectedsquare,actualsquare);


    }

}