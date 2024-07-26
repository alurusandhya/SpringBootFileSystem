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
		}
		 }catch (IOException e) {
			 return name + ", folder creation failed";
		}
		return name + ", folder create successfully";

		
	}

	@Override
	public String deleteFolder(String path) {
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
		boolean exists =      file.exists();      
		boolean isDirectory = file.isDirectory(); 	
		EntityProperties ep;
		if(exists) {
			if(isDirectory) {
				ep=new EntityProperties("Directory",file.getName(),file.getPath(), (int)getDirectorySizeJava8(path));
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
