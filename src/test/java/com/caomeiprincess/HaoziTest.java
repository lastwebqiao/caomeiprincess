package com.caomeiprincess;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class HaoziTest {

    /**
     * lambda表达式
     * @param args
     */
    public static void main(String[] args) {
        Runnable r = () -> System.out.println("Hello Lambda!");
        r.run();

        // Java 8之后：
        List list2 = Arrays.asList("a", "b", "c", "d");
        list2.forEach(n -> System.out.println(n));

        // 使用Java 8的方法引用更方便，方法引用由::双冒号操作符标示，
        list2.forEach(System.out::println);

        map();

        mapReduce();

        List<String> languages = Arrays.asList("Java", "Python", "scala", "Shell", "R");
        System.out.println("Language starts with J: ");
        filter(languages, x -> x.startsWith("J"));
        System.out.println("\nLanguage ends with a: ");
        filter(languages, x -> x.endsWith("a"));
        System.out.println("\nAll languages: ");
        filter(languages, x -> true);
        System.out.println("\nNo languages: ");
        filter(languages, x -> false);
        System.out.println("\nLanguage length bigger three: ");
        filter(languages, x -> x.length() > 4);
//        InterfaceTest interfaceTest = new InterfaceTest.InterfaceTestImpl2();
//        interfaceTest.test();
    }
    //map的作用是将一个对象变换为另外一个
    public static void map() {
        List<Double> cost = Arrays.asList(10.0, 20.0, 30.0);
        cost.stream().map(x -> x + x * 0.05).forEach(x -> System.out.println(x));

    }

    //mreduce实现的则是将所有值合并为一个
    public static void mapReduce() {
        List<Double> cost = Arrays.asList(10.0, 20.0, 30.0);
        double allCost = cost.stream().map(x -> x + x * 0.05).reduce((sum, x) -> sum + x).get();
        System.out.println(allCost);
    }

    //Predicate过滤
    public static void filter(List<String> languages, Predicate<String> condition) {
        languages.stream().filter(x -> condition.test(x)).forEach(x -> System.out.println(x + " "));
    }
}
