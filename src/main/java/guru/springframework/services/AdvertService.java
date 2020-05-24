package guru.springframework.services;

import guru.springframework.commands.AdvertForm;
import guru.springframework.domain.Advert;

import java.util.List;

/**
 * Created by jt on 1/10/17.
 */
public interface AdvertService {

    List<Advert> listAll();

    Advert getById(Long id);

    Advert saveOrUpdate(Advert advert);

    void delete(Long id);

    Advert saveOrUpdateAdvertForm(AdvertForm advertForm);
}
