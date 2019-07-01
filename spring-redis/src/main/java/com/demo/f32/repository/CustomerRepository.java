package com.demo.f32.repository;

import java.util.Map;

import com.demo.f32.model.Customer;

public interface CustomerRepository {

    void save(Customer user);
    Map<String, Customer> findAll();
    Customer findById(String id);
    void update(Customer user);
    void delete(String id);
}
