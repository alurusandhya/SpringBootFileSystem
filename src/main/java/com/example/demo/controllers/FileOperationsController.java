package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.FileOperationsService;

@RestController("/file_operations")
public class FileOperationsController {

	@Autowired
	FileOperationsService fileOperationsService;

	@GetMapping("/create_file/{name}")
	public String createfileOrDrive(@RequestParam("name") String name) {

		return fileOperationsService.createFile(name);
	}

	@GetMapping("/delete_file/{path}")
	public String deletefileOrDrive(@RequestParam("path") String path) {

		return fileOperationsService.deleteFile(path);
	}

	@GetMapping("/move_file/{sourcePath}/{destPath}")
	public String deletefileOrDrive(@RequestParam("sourcePath") String sourcePath,
			@RequestParam("destPath") String destPath) {

		return fileOperationsService.moveFile(sourcePath, destPath);
	}
	
	@PostMapping("/writeData_To_File/{path}/{content}")
	public String writeDataToFile(@RequestParam("path") String sourcePath,
			@RequestParam("content") String content) {

		return fileOperationsService.writeAFile(sourcePath, content);
	}
}
