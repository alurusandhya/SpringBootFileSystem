package com.example.demo.service;

import com.example.demo.dto.EntityProperties;

public interface FolderOperationsService {

	public String createFolder(String name);

	public String deleteFolder(String path);

	public String moveFolder(String sourcePath, String destinationPath);
	
	public EntityProperties getFolderProperties(String path);
}
