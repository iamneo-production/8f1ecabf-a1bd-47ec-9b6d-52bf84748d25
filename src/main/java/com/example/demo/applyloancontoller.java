package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin

public class applyloancontoller {
	
	@Autowired
	
	applyloanrepo applyrepo;

	@GetMapping("/applyloanget")
	public List<applyloan> getAll()
	{
	return applyrepo.findAll();
	}
	@PostMapping("/applyloaninsert")
	public applyloan create(@Value(value="") @RequestBody applyloan applyloan1)
	{
	return applyrepo.save(applyloan1);
	}

}
