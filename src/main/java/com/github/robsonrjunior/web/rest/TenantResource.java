package com.github.robsonrjunior.web.rest;

import com.github.robsonrjunior.domain.Tenant;
import com.github.robsonrjunior.repository.TenantRepository;
import com.github.robsonrjunior.service.TenantQueryService;
import com.github.robsonrjunior.service.TenantService;
import com.github.robsonrjunior.service.criteria.TenantCriteria;
import com.github.robsonrjunior.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.github.robsonrjunior.domain.Tenant}.
 */
@RestController
@RequestMapping("/api/tenants")
public class TenantResource {

    private static final Logger LOG = LoggerFactory.getLogger(TenantResource.class);

    private static final String ENTITY_NAME = "tenant";

    @Value("${jhipster.clientApp.name:supererp}")
    private String applicationName;

    private final TenantService tenantService;

    private final TenantRepository tenantRepository;

    private final TenantQueryService tenantQueryService;

    public TenantResource(TenantService tenantService, TenantRepository tenantRepository, TenantQueryService tenantQueryService) {
        this.tenantService = tenantService;
        this.tenantRepository = tenantRepository;
        this.tenantQueryService = tenantQueryService;
    }

    /**
     * {@code POST  /tenants} : Create a new tenant.
     *
     * @param tenant the tenant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tenant, or with status {@code 400 (Bad Request)} if the tenant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Tenant> createTenant(@Valid @RequestBody Tenant tenant) throws URISyntaxException {
        LOG.debug("REST request to save Tenant : {}", tenant);
        if (tenant.getId() != null) {
            throw new BadRequestAlertException("A new tenant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tenant = tenantService.save(tenant);
        return ResponseEntity.created(new URI("/api/tenants/" + tenant.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tenant.getId().toString()))
            .body(tenant);
    }

    /**
     * {@code PUT  /tenants/:id} : Updates an existing tenant.
     *
     * @param id the id of the tenant to save.
     * @param tenant the tenant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tenant,
     * or with status {@code 400 (Bad Request)} if the tenant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tenant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tenant> updateTenant(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Tenant tenant
    ) throws URISyntaxException {
        LOG.debug("REST request to update Tenant : {}, {}", id, tenant);
        if (tenant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tenant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tenantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tenant = tenantService.update(tenant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tenant.getId().toString()))
            .body(tenant);
    }

    /**
     * {@code PATCH  /tenants/:id} : Partial updates given fields of an existing tenant, field will ignore if it is null
     *
     * @param id the id of the tenant to save.
     * @param tenant the tenant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tenant,
     * or with status {@code 400 (Bad Request)} if the tenant is not valid,
     * or with status {@code 404 (Not Found)} if the tenant is not found,
     * or with status {@code 500 (Internal Server Error)} if the tenant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tenant> partialUpdateTenant(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Tenant tenant
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Tenant partially : {}, {}", id, tenant);
        if (tenant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tenant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tenantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tenant> result = tenantService.partialUpdate(tenant);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tenant.getId().toString())
        );
    }

    /**
     * {@code GET  /tenants} : get all the Tenants.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Tenants in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Tenant>> getAllTenants(
        TenantCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Tenants by criteria: {}", criteria);

        Page<Tenant> page = tenantQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tenants/count} : count all the tenants.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTenants(TenantCriteria criteria) {
        LOG.debug("REST request to count Tenants by criteria: {}", criteria);
        return ResponseEntity.ok().body(tenantQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tenants/:id} : get the "id" tenant.
     *
     * @param id the id of the tenant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tenant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tenant> getTenant(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Tenant : {}", id);
        Optional<Tenant> tenant = tenantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tenant);
    }

    /**
     * {@code DELETE  /tenants/:id} : delete the "id" tenant.
     *
     * @param id the id of the tenant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Tenant : {}", id);
        tenantService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
