package com.gawpdevelopers.gawp.services;

import com.gawpdevelopers.gawp.converters.DocumentFormToDocument;
import com.gawpdevelopers.gawp.commands.DocumentForm;
import com.gawpdevelopers.gawp.domain.Document;
import com.gawpdevelopers.gawp.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private DocumentRepository documentRepository;
    private DocumentFormToDocument documentFormToDocument;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository, DocumentFormToDocument documentFormToDocument) {
        this.documentRepository = documentRepository;
        this.documentFormToDocument = documentFormToDocument;
    }



    @Override
    public List<Document> listAll() {
        List<Document> documents = new ArrayList<>();
        documentRepository.findAll().forEach(documents::add); //fun with Java 8
        return documents;
    }

    @Override
    public Document getById(Long id) {
        if (documentRepository.findById(id).isPresent()) {
            return documentRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public Document saveOrUpdate(Document document) {
        documentRepository.save(document);
        return document;
    }

    @Override
    public void delete(Long id) {
        documentRepository.deleteById(id);

    }

    @Override
    public Document saveOrUpdateDocumentForm(DocumentForm documentForm) {
        Document savedDocument = saveOrUpdate(documentFormToDocument.convert(documentForm));

        System.out.println("Saved Document Id: " + savedDocument.getId());
        return savedDocument;
    }

}