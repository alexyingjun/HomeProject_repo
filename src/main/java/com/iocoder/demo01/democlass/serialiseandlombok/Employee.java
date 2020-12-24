package com.iocoder.demo01.democlass.serialiseandlombok;

import java.io.Serializable;

public class Employee implements Serializable {
    private static final long serialversionUID = 129348938L;
    transient int a;
    public static int b;
    String name;
    int age;

    // Default constructor
    public Employee(String name, int age, int a, int b)
    {
        this.name = name;
        this.age = age;
        this.a = a;
        this.b = b;
    }

    public static void printdata(Employee object1)
    {

        System.out.println("name = " + object1.name);
        System.out.println("age = " + object1.age);
        System.out.println("a = " + object1.a);
        System.out.println("b = " + object1.b);
    }
}