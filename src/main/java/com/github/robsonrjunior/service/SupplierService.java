package com.github.robsonrjunior.service;

import com.github.robsonrjunior.domain.Supplier;
import com.github.robsonrjunior.repository.SupplierRepository;
import java.util.Optional;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.robsonrjunior.domain.Supplier}.
 */
@Service
@Transactional
public class SupplierService {

    private static final Logger LOG = LoggerFactory.getLogger(SupplierService.class);

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    /**
     * Save a supplier.
     *
     * @param supplier the entity to save.
     * @return the persisted entity.
     */
    public Supplier save(Supplier supplier) {
        LOG.debug("Request to save Supplier : {}", supplier);
        return supplierRepository.save(supplier);
    }

    /**
     * Update a supplier.
     *
     * @param supplier the entity to save.
     * @return the persisted entity.
     */
    public Supplier update(Supplier supplier) {
        LOG.debug("Request to update Supplier : {}", supplier);
        return supplierRepository.save(supplier);
    }

    /**
     * Partially update a supplier.
     *
     * @param supplier the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Supplier> partialUpdate(Supplier supplier) {
        LOG.debug("Request to partially update Supplier : {}", supplier);

        return supplierRepository
            .findById(supplier.getId())
            .map(existingSupplier -> {
                updateIfPresent(existingSupplier::setLegalName, supplier.getLegalName());
                updateIfPresent(existingSupplier::setTradeName, supplier.getTradeName());
                updateIfPresent(existingSupplier::setTaxId, supplier.getTaxId());
                updateIfPresent(existingSupplier::setPartyType, supplier.getPartyType());
                updateIfPresent(existingSupplier::setEmail, supplier.getEmail());
                updateIfPresent(existingSupplier::setPhone, supplier.getPhone());
                updateIfPresent(existingSupplier::setActive, supplier.getActive());
                updateIfPresent(existingSupplier::setDeletedAt, supplier.getDeletedAt());

                return existingSupplier;
            })
            .map(supplierRepository::save);
    }

    /**
     * Get one supplier by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Supplier> findOne(Long id) {
        LOG.debug("Request to get Supplier : {}", id);
        return supplierRepository.findById(id);
    }

    /**
     * Delete the supplier by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Supplier : {}", id);
        supplierRepository.deleteById(id);
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
