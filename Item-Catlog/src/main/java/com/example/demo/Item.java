package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {
	public Item(String name) {
		this.name = name;
	}
	public Item() {
		
	}

	@Id
	@GeneratedValue
	private int id;

	private String name;
}
