package com.caomeiprincess;


import org.junit.Test;

public interface InterfaceTest {
    default void test(){
        System.out.println("test");
    }
    class InterfaceTestImpl implements InterfaceTest{
        @Test
        public void test(){
            System.out.println("testImpl");
        }
    }
    class InterfaceTestImpl2{
    }
}
