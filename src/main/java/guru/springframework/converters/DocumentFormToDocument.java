package guru.springframework.converters;

import guru.springframework.commands.DocumentForm;
import guru.springframework.domain.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by jt on 1/10/17.
 */
@Component
public class DocumentFormToDocument implements Converter<DocumentForm, Document> {

    @Override
    public Document convert(DocumentForm documentForm) {
        Document document = new Document();
        if (documentForm.getId() != null  && !StringUtils.isEmpty(documentForm.getId())) {
            document.setId(new Long(documentForm.getId()));
        }
        document.setDocType(documentForm.getDocType());
        document.setPath(documentForm.getPath());
        document.setApplication(documentForm.getApplication());
        return document;
    }
}
