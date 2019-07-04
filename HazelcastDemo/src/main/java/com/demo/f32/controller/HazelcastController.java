package com.demo.f32.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.core.HazelcastInstance;

/**
 * Created by Ajay on 07-07-2019.
 */
@RestController
public class HazelcastController {

	@Autowired
	private HazelcastInstance instance;

	@RequestMapping("/write-name")
	public String write(@RequestParam("name") String name) {
		 // get map from hazel cast
		Map<String, String> nameMap = instance.getMap("configuration");
		nameMap.put("data", name); // write value, This value will be accessible from another jvm also
		return "success";
	}

	@RequestMapping("/read-name")
	public String read() {
		Map<String, String> nameMap = instance.getMap("configuration"); // get map from hazel cast
		return "Hazelcast values is :" + nameMap.get("data"); // read value
	}
}
