package com.github.robsonrjunior.service;

import com.github.robsonrjunior.domain.SaleItem;
import com.github.robsonrjunior.repository.SaleItemRepository;
import java.util.Optional;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.robsonrjunior.domain.SaleItem}.
 */
@Service
@Transactional
public class SaleItemService {

    private static final Logger LOG = LoggerFactory.getLogger(SaleItemService.class);

    private final SaleItemRepository saleItemRepository;

    public SaleItemService(SaleItemRepository saleItemRepository) {
        this.saleItemRepository = saleItemRepository;
    }

    /**
     * Save a saleItem.
     *
     * @param saleItem the entity to save.
     * @return the persisted entity.
     */
    public SaleItem save(SaleItem saleItem) {
        LOG.debug("Request to save SaleItem : {}", saleItem);
        return saleItemRepository.save(saleItem);
    }

    /**
     * Update a saleItem.
     *
     * @param saleItem the entity to save.
     * @return the persisted entity.
     */
    public SaleItem update(SaleItem saleItem) {
        LOG.debug("Request to update SaleItem : {}", saleItem);
        return saleItemRepository.save(saleItem);
    }

    /**
     * Partially update a saleItem.
     *
     * @param saleItem the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SaleItem> partialUpdate(SaleItem saleItem) {
        LOG.debug("Request to partially update SaleItem : {}", saleItem);

        return saleItemRepository
            .findById(saleItem.getId())
            .map(existingSaleItem -> {
                updateIfPresent(existingSaleItem::setQuantity, saleItem.getQuantity());
                updateIfPresent(existingSaleItem::setUnitPrice, saleItem.getUnitPrice());
                updateIfPresent(existingSaleItem::setDiscountAmount, saleItem.getDiscountAmount());
                updateIfPresent(existingSaleItem::setLineTotal, saleItem.getLineTotal());
                updateIfPresent(existingSaleItem::setDeletedAt, saleItem.getDeletedAt());

                return existingSaleItem;
            })
            .map(saleItemRepository::save);
    }

    /**
     * Get one saleItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SaleItem> findOne(Long id) {
        LOG.debug("Request to get SaleItem : {}", id);
        return saleItemRepository.findById(id);
    }

    /**
     * Delete the saleItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SaleItem : {}", id);
        saleItemRepository.deleteById(id);
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
