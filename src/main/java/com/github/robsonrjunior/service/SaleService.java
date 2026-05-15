package com.github.robsonrjunior.service;

import com.github.robsonrjunior.domain.Sale;
import com.github.robsonrjunior.repository.SaleRepository;
import com.github.robsonrjunior.service.dto.SaleDTO;
import com.github.robsonrjunior.service.mapper.SaleMapper;
import java.util.Optional;
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

    private final SaleMapper saleMapper;

    public SaleService(SaleRepository saleRepository, SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
    }

    /**
     * Save a sale.
     *
     * @param saleDTO the entity to save.
     * @return the persisted entity.
     */
    public SaleDTO save(SaleDTO saleDTO) {
        LOG.debug("Request to save Sale : {}", saleDTO);
        Sale sale = saleMapper.toEntity(saleDTO);
        sale = saleRepository.save(sale);
        return saleMapper.toDto(sale);
    }

    /**
     * Update a sale.
     *
     * @param saleDTO the entity to save.
     * @return the persisted entity.
     */
    public SaleDTO update(SaleDTO saleDTO) {
        LOG.debug("Request to update Sale : {}", saleDTO);
        Sale sale = saleMapper.toEntity(saleDTO);
        sale = saleRepository.save(sale);
        return saleMapper.toDto(sale);
    }

    /**
     * Partially update a sale.
     *
     * @param saleDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SaleDTO> partialUpdate(SaleDTO saleDTO) {
        LOG.debug("Request to partially update Sale : {}", saleDTO);

        return saleRepository
            .findById(saleDTO.getId())
            .map(existingSale -> {
                saleMapper.partialUpdate(existingSale, saleDTO);

                return existingSale;
            })
            .map(saleRepository::save)
            .map(saleMapper::toDto);
    }

    /**
     * Get one sale by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SaleDTO> findOne(Long id) {
        LOG.debug("Request to get Sale : {}", id);
        return saleRepository.findById(id).map(saleMapper::toDto);
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
}
