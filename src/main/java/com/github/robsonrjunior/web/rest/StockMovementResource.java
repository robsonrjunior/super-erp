package com.github.robsonrjunior.web.rest;

import com.github.robsonrjunior.domain.StockMovement;
import com.github.robsonrjunior.repository.StockMovementRepository;
import com.github.robsonrjunior.service.StockMovementQueryService;
import com.github.robsonrjunior.service.StockMovementService;
import com.github.robsonrjunior.service.criteria.StockMovementCriteria;
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
 * REST controller for managing {@link com.github.robsonrjunior.domain.StockMovement}.
 */
@RestController
@RequestMapping("/api/stock-movements")
public class StockMovementResource {

    private static final Logger LOG = LoggerFactory.getLogger(StockMovementResource.class);

    private static final String ENTITY_NAME = "stockMovement";

    @Value("${jhipster.clientApp.name:supererp}")
    private String applicationName;

    private final StockMovementService stockMovementService;

    private final StockMovementRepository stockMovementRepository;

    private final StockMovementQueryService stockMovementQueryService;

    public StockMovementResource(
        StockMovementService stockMovementService,
        StockMovementRepository stockMovementRepository,
        StockMovementQueryService stockMovementQueryService
    ) {
        this.stockMovementService = stockMovementService;
        this.stockMovementRepository = stockMovementRepository;
        this.stockMovementQueryService = stockMovementQueryService;
    }

    /**
     * {@code POST  /stock-movements} : Create a new stockMovement.
     *
     * @param stockMovement the stockMovement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockMovement, or with status {@code 400 (Bad Request)} if the stockMovement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StockMovement> createStockMovement(@Valid @RequestBody StockMovement stockMovement) throws URISyntaxException {
        LOG.debug("REST request to save StockMovement : {}", stockMovement);
        if (stockMovement.getId() != null) {
            throw new BadRequestAlertException("A new stockMovement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        stockMovement = stockMovementService.save(stockMovement);
        return ResponseEntity.created(new URI("/api/stock-movements/" + stockMovement.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, stockMovement.getId().toString()))
            .body(stockMovement);
    }

    /**
     * {@code PUT  /stock-movements/:id} : Updates an existing stockMovement.
     *
     * @param id the id of the stockMovement to save.
     * @param stockMovement the stockMovement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockMovement,
     * or with status {@code 400 (Bad Request)} if the stockMovement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockMovement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StockMovement> updateStockMovement(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StockMovement stockMovement
    ) throws URISyntaxException {
        LOG.debug("REST request to update StockMovement : {}, {}", id, stockMovement);
        if (stockMovement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stockMovement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stockMovementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        stockMovement = stockMovementService.update(stockMovement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockMovement.getId().toString()))
            .body(stockMovement);
    }

    /**
     * {@code PATCH  /stock-movements/:id} : Partial updates given fields of an existing stockMovement, field will ignore if it is null
     *
     * @param id the id of the stockMovement to save.
     * @param stockMovement the stockMovement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockMovement,
     * or with status {@code 400 (Bad Request)} if the stockMovement is not valid,
     * or with status {@code 404 (Not Found)} if the stockMovement is not found,
     * or with status {@code 500 (Internal Server Error)} if the stockMovement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StockMovement> partialUpdateStockMovement(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StockMovement stockMovement
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update StockMovement partially : {}, {}", id, stockMovement);
        if (stockMovement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stockMovement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stockMovementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StockMovement> result = stockMovementService.partialUpdate(stockMovement);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockMovement.getId().toString())
        );
    }

    /**
     * {@code GET  /stock-movements} : get all the Stock Movements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Stock Movements in body.
     */
    @GetMapping("")
    public ResponseEntity<List<StockMovement>> getAllStockMovements(
        StockMovementCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get StockMovements by criteria: {}", criteria);

        Page<StockMovement> page = stockMovementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stock-movements/count} : count all the stockMovements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countStockMovements(StockMovementCriteria criteria) {
        LOG.debug("REST request to count StockMovements by criteria: {}", criteria);
        return ResponseEntity.ok().body(stockMovementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /stock-movements/:id} : get the "id" stockMovement.
     *
     * @param id the id of the stockMovement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockMovement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StockMovement> getStockMovement(@PathVariable("id") Long id) {
        LOG.debug("REST request to get StockMovement : {}", id);
        Optional<StockMovement> stockMovement = stockMovementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockMovement);
    }

    /**
     * {@code DELETE  /stock-movements/:id} : delete the "id" stockMovement.
     *
     * @param id the id of the stockMovement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockMovement(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete StockMovement : {}", id);
        stockMovementService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
