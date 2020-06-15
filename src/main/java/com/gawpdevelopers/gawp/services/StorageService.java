package com.gawpdevelopers.gawp.services;

import com.gawpdevelopers.gawp.domain.Document;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	Path store(MultipartFile file);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	Resource loadAsResource(Document doc);

	void deleteAll();

}
