package edu.eci.arsw.math;

import java.util.ArrayList;

///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
public class PiDigits {
    private static int DigitsPerSum = 8;


    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param count The number of digits to return
     * @param N The number o threads
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count, int N) throws InterruptedException {
        if (start < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        if (count < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        PiThread[] threads = new PiThread[N];

        for (int i=0; i<N; i++){
            threads[i]=new PiThread();
            threads[i].run();
        }

        for (int i=0; i<N; i++){
            Thread thread = threads[i];
            thread.join();
        }

        byte[] digits = new byte[count];
        double sum = 0;

        for (int i = 0; i < N; i++) {
            if (i % DigitsPerSum == 0) {
                sum = threads[i].calculate(start);
                start += DigitsPerSum;
            }
            sum = 16 * (sum - Math.floor(sum));
            digits[i] = (byte) sum;
        }

        return digits;
        }
    }

