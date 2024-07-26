package com.example.demo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EntityProperties;
import com.example.demo.service.FolderOperationsService;

@Service
public class FolderOperationServiceImpl implements FolderOperationsService {

	@Value("${root.directory.path}")
	private String rootDirectoryOfFileSystem;

	@Override
	public String createFolder(String name){
		 Path path = Paths.get(rootDirectoryOfFileSystem + "/" + name);
		 try {
		 if (!Files.exists(path)) {
	            
	            Files.createDirectories(path);
	           
//
//		if (new File(rootDirectoryOfFileSystem + "/" + name).mkdirs()) {
//			return name + ", folder create successfully!!";
		}
		 }catch (IOException e) {
			 return name + ", folder creation failed";
		}
		return name + ", folder create successfully";

		
	}

	@Override
	public String deleteFolder(String path) {

		//File directory = new File(path);
		Path directory = Path.of(path + "/"); 
		try (Stream<Path> pathStream = Files.walk(directory)) { //.toPath().toAbsolutePath())) {
			pathStream.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
		} catch (IOException e) {
			e.printStackTrace();
			return "File deletion failed due to => " + e.getClass();
		}

		return path + ", folder deleted successfully!!";
	}

	@Override
	public String moveFolder(String sourcePath, String destinationPath) {

		try {
//			Files.move(new File(sourcePath).toPath(), new File(destinationPath).toPath(),
//					StandardCopyOption.REPLACE_EXISTING);
			
			 Files.move(Paths.get(sourcePath)
					 , Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			return "unable to move folder due to => " + e.getClass();
		}

		return sourcePath + ", succesfully moved to " + destinationPath;
	}

	@Override
	public EntityProperties getFolderProperties(String path) {
		File file = new File(path);
		boolean exists =      file.exists();      // Check if the file exists
		boolean isDirectory = file.isDirectory(); // Check if it's a directory
//		boolean isFile =      file.isFile();      // Check if it's a regular file
		
		EntityProperties ep;
		if(exists) {
			if(isDirectory) {
				ep=new EntityProperties("Directory",file.getName(),file.getPath(), (int)getDirectorySizeJava8(path)%2);
			}else {
				return new EntityProperties(path+" is not a directory");
			}
		}else {
			return new EntityProperties(path+" does not exist");
		}
		return ep;
	}
	
	public static long getDirectorySizeJava8(String pathString) {
		Path path = Paths.get(pathString);
	      long size = 0;

	      // need close Files.walk
	      try (Stream<Path> walk = Files.walk(path)) {

	          size = walk
                  //.peek(System.out::println) // debug
	                  .filter(Files::isRegularFile)
	                  .mapToLong(p -> {
	                      // ugly, can pretty it with an extract method
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
