package guru.springframework.services;

import guru.springframework.commands.AdvertForm;
import guru.springframework.converters.AdvertFormToAdvert;
import guru.springframework.domain.Advert;
import guru.springframework.repositories.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class AdvertServiceImpl implements AdvertService {

    private AdvertRepository advertRepository;
    private AdvertFormToAdvert advertFormToAdvert;

    @Autowired
    public AdvertServiceImpl(AdvertRepository advertRepository, AdvertFormToAdvert advertFormToAdvert) {
        this.advertRepository = advertRepository;
        this.advertFormToAdvert = advertFormToAdvert;
    }

    @Override
    public List<Advert> listAll() {
        List<Advert> adverts = new ArrayList<>();
        advertRepository.findAll().forEach(adverts::add); //fun with Java 8
        return adverts;
    }

    @Override
    public Advert getById(Long id) {
        return advertRepository.findOne(id);
    }

    @Override
    public Advert saveOrUpdate(Advert advert) {
        advertRepository.save(advert);
        return advert;
    }

    @Override
    public void delete(Long id) {
        advertRepository.delete(id);

    }

    @Override
    public Advert saveOrUpdateAdvertForm(AdvertForm advertForm) {
        Advert savedAdvert = saveOrUpdate(advertFormToAdvert.convert(advertForm));

        System.out.println("Saved Advert Id: " + savedAdvert.getId());
        return savedAdvert;
    }
}
