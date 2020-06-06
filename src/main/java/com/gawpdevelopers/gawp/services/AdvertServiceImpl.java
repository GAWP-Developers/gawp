package com.gawpdevelopers.gawp.services;

import com.gawpdevelopers.gawp.repositories.AdvertRepository;
import com.gawpdevelopers.gawp.commands.AdvertForm;
import com.gawpdevelopers.gawp.converters.AdvertFormToAdvert;
import com.gawpdevelopers.gawp.domain.Advert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        if (advertRepository.findById(id).isPresent()) {
            return advertRepository.findById(id).get();
        }
        else
            return null;
    }

    @Override
    public Advert saveOrUpdate(Advert advert) {
        advertRepository.save(advert);
        return advert;
    }

    @Override
    public void delete(Long id) {
        advertRepository.deleteById(id);

    }

    @Override
    public Advert saveOrUpdateAdvertForm(AdvertForm advertForm) {
        Advert savedAdvert = saveOrUpdate(advertFormToAdvert.convert(advertForm));

        System.out.println("Saved Advert Id: " + savedAdvert.getId());
        return savedAdvert;
    }
}
