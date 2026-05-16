package com.github.robsonrjunior.service;

import com.github.robsonrjunior.domain.StockMovement;
import com.github.robsonrjunior.repository.StockMovementRepository;
import java.util.Optional;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.robsonrjunior.domain.StockMovement}.
 */
@Service
@Transactional
public class StockMovementService {

    private static final Logger LOG = LoggerFactory.getLogger(StockMovementService.class);

    private final StockMovementRepository stockMovementRepository;

    public StockMovementService(StockMovementRepository stockMovementRepository) {
        this.stockMovementRepository = stockMovementRepository;
    }

    /**
     * Save a stockMovement.
     *
     * @param stockMovement the entity to save.
     * @return the persisted entity.
     */
    public StockMovement save(StockMovement stockMovement) {
        LOG.debug("Request to save StockMovement : {}", stockMovement);
        return stockMovementRepository.save(stockMovement);
    }

    /**
     * Update a stockMovement.
     *
     * @param stockMovement the entity to save.
     * @return the persisted entity.
     */
    public StockMovement update(StockMovement stockMovement) {
        LOG.debug("Request to update StockMovement : {}", stockMovement);
        return stockMovementRepository.save(stockMovement);
    }

    /**
     * Partially update a stockMovement.
     *
     * @param stockMovement the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StockMovement> partialUpdate(StockMovement stockMovement) {
        LOG.debug("Request to partially update StockMovement : {}", stockMovement);

        return stockMovementRepository
            .findById(stockMovement.getId())
            .map(existingStockMovement -> {
                updateIfPresent(existingStockMovement::setMovementDate, stockMovement.getMovementDate());
                updateIfPresent(existingStockMovement::setMovementType, stockMovement.getMovementType());
                updateIfPresent(existingStockMovement::setQuantity, stockMovement.getQuantity());
                updateIfPresent(existingStockMovement::setUnitCost, stockMovement.getUnitCost());
                updateIfPresent(existingStockMovement::setReferenceNumber, stockMovement.getReferenceNumber());
                updateIfPresent(existingStockMovement::setNotes, stockMovement.getNotes());
                updateIfPresent(existingStockMovement::setDeletedAt, stockMovement.getDeletedAt());

                return existingStockMovement;
            })
            .map(stockMovementRepository::save);
    }

    /**
     * Get one stockMovement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StockMovement> findOne(Long id) {
        LOG.debug("Request to get StockMovement : {}", id);
        return stockMovementRepository.findById(id);
    }

    /**
     * Delete the stockMovement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete StockMovement : {}", id);
        stockMovementRepository.deleteById(id);
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
