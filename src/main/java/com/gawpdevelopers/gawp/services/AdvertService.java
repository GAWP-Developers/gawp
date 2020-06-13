package com.gawpdevelopers.gawp.services;

import com.gawpdevelopers.gawp.commands.AdvertForm;
import com.gawpdevelopers.gawp.domain.Advert;
import com.gawpdevelopers.gawp.domain.ApplicationStatus;

import java.util.List;

public interface AdvertService {

    List<Advert> listAll();

    Advert getById(Long id);

    Advert saveOrUpdate(Advert advert);

    void delete(Long id);

    Advert saveOrUpdateAdvertForm(AdvertForm advertForm);
}
