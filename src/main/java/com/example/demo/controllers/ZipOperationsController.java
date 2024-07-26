package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EntityProperties;
import com.example.demo.service.ZipOperationsService;

@RestController("/zip_operations")
public class ZipOperationsController {
	
	@Autowired
	ZipOperationsService zipOperationsService;

	@GetMapping("/create_zip/{zipname}/{foldername}")
	public String createZipOrDrive(@RequestParam("zipname") String zipname, @RequestParam("foldername") String filename) {

		return zipOperationsService.createZip(zipname,filename);
	}

	@GetMapping("/delete_zip/{path}")
	public String deleteZipOrDrive(@RequestParam("path") String path) {

		return zipOperationsService.deleteZip(path);
	}
	
	@GetMapping("/get_zip_properties/{path}")
	public EntityProperties getFolderProperties(@RequestParam("path") String path) {

		return zipOperationsService.getZipProperties(path);
	}
}
