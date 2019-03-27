package com.example.demo;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;

public class ItemInitializer implements CommandLineRunner {

	private final ItemRepository itemRepository;

	public ItemInitializer(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("------------============================-------------------------====================");
		Stream.of("Lining", "Puma", "Bad Boy", "Air Jordan", "Nike", "Addidas", "Reebok")
				.forEach(item -> itemRepository.save(new Item(item)));

		itemRepository.findAll().forEach(System.out::println);
		System.out.println("---------------------------------------========================-------------------");
	}

}