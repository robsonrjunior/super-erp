package com.github.robsonrjunior.service;

import com.github.robsonrjunior.domain.StockMovement;
import com.github.robsonrjunior.repository.StockMovementRepository;
import com.github.robsonrjunior.service.dto.StockMovementDTO;
import com.github.robsonrjunior.service.mapper.StockMovementMapper;
import java.util.Optional;
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

    private final StockMovementMapper stockMovementMapper;

    public StockMovementService(StockMovementRepository stockMovementRepository, StockMovementMapper stockMovementMapper) {
        this.stockMovementRepository = stockMovementRepository;
        this.stockMovementMapper = stockMovementMapper;
    }

    /**
     * Save a stockMovement.
     *
     * @param stockMovementDTO the entity to save.
     * @return the persisted entity.
     */
    public StockMovementDTO save(StockMovementDTO stockMovementDTO) {
        LOG.debug("Request to save StockMovement : {}", stockMovementDTO);
        StockMovement stockMovement = stockMovementMapper.toEntity(stockMovementDTO);
        stockMovement = stockMovementRepository.save(stockMovement);
        return stockMovementMapper.toDto(stockMovement);
    }

    /**
     * Update a stockMovement.
     *
     * @param stockMovementDTO the entity to save.
     * @return the persisted entity.
     */
    public StockMovementDTO update(StockMovementDTO stockMovementDTO) {
        LOG.debug("Request to update StockMovement : {}", stockMovementDTO);
        StockMovement stockMovement = stockMovementMapper.toEntity(stockMovementDTO);
        stockMovement = stockMovementRepository.save(stockMovement);
        return stockMovementMapper.toDto(stockMovement);
    }

    /**
     * Partially update a stockMovement.
     *
     * @param stockMovementDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StockMovementDTO> partialUpdate(StockMovementDTO stockMovementDTO) {
        LOG.debug("Request to partially update StockMovement : {}", stockMovementDTO);

        return stockMovementRepository
            .findById(stockMovementDTO.getId())
            .map(existingStockMovement -> {
                stockMovementMapper.partialUpdate(existingStockMovement, stockMovementDTO);

                return existingStockMovement;
            })
            .map(stockMovementRepository::save)
            .map(stockMovementMapper::toDto);
    }

    /**
     * Get one stockMovement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StockMovementDTO> findOne(Long id) {
        LOG.debug("Request to get StockMovement : {}", id);
        return stockMovementRepository.findById(id).map(stockMovementMapper::toDto);
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
}
