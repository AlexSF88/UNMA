package ru.al.test;

/**
 * Created by Alex on 05.01.2016.
 */
public class TestMain {
    public static void main(String[] args) {
        Test test = new TestImpl();
        test.test();
        test = new ChildTestImpl();
        test.test();
        test = new AnotherTestImpl();
        test.test();
    }
}
