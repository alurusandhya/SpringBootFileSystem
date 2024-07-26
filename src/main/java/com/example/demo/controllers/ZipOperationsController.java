package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ZipOperationsService;

@RestController("/zip_operations")
public class ZipOperationsController {
	
	@Autowired
	ZipOperationsService zipOperationsService;

	@GetMapping("/create_zip/{zipname}/{filename}")
	public String createZipOrDrive(@RequestParam("zipname") String zipname, @RequestParam("filename") String filename) {

		return zipOperationsService.createFile(zipname,filename);
	}

	@GetMapping("/delete_zip/{path}")
	public String deleteZipOrDrive(@RequestParam("path") String path) {

		return zipOperationsService.deleteFile(path);
	}

	@GetMapping("/move_zip/{sourcePath}/{destPath}")
	public String deleteZipOrDrive(@RequestParam("sourcePath") String sourcePath,
			@RequestParam("destPath") String destPath) {

		return zipOperationsService.moveFile(sourcePath, destPath);
	}
	
	@PostMapping("/writeData_To_zip/{path}/{content}")
	public String writeDataToFile(@RequestParam("path") String sourcePath,
			@RequestParam("content") String content) {

		return zipOperationsService.writeAFile(sourcePath, content);
	}

}
