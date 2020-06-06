package com.gawpdevelopers.gawp.commands;

import com.gawpdevelopers.gawp.domain.Application;
import com.gawpdevelopers.gawp.domain.DocumentType;

/**
 * Used to send to the html file for the user to form.
 * It is get from the post request sent by the user to html form.
 */
public class DocumentForm {
    private Long id;
    private DocumentType docType;
    private String path;
    private Application application;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocumentType getDocType() {
        return docType;
    }

    public void setDocType(DocumentType docType) {
        this.docType = docType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
