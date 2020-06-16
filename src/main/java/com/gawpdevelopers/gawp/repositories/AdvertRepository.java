package com.gawpdevelopers.gawp.repositories;

import com.gawpdevelopers.gawp.domain.Advert;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdvertRepository extends CrudRepository<Advert, Long> {
    List<Advert> findAdvertsByGradID(Long id);
}
