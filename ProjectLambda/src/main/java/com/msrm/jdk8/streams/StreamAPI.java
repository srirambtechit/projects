package com.msrm.jdk8.streams;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.msrm.jdk8.lambdaexpression.helper.City;
import com.msrm.jdk8.lambdaexpression.helper.DataProvider;
import com.msrm.jdk8.lambdaexpression.helper.Employee;

public class StreamAPI {

    public static void main(String[] args) {

        IntStream.iterate(3, x -> x + 3) //
                .limit(50) //
                .filter(i -> i > 100 && i < 200) //
                .forEach(System.out::println);

        Map<String, List<Locale>> countriesLocale = Arrays.stream(Locale.getAvailableLocales())
                .collect(Collectors.groupingBy(Locale::getDisplayCountry));

        System.out.println("CountriesLocale : " + countriesLocale);

        Map<String, Long> languagesCount = Arrays.stream(Locale.getAvailableLocales())
                .collect(Collectors.groupingBy(Locale::getDisplayLanguage, Collectors.counting()));
        System.out.println("Languages Count : " + languagesCount);

        Map<String, Long> langCounts = Arrays.stream(Locale.getAvailableLocales())
                .map(Locale::getDisplayLanguage).sorted()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(langCounts);

        List<Employee> employees = Employee.employees(10);

        // if employeeName is duplicate, then java.lang.IllegalStateException will be
        // thrown
        // System.out.println(
        // employees.stream().collect(Collectors.toMap(Employee::getName,
        // Employee::getId)));

        // if duplicate key is found, should manipulate value and return some value as
        // key
        System.out.println(employees.stream().collect(Collectors.toMap(Employee::getId,
                Employee::getName, (existingValue, oldValue) -> existingValue)));

        TreeMap<Integer, Employee> treeMap = employees.stream()
                .collect(Collectors.toMap(Employee::getId, Function.identity(),
                        (existingValue, newValue) -> existingValue, TreeMap::new));
        System.out.println(treeMap);

        Map<String, Set<String>> countryLangs = Arrays.stream(Locale.getAvailableLocales())
                .collect(Collectors.toMap( //
                        l -> l.getDisplayCountry(), //
                        l -> Collections.singleton(l.getDisplayLanguage()), //
                        (a, b) -> //
                        {
                            Set<String> set = new HashSet<>(a);
                            set.addAll(b);
                            return set;
                        }));
        System.out.println(countryLangs);

        Map<String, List<Locale>> collect = Arrays.stream(Locale.getAvailableLocales())
                .collect(Collectors.groupingBy(Locale::getCountry));
        System.out.println(collect);

        Map<String, Set<Locale>> collect3 = Arrays.stream(Locale.getAvailableLocales())
                .collect(Collectors.groupingBy(Locale::getCountry, Collectors.toSet()));
        System.out.println(collect3);

        Map<String, Long> collect2 = Arrays.stream(Locale.getAvailableLocales())
                .collect(Collectors.groupingBy(Locale::getCountry, Collectors.counting()));
        System.out.println(collect2);

        Map<String, IntSummaryStatistics> collect4 = Arrays.stream(Locale.getAvailableLocales())
                .collect(Collectors.groupingBy(Locale::getCountry,
                        Collectors.summarizingInt(l -> l.getDisplayCountry().length())));
        System.out.println(collect4);
        System.out.println(collect4.get("US").getAverage());
        System.out.println(collect4.get("US").getCount());
        System.out.println(collect4.get("US").getSum());

        List<City> cities = DataProvider.cities();
        Map<String, Optional<City>> maxPopulatedState = cities.stream()
                .collect(Collectors.groupingBy(City::getState,
                        Collectors.maxBy(Comparator.comparing(City::getPopulation))));
        System.out.println(maxPopulatedState);

        String lengthyString = "This is very, very bad code. The function passed to forEach runs concurrently in multiple threads, updating a shared array. That’s a classic race condition. If you run this program multiple times, you are quite likely to get a different sequence of counts in each run, each of them wrong. This is very, very bad code. The function passed to forEach runs concurrently in multiple threads, updating a shared array. That’s a classic race condition. If you run this program multiple times, you are quite likely to get a different sequence of counts in each run, each of them wrong. This is very, very bad code. The function passed to forEach runs concurrently in multiple threads, updating a shared array. That’s a classic race condition. If you run this program multiple times, you are quite likely to get a different sequence of counts in each run, each of them wrong. This is very, very bad code. The function passed to forEach runs concurrently in multiple threads, updating a shared array. That’s a classic race condition. If you run this program multiple times, you are quite likely to get a different sequence of counts in each run, each of them wrong. This is very, very bad code. The function passed to forEach runs concurrently in multiple threads, updating a shared array. That’s a classic race condition. If you run this program multiple times, you are quite likely to get a different sequence of counts in each run, each of them wrong.";
        Stream<String> words = Arrays.stream(lengthyString.split(" "));

        // find out no of words whose length is less than 12

        // approach 1 : bad code
        // int[] wordsCount = new int[12]; // it is not thread-safe
        // words.parallel().forEach(w -> {
        // if (w.length() < 12) wordsCount[w.length()]++;
        // });

        // approach 2
        AtomicInteger[] wordsCount = new AtomicInteger[12];
        for (int i = 0; i < wordsCount.length; i++) {
            wordsCount[i] = new AtomicInteger(0);
        }
        words.parallel().forEach(w -> {
            if (w.length() < 12) wordsCount[w.length()].incrementAndGet();
        });
        System.out.println(Arrays.toString(wordsCount));

        // approach 3
        words = Arrays.stream(lengthyString.split(" "));
        Map<Integer, Long> collect5 = words.parallel()
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));
        System.out.println(collect5);
        // [0, 15, 40, 35, 45, 15, 40, 15, 30, 5, 5, 0]
    }

}
