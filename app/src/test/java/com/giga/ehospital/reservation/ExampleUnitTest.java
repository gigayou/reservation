package com.giga.ehospital.reservation;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {
        StringBuilder sb = new StringBuilder();
        String[] split = UUID.randomUUID().toString().split("-");
        for (String s : split) {
            sb.append(s);
        }
        System.out.println(sb.toString().substring(0,21).length());
    }
}