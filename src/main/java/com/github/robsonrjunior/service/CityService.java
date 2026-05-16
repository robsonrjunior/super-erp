package com.github.robsonrjunior.service;

import com.github.robsonrjunior.domain.City;
import com.github.robsonrjunior.repository.CityRepository;
import java.util.Optional;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.robsonrjunior.domain.City}.
 */
@Service
@Transactional
public class CityService {

    private static final Logger LOG = LoggerFactory.getLogger(CityService.class);

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    /**
     * Save a city.
     *
     * @param city the entity to save.
     * @return the persisted entity.
     */
    public City save(City city) {
        LOG.debug("Request to save City : {}", city);
        return cityRepository.save(city);
    }

    /**
     * Update a city.
     *
     * @param city the entity to save.
     * @return the persisted entity.
     */
    public City update(City city) {
        LOG.debug("Request to update City : {}", city);
        return cityRepository.save(city);
    }

    /**
     * Partially update a city.
     *
     * @param city the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<City> partialUpdate(City city) {
        LOG.debug("Request to partially update City : {}", city);

        return cityRepository
            .findById(city.getId())
            .map(existingCity -> {
                updateIfPresent(existingCity::setName, city.getName());

                return existingCity;
            })
            .map(cityRepository::save);
    }

    /**
     * Get one city by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<City> findOne(Long id) {
        LOG.debug("Request to get City : {}", id);
        return cityRepository.findById(id);
    }

    /**
     * Delete the city by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete City : {}", id);
        cityRepository.deleteById(id);
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
