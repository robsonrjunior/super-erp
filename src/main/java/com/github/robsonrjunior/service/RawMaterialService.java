package com.github.robsonrjunior.service;

import com.github.robsonrjunior.domain.RawMaterial;
import com.github.robsonrjunior.repository.RawMaterialRepository;
import com.github.robsonrjunior.service.dto.RawMaterialDTO;
import com.github.robsonrjunior.service.mapper.RawMaterialMapper;
import java.util.Optional;
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

    private final RawMaterialMapper rawMaterialMapper;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository, RawMaterialMapper rawMaterialMapper) {
        this.rawMaterialRepository = rawMaterialRepository;
        this.rawMaterialMapper = rawMaterialMapper;
    }

    /**
     * Save a rawMaterial.
     *
     * @param rawMaterialDTO the entity to save.
     * @return the persisted entity.
     */
    public RawMaterialDTO save(RawMaterialDTO rawMaterialDTO) {
        LOG.debug("Request to save RawMaterial : {}", rawMaterialDTO);
        RawMaterial rawMaterial = rawMaterialMapper.toEntity(rawMaterialDTO);
        rawMaterial = rawMaterialRepository.save(rawMaterial);
        return rawMaterialMapper.toDto(rawMaterial);
    }

    /**
     * Update a rawMaterial.
     *
     * @param rawMaterialDTO the entity to save.
     * @return the persisted entity.
     */
    public RawMaterialDTO update(RawMaterialDTO rawMaterialDTO) {
        LOG.debug("Request to update RawMaterial : {}", rawMaterialDTO);
        RawMaterial rawMaterial = rawMaterialMapper.toEntity(rawMaterialDTO);
        rawMaterial = rawMaterialRepository.save(rawMaterial);
        return rawMaterialMapper.toDto(rawMaterial);
    }

    /**
     * Partially update a rawMaterial.
     *
     * @param rawMaterialDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RawMaterialDTO> partialUpdate(RawMaterialDTO rawMaterialDTO) {
        LOG.debug("Request to partially update RawMaterial : {}", rawMaterialDTO);

        return rawMaterialRepository
            .findById(rawMaterialDTO.getId())
            .map(existingRawMaterial -> {
                rawMaterialMapper.partialUpdate(existingRawMaterial, rawMaterialDTO);

                return existingRawMaterial;
            })
            .map(rawMaterialRepository::save)
            .map(rawMaterialMapper::toDto);
    }

    /**
     * Get one rawMaterial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RawMaterialDTO> findOne(Long id) {
        LOG.debug("Request to get RawMaterial : {}", id);
        return rawMaterialRepository.findById(id).map(rawMaterialMapper::toDto);
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
}
