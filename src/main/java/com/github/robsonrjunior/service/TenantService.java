package com.github.robsonrjunior.service;

import com.github.robsonrjunior.domain.Tenant;
import com.github.robsonrjunior.repository.TenantRepository;
import java.util.Optional;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.robsonrjunior.domain.Tenant}.
 */
@Service
@Transactional
public class TenantService {

    private static final Logger LOG = LoggerFactory.getLogger(TenantService.class);

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    /**
     * Save a tenant.
     *
     * @param tenant the entity to save.
     * @return the persisted entity.
     */
    public Tenant save(Tenant tenant) {
        LOG.debug("Request to save Tenant : {}", tenant);
        return tenantRepository.save(tenant);
    }

    /**
     * Update a tenant.
     *
     * @param tenant the entity to save.
     * @return the persisted entity.
     */
    public Tenant update(Tenant tenant) {
        LOG.debug("Request to update Tenant : {}", tenant);
        return tenantRepository.save(tenant);
    }

    /**
     * Partially update a tenant.
     *
     * @param tenant the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Tenant> partialUpdate(Tenant tenant) {
        LOG.debug("Request to partially update Tenant : {}", tenant);

        return tenantRepository
            .findById(tenant.getId())
            .map(existingTenant -> {
                updateIfPresent(existingTenant::setName, tenant.getName());
                updateIfPresent(existingTenant::setCode, tenant.getCode());
                updateIfPresent(existingTenant::setActive, tenant.getActive());
                updateIfPresent(existingTenant::setDeletedAt, tenant.getDeletedAt());

                return existingTenant;
            })
            .map(tenantRepository::save);
    }

    /**
     * Get one tenant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Tenant> findOne(Long id) {
        LOG.debug("Request to get Tenant : {}", id);
        return tenantRepository.findById(id);
    }

    /**
     * Delete the tenant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Tenant : {}", id);
        tenantRepository.deleteById(id);
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
