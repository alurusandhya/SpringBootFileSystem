package com.example.demo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.service.FileOperationsService;

@Service
public class FileOperationServiceImpl implements FileOperationsService {

	@Value("${root.directory.path}")
	private String rootDirectoryOfFileSystem;

	@Override
	public String createFile(String name) {
		try {
			if (new File(rootDirectoryOfFileSystem + "/" + name).createNewFile()) {
				return name + ", File create successfully!!";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return name + ", File creation failed";
	}

	@Override
	public String deleteFile(String path) {

		File directory = new File(path);
		try (Stream<Path> pathStream = Files.walk(directory.toPath())) {
			pathStream.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
		} catch (IOException e) {
			e.printStackTrace();
			return "File deletion failed due to => " + e.getClass();
		}

		return path + ", File deleted successfully!!";
	}

	@Override
	public String moveFile(String sourcePath, String destinationPath) {

		try {
			Path source=Paths.get(sourcePath);
			List<String> destFolder=Arrays.asList(destinationPath.split("\\\\"));
			List<String> destFolderFinal=destFolder.subList(0, destFolder.size()-1);
			File directory = new File(destFolderFinal.stream().collect(Collectors.joining("\\\\")));
		    if (! directory.exists()){
		        directory.mkdir();
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

	@Override
	public String writeAFile(String path, String content) {
		Path filePath = Paths.get(rootDirectoryOfFileSystem + "/" + path);
		File file = new File(rootDirectoryOfFileSystem + "/" + path);

		try {
			if (file.createNewFile()) {
			    System.out.println("File created: " + file.getName());
			  } else {
			    System.out.println("File already exists.");
			  }
			Files.write(filePath, content.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return path+"Data added to the file Succesfully";
	}
}
