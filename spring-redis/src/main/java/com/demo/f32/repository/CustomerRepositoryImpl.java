package com.demo.f32.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.demo.f32.model.Customer;

import java.util.Map;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private RedisTemplate<String, Customer> redisTemplate;

    private HashOperations hashOperations;


    public CustomerRepositoryImpl(RedisTemplate<String, Customer> redisTemplate) {
        this.redisTemplate = redisTemplate;

        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(Customer customer) {
        hashOperations.put("CUSTOMER", customer.getId(), customer);
    }

    @Override
    public Map<String, Customer> findAll() {
        return hashOperations.entries("CUSTOMER");
    }

    @Override
    public Customer findById(String id) {
        return (Customer)hashOperations.get("CUSTOMER", id);
    }

    @Override
    public void update(Customer customer) {
        save(customer);
    }

    @Override
    public void delete(String id) {

        hashOperations.delete("CUSTOMER", id);
    }
}
