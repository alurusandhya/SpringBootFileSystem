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
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.service.ZipOperationsService;

@Service
public class ZipOperationServiceImpl implements ZipOperationsService {

	@Value("${root.directory.path}")
	private String rootDirectoryOfFileSystem;

	@Override
	public String createFile(String zipName,String fileName){
		  try {
	      Path zipFile = Files.createFile(Paths.get(rootDirectoryOfFileSystem + "/" + zipName));

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
			  
			  return zipName + ", zip folder creation is failed";
			
		}
	    
		return zipName + ", Zip is created Successfully";
	}

	@Override
	public String deleteFile(String path) {
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
	public String moveFile(String sourcePath, String destinationPath) {

		try {
			Path source=Paths.get(sourcePath);
			List<String> destFolder=Arrays.asList(destinationPath.split("\\\\"));
			List<String> destFolderFinal=destFolder.subList(0, destFolder.size()-1);
//			System.out.println("=====Destination folder => "+destFolderFinal+ destFolder);
			File directory = new File(destFolderFinal.stream().collect(Collectors.joining("\\\\")));
		    if (! directory.exists()){
		        directory.mkdir();
		        System.out.println("**************directory created successfully!!!**************");
		    }else {
		    	System.out.println("**************directory alreay exist...**************");
		    }
			Path destination=Paths.get(destinationPath);
			Files.move(source, destination,
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			return "unable to move File due to => " + e.getClass();
		}

		return sourcePath + ", succesfully moved to " + destinationPath;
	}	

}
