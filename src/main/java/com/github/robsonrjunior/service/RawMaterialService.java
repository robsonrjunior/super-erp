package com.github.robsonrjunior.service;

import com.github.robsonrjunior.domain.RawMaterial;
import com.github.robsonrjunior.repository.RawMaterialRepository;
import java.util.Optional;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.robsonrjunior.domain.RawMaterial}.
 */
@Service
@Transactional
public class RawMaterialService {

    private static final Logger LOG = LoggerFactory.getLogger(RawMaterialService.class);

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    /**
     * Save a rawMaterial.
     *
     * @param rawMaterial the entity to save.
     * @return the persisted entity.
     */
    public RawMaterial save(RawMaterial rawMaterial) {
        LOG.debug("Request to save RawMaterial : {}", rawMaterial);
        return rawMaterialRepository.save(rawMaterial);
    }

    /**
     * Update a rawMaterial.
     *
     * @param rawMaterial the entity to save.
     * @return the persisted entity.
     */
    public RawMaterial update(RawMaterial rawMaterial) {
        LOG.debug("Request to update RawMaterial : {}", rawMaterial);
        return rawMaterialRepository.save(rawMaterial);
    }

    /**
     * Partially update a rawMaterial.
     *
     * @param rawMaterial the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RawMaterial> partialUpdate(RawMaterial rawMaterial) {
        LOG.debug("Request to partially update RawMaterial : {}", rawMaterial);

        return rawMaterialRepository
            .findById(rawMaterial.getId())
            .map(existingRawMaterial -> {
                updateIfPresent(existingRawMaterial::setName, rawMaterial.getName());
                updateIfPresent(existingRawMaterial::setSku, rawMaterial.getSku());
                updateIfPresent(existingRawMaterial::setUnitOfMeasure, rawMaterial.getUnitOfMeasure());
                updateIfPresent(existingRawMaterial::setUnitDecimalPlaces, rawMaterial.getUnitDecimalPlaces());
                updateIfPresent(existingRawMaterial::setUnitCost, rawMaterial.getUnitCost());
                updateIfPresent(existingRawMaterial::setMinStock, rawMaterial.getMinStock());
                updateIfPresent(existingRawMaterial::setActive, rawMaterial.getActive());
                updateIfPresent(existingRawMaterial::setDeletedAt, rawMaterial.getDeletedAt());

                return existingRawMaterial;
            })
            .map(rawMaterialRepository::save);
    }

    /**
     * Get one rawMaterial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RawMaterial> findOne(Long id) {
        LOG.debug("Request to get RawMaterial : {}", id);
        return rawMaterialRepository.findById(id);
    }

    /**
     * Delete the rawMaterial by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete RawMaterial : {}", id);
        rawMaterialRepository.deleteById(id);
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
