package ru.al.test;

/**
 * Created by Alex on 05.01.2016.
 */
public class AnotherTestImpl implements Test {
    @Override
    public void test() {
        System.out.println(getClass().getMethods().toString());
        System.out.println("AnotherTestImpl");
    }
}
