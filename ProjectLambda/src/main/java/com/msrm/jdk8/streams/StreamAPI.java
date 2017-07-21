package com.msrm.jdk8.streams;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamAPI {

    public static void main(String[] args) {
        //@formatter:off
		IntStream.iterate(3, x -> x + 3)
			.limit(50)
			.filter(i -> i > 100 && i < 200)
			.forEach(System.out::println);
		
		Stream<Locale> locales = Arrays.stream(Locale.getAvailableLocales());
        locales.forEach(l -> {
                System.out.println(l.getDisplayCountry());
                System.out.println(l.getDisplayLanguage());
                System.out.println(l.getDisplayName());
                System.out.println();
            });

	}

}
