package com.example.demo;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemCatalogController {

	@Autowired
	ItemRepository itemRepository;

	@GetMapping("/item")
	public List<Item> getItem() {
		List<Item> list = itemRepository.findAll();
		System.out.println(list);
		return list;
	}

	@GetMapping("/save-item")
	public void saveItem() {
		System.out.println("------------============================-------------------------====================");
		Stream.of("Lining", "Puma", "Bad Boy", "Air Jordan", "Nike", "Addidas", "Reebok")
				.forEach(item -> itemRepository.save(new Item(item)));

		itemRepository.findAll().forEach(System.out::println);
		System.out.println("---------------------------------------========================-------------------");
	}
}
