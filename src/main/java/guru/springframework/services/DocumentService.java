package guru.springframework.services;

import guru.springframework.commands.DocumentForm;
import guru.springframework.domain.Document;

import java.util.List;

public interface DocumentService {
    List<Document> listAll();

    Document getById(Long id);

    Document saveOrUpdate(Document document);

    void delete(Long id);

    Document saveOrUpdateDocumentForm(DocumentForm documentForm);
}
