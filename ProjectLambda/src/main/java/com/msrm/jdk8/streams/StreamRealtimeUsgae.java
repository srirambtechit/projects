package com.msrm.jdk8.streams;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.partitioningBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msrm.jdk8.lambdaexpression.helper.Customer;
import com.msrm.jdk8.lambdaexpression.helper.Employee;
import com.msrm.jdk8.lambdaexpression.helper.Employee.Sex;
import com.msrm.jdk8.lambdaexpression.helper.Invoice;

public class StreamRealtimeUsgae {
    public static void main(String[] args) {

        List<Invoice> invoices = Invoice.invoices(8);
        invoices.forEach(System.out::println);

        System.out.println();

        // 1. count number of Oracle customer
        int count = 0;
        for (Invoice invoice : invoices) {
            if (invoice.getCustomer() == Customer.ORACLE) count++;
        }
        System.out.println("Count : " + count);

        Long iCount = invoices.stream().filter(i -> i.getCustomer() == Customer.ORACLE)
                .collect(counting());
        System.out.println("Oracle customer : " + iCount);

        System.out.println();

        // 2. count number of duplicate invoice names
        Map<String, Integer> dupNamesCount = new HashMap<>();
        for (Invoice invoice : invoices) {
            String name = invoice.getName();
            if (dupNamesCount.containsKey(name))
                dupNamesCount.put(name, dupNamesCount.get(name) + 1);
            else dupNamesCount.put(name, 1);
        }
        System.out.println(dupNamesCount);

        Map<String, Long> iDupNamesCount = invoices.stream()
                .collect(groupingBy(Invoice::getName, mapping(Invoice::getName, counting())));
        System.out.println(iDupNamesCount);

        System.out.println();

        List<Employee> employees = Employee.employees(10);
        employees.forEach(System.out::println);

        // 3. Split employee by gender
        Map<Boolean, List<Employee>> empsBySex = employees.stream()
                .collect(partitioningBy(e -> e.getSex() == Sex.MALE));
        System.out.println(empsBySex);
    }
}