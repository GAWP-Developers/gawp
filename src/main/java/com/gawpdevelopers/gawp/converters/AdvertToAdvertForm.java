package com.gawpdevelopers.gawp.converters;

import com.gawpdevelopers.gawp.commands.AdvertForm;
import com.gawpdevelopers.gawp.domain.Advert;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AdvertToAdvertForm implements Converter<Advert, AdvertForm> {
    @Override
    public AdvertForm convert(Advert advert) {
        AdvertForm advertForm = new AdvertForm();

        advertForm.setId(advert.getId());
        advertForm.setName(advert.getName());
        advertForm.setGradID(advert.getGradID());
        advertForm.setIntInfoID(advert.getIntInfoID());
        advertForm.setShareDate(advert.getShareDate());
        advertForm.setDeadlineDate(advert.getDeadlineDate());
        advertForm.setType(advert.getType());
        advertForm.setDetails(advert.getDetails());
        advertForm.setDepartmentType(advertForm.getDepartmentType());
        return advertForm;
    }
}
