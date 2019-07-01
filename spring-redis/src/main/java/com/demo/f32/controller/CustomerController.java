package com.demo.f32.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.f32.model.Customer;
import com.demo.f32.repository.CustomerRepository;

import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	private CustomerRepository customerRepository;

	public CustomerController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@GetMapping("/save/{id}/{name}")
	public Customer add(@PathVariable("id") final String id, @PathVariable("name") final String name) {
		customerRepository.save(new Customer(id, name, 4000L));
		return customerRepository.findById(id);
	}

	@GetMapping("/update/{id}/{name}")
	public Customer update(@PathVariable("id") final String id, @PathVariable("name") final String name) {
		customerRepository.update(new Customer(id, name, 1000L));
		return customerRepository.findById(id);
	}

	@GetMapping("/delete/{id}")
	public Map<String, Customer> delete(@PathVariable("id") final String id) {
		customerRepository.delete(id);
		return all();
	}

	@GetMapping("/all")
	public Map<String, Customer> all() {
		return customerRepository.findAll();
	}
}
