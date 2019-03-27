package com.java8.Java8Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException {

		IntStream.range(1, 10).forEach(x -> System.out.println(x));
		System.out.println("-------------------------------------");
		IntStream.range(1, 10).skip(3).forEach(x -> System.out.println(x));

		System.out.println("Sum : " + IntStream.range(1, 10).sum());

		// Stream sort, find first and print.
		Stream.of("Ajaya", "Aryan", "Abhay").sorted().findFirst().ifPresent(System.out::println);
		// stream from array sort, filter and print
		Arrays.asList("Ajay", "Kiran", "Ashwi", "Raju", "Mohan").stream().filter(s -> s.startsWith("A")).sorted()
				.forEach(System.out::println);
		
		//Average of int
		Arrays.stream(new int[] {1,2,3,4}).average().ifPresent(System.out::println);
		
		//Name in Upper case
		Stream.of("Ajaya", "Aryan", "Abhay").map(String::toUpperCase).forEach(System.out::println);
		
		
		//
		Stream<String> rows = Files.lines(Paths.get("/home/ajay/students.csv"));
		System.out.println(rows);
		//Map<String, Interger> map = rows.skip(1).map(x->x.split(",")).filter(x->x.length )
		
		
		
	}
}
