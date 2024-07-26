package com.example.demo.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EntityProperties;
import com.example.demo.service.ZipOperationsService;

@Service
public class ZipOperationServiceImpl implements ZipOperationsService {

	@Value("${root.directory.path}")
	private String rootDirectoryOfFileSystem;

	@Override
	public String createZip(String zipName,String fileName){
		  try {
			  Path zipFile = null;
			  if (zipName.toString().endsWith(".zip")){
	           zipFile = Files.createFile(Paths.get(rootDirectoryOfFileSystem + "/" + zipName));
			  }
	        Path sourceDirPath = Paths.get(rootDirectoryOfFileSystem + "/" + fileName);
	        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile));
	             Stream<Path> paths = Files.walk(sourceDirPath)) {
	        	System.out.println(paths.toString());
	            paths
	                    .forEach(path -> {
	                        ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
	                        try {
	                            zipOutputStream.putNextEntry(zipEntry);
	                            Files.copy(path, zipOutputStream);
	                            zipOutputStream.closeEntry();
	                        } catch (IOException e) {
	                            System.err.println(e);
	                        }
	                    });
	        }
	        
		  }catch (Exception e) {
			  
			  return zipName + ", zip folder creation is failed dut to invalid format";
			
		}
	    
		return zipName + ", Zip is created Successfully";
	}

	@Override
	public String deleteZip(String path) {
		Path zipFile =Paths.get(rootDirectoryOfFileSystem + "/" + path);				
		try {
			Files.delete(zipFile);
		} catch (IOException e) {
			e.printStackTrace();
			return "Zip deletion failed due to => " + e.getClass();
		}

		return path + ", Zip is deleted successfully!!";
	}
	
	
	@Override
	public EntityProperties getZipProperties(String path) {
			
		EntityProperties ep;
		 
		 ZipFile zipFile = null;		 
		 
		try {
			if (path.toString().endsWith(".zip")){
			zipFile = new ZipFile(path);
			 ZipEntry zipEntry = new ZipEntry(zipFile.getName());
			 
			 String zipName = String.valueOf(zipEntry.getName());
			 
			 String[] str = zipName.replace('\\',',').split(",");
			 
			 ep=new EntityProperties("Directory",str[str.length -1],zipFile.getName(), (zipFile.size())/2);		 
		 
			
			}else {
				return new EntityProperties(path+" is not a zip format");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return new EntityProperties(path+" is not a zip format");
			
		}
		 return ep;
		
	}
	
	public static long getDirectorySizeJava8(String pathString) {
		Path path = Paths.get(pathString);
	      long size = 0;
	      try (Stream<Path> walk = Files.walk(path)) {

	          size = walk
	                  .filter(Files::isRegularFile)
	                  .mapToLong(p -> {
	                      try {
	                          return Files.size(p);
	                      } catch (IOException e) {
	                          System.out.printf("Failed to get size of %s%n%s", p, e);
	                          return 0L;
	                      }
	                  })
	                  .sum();

	      } catch (IOException e) {
	          System.out.printf("IO errors %s", e);
	      }

	      return size;

	  }

}

