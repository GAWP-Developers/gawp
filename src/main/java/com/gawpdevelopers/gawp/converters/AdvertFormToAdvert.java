package com.gawpdevelopers.gawp.converters;

import com.gawpdevelopers.gawp.commands.AdvertForm;
import com.gawpdevelopers.gawp.domain.Advert;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AdvertFormToAdvert implements Converter<AdvertForm, Advert> {

    @Override
    public Advert convert(AdvertForm advertForm) {
        Advert advert = new Advert();
        if (advertForm.getId() != null  && !StringUtils.isEmpty(advertForm.getId())) {
            advert.setId(new Long(advertForm.getId()));
        }
        advert.setName(advertForm.getName());
        advert.setGradID(advertForm.getGradID());
        advert.setIntInfoID(advertForm.getIntInfoID());
        advert.setShareDate(advertForm.getShareDate());
        advert.setDeadlineDate(advertForm.getDeadlineDate());
        advert.setType(advertForm.getType());
        advert.setDetails(advertForm.getDetails());
        return advert;
    }
}
