package guru.springframework.repositories;

import guru.springframework.domain.Advert;
import guru.springframework.domain.Document;
import org.springframework.data.repository.CrudRepository;

public interface DocumentRepository extends CrudRepository<Document, Long> {
}