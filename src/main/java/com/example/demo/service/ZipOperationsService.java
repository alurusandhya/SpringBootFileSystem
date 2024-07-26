package com.example.demo.service;

public interface ZipOperationsService {

	public String createFile(String zipname, String filename);

	public String deleteFile(String path);

	public String moveFile(String sourcePath, String destinationPath);
}
