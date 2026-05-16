package com.github.robsonrjunior.service;

import com.github.robsonrjunior.domain.Sale;
import com.github.robsonrjunior.repository.SaleRepository;
import java.util.Optional;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.robsonrjunior.domain.Sale}.
 */
@Service
@Transactional
public class SaleService {

    private static final Logger LOG = LoggerFactory.getLogger(SaleService.class);

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    /**
     * Save a sale.
     *
     * @param sale the entity to save.
     * @return the persisted entity.
     */
    public Sale save(Sale sale) {
        LOG.debug("Request to save Sale : {}", sale);
        return saleRepository.save(sale);
    }

    /**
     * Update a sale.
     *
     * @param sale the entity to save.
     * @return the persisted entity.
     */
    public Sale update(Sale sale) {
        LOG.debug("Request to update Sale : {}", sale);
        return saleRepository.save(sale);
    }

    /**
     * Partially update a sale.
     *
     * @param sale the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Sale> partialUpdate(Sale sale) {
        LOG.debug("Request to partially update Sale : {}", sale);

        return saleRepository
            .findById(sale.getId())
            .map(existingSale -> {
                updateIfPresent(existingSale::setSaleDate, sale.getSaleDate());
                updateIfPresent(existingSale::setSaleNumber, sale.getSaleNumber());
                updateIfPresent(existingSale::setStatus, sale.getStatus());
                updateIfPresent(existingSale::setGrossAmount, sale.getGrossAmount());
                updateIfPresent(existingSale::setDiscountAmount, sale.getDiscountAmount());
                updateIfPresent(existingSale::setNetAmount, sale.getNetAmount());
                updateIfPresent(existingSale::setNotes, sale.getNotes());
                updateIfPresent(existingSale::setDeletedAt, sale.getDeletedAt());

                return existingSale;
            })
            .map(saleRepository::save);
    }

    /**
     * Get one sale by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Sale> findOne(Long id) {
        LOG.debug("Request to get Sale : {}", id);
        return saleRepository.findById(id);
    }

    /**
     * Delete the sale by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Sale : {}", id);
        saleRepository.deleteById(id);
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
