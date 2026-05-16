package com.github.robsonrjunior.web.rest;

import com.github.robsonrjunior.domain.SaleItem;
import com.github.robsonrjunior.repository.SaleItemRepository;
import com.github.robsonrjunior.service.SaleItemQueryService;
import com.github.robsonrjunior.service.SaleItemService;
import com.github.robsonrjunior.service.criteria.SaleItemCriteria;
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
 * REST controller for managing {@link com.github.robsonrjunior.domain.SaleItem}.
 */
@RestController
@RequestMapping("/api/sale-items")
public class SaleItemResource {

    private static final Logger LOG = LoggerFactory.getLogger(SaleItemResource.class);

    private static final String ENTITY_NAME = "saleItem";

    @Value("${jhipster.clientApp.name:supererp}")
    private String applicationName;

    private final SaleItemService saleItemService;

    private final SaleItemRepository saleItemRepository;

    private final SaleItemQueryService saleItemQueryService;

    public SaleItemResource(
        SaleItemService saleItemService,
        SaleItemRepository saleItemRepository,
        SaleItemQueryService saleItemQueryService
    ) {
        this.saleItemService = saleItemService;
        this.saleItemRepository = saleItemRepository;
        this.saleItemQueryService = saleItemQueryService;
    }

    /**
     * {@code POST  /sale-items} : Create a new saleItem.
     *
     * @param saleItem the saleItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saleItem, or with status {@code 400 (Bad Request)} if the saleItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SaleItem> createSaleItem(@Valid @RequestBody SaleItem saleItem) throws URISyntaxException {
        LOG.debug("REST request to save SaleItem : {}", saleItem);
        if (saleItem.getId() != null) {
            throw new BadRequestAlertException("A new saleItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        saleItem = saleItemService.save(saleItem);
        return ResponseEntity.created(new URI("/api/sale-items/" + saleItem.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, saleItem.getId().toString()))
            .body(saleItem);
    }

    /**
     * {@code PUT  /sale-items/:id} : Updates an existing saleItem.
     *
     * @param id the id of the saleItem to save.
     * @param saleItem the saleItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saleItem,
     * or with status {@code 400 (Bad Request)} if the saleItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saleItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SaleItem> updateSaleItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SaleItem saleItem
    ) throws URISyntaxException {
        LOG.debug("REST request to update SaleItem : {}, {}", id, saleItem);
        if (saleItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saleItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        saleItem = saleItemService.update(saleItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saleItem.getId().toString()))
            .body(saleItem);
    }

    /**
     * {@code PATCH  /sale-items/:id} : Partial updates given fields of an existing saleItem, field will ignore if it is null
     *
     * @param id the id of the saleItem to save.
     * @param saleItem the saleItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saleItem,
     * or with status {@code 400 (Bad Request)} if the saleItem is not valid,
     * or with status {@code 404 (Not Found)} if the saleItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the saleItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SaleItem> partialUpdateSaleItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SaleItem saleItem
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SaleItem partially : {}, {}", id, saleItem);
        if (saleItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saleItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaleItem> result = saleItemService.partialUpdate(saleItem);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saleItem.getId().toString())
        );
    }

    /**
     * {@code GET  /sale-items} : get all the Sale Items.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Sale Items in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SaleItem>> getAllSaleItems(
        SaleItemCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SaleItems by criteria: {}", criteria);

        Page<SaleItem> page = saleItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sale-items/count} : count all the saleItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSaleItems(SaleItemCriteria criteria) {
        LOG.debug("REST request to count SaleItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(saleItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sale-items/:id} : get the "id" saleItem.
     *
     * @param id the id of the saleItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saleItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SaleItem> getSaleItem(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SaleItem : {}", id);
        Optional<SaleItem> saleItem = saleItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(saleItem);
    }

    /**
     * {@code DELETE  /sale-items/:id} : delete the "id" saleItem.
     *
     * @param id the id of the saleItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaleItem(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SaleItem : {}", id);
        saleItemService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
