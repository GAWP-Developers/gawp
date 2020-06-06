package com.gawpdevelopers.gawp.services;

import com.gawpdevelopers.gawp.commands.DocumentForm;
import com.gawpdevelopers.gawp.domain.Document;

import java.util.List;

public interface DocumentService {
    List<Document> listAll();

    Document getById(Long id);

    Document saveOrUpdate(Document document);

    void delete(Long id);

    Document saveOrUpdateDocumentForm(DocumentForm documentForm);
}
