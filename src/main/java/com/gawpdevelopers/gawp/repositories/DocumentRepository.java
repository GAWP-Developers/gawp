package com.gawpdevelopers.gawp.repositories;

import com.gawpdevelopers.gawp.domain.Document;
import org.springframework.data.repository.CrudRepository;

public interface DocumentRepository extends CrudRepository<Document, Long> {
}