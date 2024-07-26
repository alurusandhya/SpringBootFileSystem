package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EntityProperties;
import com.example.demo.service.FolderOperationsService;

@RestController("/folder_operations")
public class FolderOperationsController {

	@Autowired
	FolderOperationsService folderOperationsService;

	@GetMapping("/create_folder/{name}")
	public String createFolderOrDrive(@RequestParam("name") String name) {

		return folderOperationsService.createFolder(name);
	}

	@GetMapping("/delete_folder/{path}")
	public String deleteFolderOrDrive(@RequestParam("path") String path) {

		return folderOperationsService.deleteFolder(path);
	}

	@GetMapping("/move_folder/{sourcePath}/{destPath}")
	public String deleteFolderOrDrive(@RequestParam("sourcePath") String sourcePath,
			@RequestParam("destPath") String destPath) {

		return folderOperationsService.moveFolder(sourcePath, destPath);
	}
	
	@GetMapping("/get_folder_properties/{path}")
	public EntityProperties getFolderProperties(@RequestParam("path") String path) {

		return folderOperationsService.getFolderProperties(path);
	}
}
