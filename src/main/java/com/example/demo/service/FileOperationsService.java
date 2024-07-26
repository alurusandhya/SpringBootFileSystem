package com.example.demo.service;

public interface FileOperationsService {

	public String createFile(String name);

	public String deleteFile(String path);

	public String moveFile(String sourcePath, String destinationPath);
	
	public String writeAFile(String path,String content);
}
