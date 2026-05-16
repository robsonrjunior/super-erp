package com.github.robsonrjunior.service;

import com.github.robsonrjunior.domain.Warehouse;
import com.github.robsonrjunior.repository.WarehouseRepository;
import java.util.Optional;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.robsonrjunior.domain.Warehouse}.
 */
@Service
@Transactional
public class WarehouseService {

    private static final Logger LOG = LoggerFactory.getLogger(WarehouseService.class);

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * Save a warehouse.
     *
     * @param warehouse the entity to save.
     * @return the persisted entity.
     */
    public Warehouse save(Warehouse warehouse) {
        LOG.debug("Request to save Warehouse : {}", warehouse);
        return warehouseRepository.save(warehouse);
    }

    /**
     * Update a warehouse.
     *
     * @param warehouse the entity to save.
     * @return the persisted entity.
     */
    public Warehouse update(Warehouse warehouse) {
        LOG.debug("Request to update Warehouse : {}", warehouse);
        return warehouseRepository.save(warehouse);
    }

    /**
     * Partially update a warehouse.
     *
     * @param warehouse the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Warehouse> partialUpdate(Warehouse warehouse) {
        LOG.debug("Request to partially update Warehouse : {}", warehouse);

        return warehouseRepository
            .findById(warehouse.getId())
            .map(existingWarehouse -> {
                updateIfPresent(existingWarehouse::setName, warehouse.getName());
                updateIfPresent(existingWarehouse::setCode, warehouse.getCode());
                updateIfPresent(existingWarehouse::setActive, warehouse.getActive());
                updateIfPresent(existingWarehouse::setDeletedAt, warehouse.getDeletedAt());

                return existingWarehouse;
            })
            .map(warehouseRepository::save);
    }

    /**
     * Get one warehouse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Warehouse> findOne(Long id) {
        LOG.debug("Request to get Warehouse : {}", id);
        return warehouseRepository.findById(id);
    }

    /**
     * Delete the warehouse by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Warehouse : {}", id);
        warehouseRepository.deleteById(id);
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
