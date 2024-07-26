package com.example.demo.service;

import com.example.demo.dto.EntityProperties;

public interface ZipOperationsService {

	public String createZip(String zipname, String filename);

	public String deleteZip(String path);
	
	public EntityProperties getZipProperties(String path);
}
