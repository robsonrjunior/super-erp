package com.github.robsonrjunior.web.rest;

import static com.github.robsonrjunior.domain.SaleAsserts.*;
import static com.github.robsonrjunior.web.rest.TestUtil.createUpdateProxyForBean;
import static com.github.robsonrjunior.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.robsonrjunior.IntegrationTest;
import com.github.robsonrjunior.domain.Sale;
import com.github.robsonrjunior.domain.SaleItem;
import com.github.robsonrjunior.domain.enumeration.SaleStatus;
import com.github.robsonrjunior.repository.SaleRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SaleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaleResourceIT {

    private static final Instant DEFAULT_SALE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SALE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SALE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SALE_NUMBER = "BBBBBBBBBB";

    private static final SaleStatus DEFAULT_STATUS = SaleStatus.OPEN;
    private static final SaleStatus UPDATED_STATUS = SaleStatus.CONFIRMED;

    private static final BigDecimal DEFAULT_GROSS_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_GROSS_AMOUNT = new BigDecimal(1);
    private static final BigDecimal SMALLER_GROSS_AMOUNT = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_DISCOUNT_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_DISCOUNT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal SMALLER_DISCOUNT_AMOUNT = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_NET_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_NET_AMOUNT = new BigDecimal(1);
    private static final BigDecimal SMALLER_NET_AMOUNT = new BigDecimal(0 - 1);

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/sales";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaleMockMvc;

    private Sale sale;

    private Sale insertedSale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sale createEntity(EntityManager em) {
        Sale sale = new Sale();
        sale.setSaleDate(DEFAULT_SALE_DATE);
        sale.setSaleNumber(DEFAULT_SALE_NUMBER);
        sale.setStatus(DEFAULT_STATUS);
        sale.setGrossAmount(DEFAULT_GROSS_AMOUNT);
        sale.setDiscountAmount(DEFAULT_DISCOUNT_AMOUNT);
        sale.setNetAmount(DEFAULT_NET_AMOUNT);
        sale.setNotes(DEFAULT_NOTES);
        sale.setDeletedAt(DEFAULT_DELETED_AT);
        return sale;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sale createUpdatedEntity(EntityManager em) {
        Sale updatedSale = new Sale();
        updatedSale.setSaleDate(UPDATED_SALE_DATE);
        updatedSale.setSaleNumber(UPDATED_SALE_NUMBER);
        updatedSale.setStatus(UPDATED_STATUS);
        updatedSale.setGrossAmount(UPDATED_GROSS_AMOUNT);
        updatedSale.setDiscountAmount(UPDATED_DISCOUNT_AMOUNT);
        updatedSale.setNetAmount(UPDATED_NET_AMOUNT);
        updatedSale.setNotes(UPDATED_NOTES);
        updatedSale.setDeletedAt(UPDATED_DELETED_AT);
        return updatedSale;
    }

    @BeforeEach
    void initTest() {
        sale = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedSale != null) {
            saleRepository.delete(insertedSale);
            insertedSale = null;
        }
    }

    @Test
    @Transactional
    void createSale() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sale
        var returnedSale = om.readValue(
            restSaleMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sale)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sale.class
        );

        // Validate the Sale in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSaleUpdatableFieldsEquals(returnedSale, getPersistedSale(returnedSale));

        insertedSale = returnedSale;
    }

    @Test
    @Transactional
    void createSaleWithExistingId() throws Exception {
        // Create the Sale with an existing ID
        sale.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sale)))
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSaleDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sale.setSaleDate(null);

        // Create the Sale, which fails.

        restSaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sale)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSaleNumberIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sale.setSaleNumber(null);

        // Create the Sale, which fails.

        restSaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sale)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sale.setStatus(null);

        // Create the Sale, which fails.

        restSaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sale)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGrossAmountIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sale.setGrossAmount(null);

        // Create the Sale, which fails.

        restSaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sale)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNetAmountIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sale.setNetAmount(null);

        // Create the Sale, which fails.

        restSaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sale)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSales() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList
        restSaleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sale.getId().intValue())))
            .andExpect(jsonPath("$.[*].saleDate").value(hasItem(DEFAULT_SALE_DATE.toString())))
            .andExpect(jsonPath("$.[*].saleNumber").value(hasItem(DEFAULT_SALE_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].grossAmount").value(hasItem(sameNumber(DEFAULT_GROSS_AMOUNT))))
            .andExpect(jsonPath("$.[*].discountAmount").value(hasItem(sameNumber(DEFAULT_DISCOUNT_AMOUNT))))
            .andExpect(jsonPath("$.[*].netAmount").value(hasItem(sameNumber(DEFAULT_NET_AMOUNT))))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }

    @Test
    @Transactional
    void getSale() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get the sale
        restSaleMockMvc
            .perform(get(ENTITY_API_URL_ID, sale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sale.getId().intValue()))
            .andExpect(jsonPath("$.saleDate").value(DEFAULT_SALE_DATE.toString()))
            .andExpect(jsonPath("$.saleNumber").value(DEFAULT_SALE_NUMBER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.grossAmount").value(sameNumber(DEFAULT_GROSS_AMOUNT)))
            .andExpect(jsonPath("$.discountAmount").value(sameNumber(DEFAULT_DISCOUNT_AMOUNT)))
            .andExpect(jsonPath("$.netAmount").value(sameNumber(DEFAULT_NET_AMOUNT)))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    @Transactional
    void getSalesByIdFiltering() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        Long id = sale.getId();

        defaultSaleFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSaleFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSaleFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSalesBySaleDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where saleDate equals to
        defaultSaleFiltering("saleDate.equals=" + DEFAULT_SALE_DATE, "saleDate.equals=" + UPDATED_SALE_DATE);
    }

    @Test
    @Transactional
    void getAllSalesBySaleDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where saleDate in
        defaultSaleFiltering("saleDate.in=" + DEFAULT_SALE_DATE + "," + UPDATED_SALE_DATE, "saleDate.in=" + UPDATED_SALE_DATE);
    }

    @Test
    @Transactional
    void getAllSalesBySaleDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where saleDate is not null
        defaultSaleFiltering("saleDate.specified=true", "saleDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesBySaleNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where saleNumber equals to
        defaultSaleFiltering("saleNumber.equals=" + DEFAULT_SALE_NUMBER, "saleNumber.equals=" + UPDATED_SALE_NUMBER);
    }

    @Test
    @Transactional
    void getAllSalesBySaleNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where saleNumber in
        defaultSaleFiltering("saleNumber.in=" + DEFAULT_SALE_NUMBER + "," + UPDATED_SALE_NUMBER, "saleNumber.in=" + UPDATED_SALE_NUMBER);
    }

    @Test
    @Transactional
    void getAllSalesBySaleNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where saleNumber is not null
        defaultSaleFiltering("saleNumber.specified=true", "saleNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesBySaleNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where saleNumber contains
        defaultSaleFiltering("saleNumber.contains=" + DEFAULT_SALE_NUMBER, "saleNumber.contains=" + UPDATED_SALE_NUMBER);
    }

    @Test
    @Transactional
    void getAllSalesBySaleNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where saleNumber does not contain
        defaultSaleFiltering("saleNumber.doesNotContain=" + UPDATED_SALE_NUMBER, "saleNumber.doesNotContain=" + DEFAULT_SALE_NUMBER);
    }

    @Test
    @Transactional
    void getAllSalesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where status equals to
        defaultSaleFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSalesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where status in
        defaultSaleFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSalesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where status is not null
        defaultSaleFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesByGrossAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where grossAmount equals to
        defaultSaleFiltering("grossAmount.equals=" + DEFAULT_GROSS_AMOUNT, "grossAmount.equals=" + UPDATED_GROSS_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesByGrossAmountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where grossAmount in
        defaultSaleFiltering(
            "grossAmount.in=" + DEFAULT_GROSS_AMOUNT + "," + UPDATED_GROSS_AMOUNT,
            "grossAmount.in=" + UPDATED_GROSS_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesByGrossAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where grossAmount is not null
        defaultSaleFiltering("grossAmount.specified=true", "grossAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesByGrossAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where grossAmount is greater than or equal to
        defaultSaleFiltering(
            "grossAmount.greaterThanOrEqual=" + DEFAULT_GROSS_AMOUNT,
            "grossAmount.greaterThanOrEqual=" + UPDATED_GROSS_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesByGrossAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where grossAmount is less than or equal to
        defaultSaleFiltering("grossAmount.lessThanOrEqual=" + DEFAULT_GROSS_AMOUNT, "grossAmount.lessThanOrEqual=" + SMALLER_GROSS_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesByGrossAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where grossAmount is less than
        defaultSaleFiltering("grossAmount.lessThan=" + UPDATED_GROSS_AMOUNT, "grossAmount.lessThan=" + DEFAULT_GROSS_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesByGrossAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where grossAmount is greater than
        defaultSaleFiltering("grossAmount.greaterThan=" + SMALLER_GROSS_AMOUNT, "grossAmount.greaterThan=" + DEFAULT_GROSS_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesByDiscountAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where discountAmount equals to
        defaultSaleFiltering("discountAmount.equals=" + DEFAULT_DISCOUNT_AMOUNT, "discountAmount.equals=" + UPDATED_DISCOUNT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesByDiscountAmountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where discountAmount in
        defaultSaleFiltering(
            "discountAmount.in=" + DEFAULT_DISCOUNT_AMOUNT + "," + UPDATED_DISCOUNT_AMOUNT,
            "discountAmount.in=" + UPDATED_DISCOUNT_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesByDiscountAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where discountAmount is not null
        defaultSaleFiltering("discountAmount.specified=true", "discountAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesByDiscountAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where discountAmount is greater than or equal to
        defaultSaleFiltering(
            "discountAmount.greaterThanOrEqual=" + DEFAULT_DISCOUNT_AMOUNT,
            "discountAmount.greaterThanOrEqual=" + UPDATED_DISCOUNT_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesByDiscountAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where discountAmount is less than or equal to
        defaultSaleFiltering(
            "discountAmount.lessThanOrEqual=" + DEFAULT_DISCOUNT_AMOUNT,
            "discountAmount.lessThanOrEqual=" + SMALLER_DISCOUNT_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesByDiscountAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where discountAmount is less than
        defaultSaleFiltering("discountAmount.lessThan=" + UPDATED_DISCOUNT_AMOUNT, "discountAmount.lessThan=" + DEFAULT_DISCOUNT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesByDiscountAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where discountAmount is greater than
        defaultSaleFiltering(
            "discountAmount.greaterThan=" + SMALLER_DISCOUNT_AMOUNT,
            "discountAmount.greaterThan=" + DEFAULT_DISCOUNT_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesByNetAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where netAmount equals to
        defaultSaleFiltering("netAmount.equals=" + DEFAULT_NET_AMOUNT, "netAmount.equals=" + UPDATED_NET_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesByNetAmountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where netAmount in
        defaultSaleFiltering("netAmount.in=" + DEFAULT_NET_AMOUNT + "," + UPDATED_NET_AMOUNT, "netAmount.in=" + UPDATED_NET_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesByNetAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where netAmount is not null
        defaultSaleFiltering("netAmount.specified=true", "netAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesByNetAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where netAmount is greater than or equal to
        defaultSaleFiltering("netAmount.greaterThanOrEqual=" + DEFAULT_NET_AMOUNT, "netAmount.greaterThanOrEqual=" + UPDATED_NET_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesByNetAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where netAmount is less than or equal to
        defaultSaleFiltering("netAmount.lessThanOrEqual=" + DEFAULT_NET_AMOUNT, "netAmount.lessThanOrEqual=" + SMALLER_NET_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesByNetAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where netAmount is less than
        defaultSaleFiltering("netAmount.lessThan=" + UPDATED_NET_AMOUNT, "netAmount.lessThan=" + DEFAULT_NET_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesByNetAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where netAmount is greater than
        defaultSaleFiltering("netAmount.greaterThan=" + SMALLER_NET_AMOUNT, "netAmount.greaterThan=" + DEFAULT_NET_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where notes equals to
        defaultSaleFiltering("notes.equals=" + DEFAULT_NOTES, "notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllSalesByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where notes in
        defaultSaleFiltering("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES, "notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllSalesByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where notes is not null
        defaultSaleFiltering("notes.specified=true", "notes.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesByNotesContainsSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where notes contains
        defaultSaleFiltering("notes.contains=" + DEFAULT_NOTES, "notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllSalesByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where notes does not contain
        defaultSaleFiltering("notes.doesNotContain=" + UPDATED_NOTES, "notes.doesNotContain=" + DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void getAllSalesByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where deletedAt equals to
        defaultSaleFiltering("deletedAt.equals=" + DEFAULT_DELETED_AT, "deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllSalesByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where deletedAt in
        defaultSaleFiltering("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT, "deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllSalesByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        // Get all the saleList where deletedAt is not null
        defaultSaleFiltering("deletedAt.specified=true", "deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesByItemsIsEqualToSomething() throws Exception {
        SaleItem items;
        if (TestUtil.findAll(em, SaleItem.class).isEmpty()) {
            saleRepository.saveAndFlush(sale);
            items = SaleItemResourceIT.createEntity(em);
        } else {
            items = TestUtil.findAll(em, SaleItem.class).get(0);
        }
        em.persist(items);
        em.flush();
        sale.setItems(items);
        saleRepository.saveAndFlush(sale);
        Long itemsId = items.getId();
        // Get all the saleList where items equals to itemsId
        defaultSaleShouldBeFound("itemsId.equals=" + itemsId);

        // Get all the saleList where items equals to (itemsId + 1)
        defaultSaleShouldNotBeFound("itemsId.equals=" + (itemsId + 1));
    }

    private void defaultSaleFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSaleShouldBeFound(shouldBeFound);
        defaultSaleShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSaleShouldBeFound(String filter) throws Exception {
        restSaleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sale.getId().intValue())))
            .andExpect(jsonPath("$.[*].saleDate").value(hasItem(DEFAULT_SALE_DATE.toString())))
            .andExpect(jsonPath("$.[*].saleNumber").value(hasItem(DEFAULT_SALE_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].grossAmount").value(hasItem(sameNumber(DEFAULT_GROSS_AMOUNT))))
            .andExpect(jsonPath("$.[*].discountAmount").value(hasItem(sameNumber(DEFAULT_DISCOUNT_AMOUNT))))
            .andExpect(jsonPath("$.[*].netAmount").value(hasItem(sameNumber(DEFAULT_NET_AMOUNT))))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));

        // Check, that the count call also returns 1
        restSaleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSaleShouldNotBeFound(String filter) throws Exception {
        restSaleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSaleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSale() throws Exception {
        // Get the sale
        restSaleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSale() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sale
        Sale updatedSale = saleRepository.findById(sale.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSale are not directly saved in db
        em.detach(updatedSale);
        updatedSale.setSaleDate(UPDATED_SALE_DATE);
        updatedSale.setSaleNumber(UPDATED_SALE_NUMBER);
        updatedSale.setStatus(UPDATED_STATUS);
        updatedSale.setGrossAmount(UPDATED_GROSS_AMOUNT);
        updatedSale.setDiscountAmount(UPDATED_DISCOUNT_AMOUNT);
        updatedSale.setNetAmount(UPDATED_NET_AMOUNT);
        updatedSale.setNotes(UPDATED_NOTES);
        updatedSale.setDeletedAt(UPDATED_DELETED_AT);

        restSaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSale))
            )
            .andExpect(status().isOk());

        // Validate the Sale in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSaleToMatchAllProperties(updatedSale);
    }

    @Test
    @Transactional
    void putNonExistingSale() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sale.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(put(ENTITY_API_URL_ID, sale.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sale)))
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSale() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sale.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sale))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSale() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sale.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sale)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sale in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSaleWithPatch() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sale using partial update
        Sale partialUpdatedSale = new Sale();
        partialUpdatedSale.setId(sale.getId());

        partialUpdatedSale.setSaleDate(UPDATED_SALE_DATE);
        partialUpdatedSale.setSaleNumber(UPDATED_SALE_NUMBER);
        partialUpdatedSale.setNotes(UPDATED_NOTES);

        restSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSale.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSale))
            )
            .andExpect(status().isOk());

        // Validate the Sale in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaleUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSale, sale), getPersistedSale(sale));
    }

    @Test
    @Transactional
    void fullUpdateSaleWithPatch() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sale using partial update
        Sale partialUpdatedSale = new Sale();
        partialUpdatedSale.setId(sale.getId());

        partialUpdatedSale.setSaleDate(UPDATED_SALE_DATE);
        partialUpdatedSale.setSaleNumber(UPDATED_SALE_NUMBER);
        partialUpdatedSale.setStatus(UPDATED_STATUS);
        partialUpdatedSale.setGrossAmount(UPDATED_GROSS_AMOUNT);
        partialUpdatedSale.setDiscountAmount(UPDATED_DISCOUNT_AMOUNT);
        partialUpdatedSale.setNetAmount(UPDATED_NET_AMOUNT);
        partialUpdatedSale.setNotes(UPDATED_NOTES);
        partialUpdatedSale.setDeletedAt(UPDATED_DELETED_AT);

        restSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSale.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSale))
            )
            .andExpect(status().isOk());

        // Validate the Sale in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaleUpdatableFieldsEquals(partialUpdatedSale, getPersistedSale(partialUpdatedSale));
    }

    @Test
    @Transactional
    void patchNonExistingSale() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sale.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(patch(ENTITY_API_URL_ID, sale.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sale)))
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSale() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sale.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sale))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSale() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sale.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sale)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sale in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSale() throws Exception {
        // Initialize the database
        insertedSale = saleRepository.saveAndFlush(sale);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sale
        restSaleMockMvc
            .perform(delete(ENTITY_API_URL_ID, sale.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return saleRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Sale getPersistedSale(Sale sale) {
        return saleRepository.findById(sale.getId()).orElseThrow();
    }

    protected void assertPersistedSaleToMatchAllProperties(Sale expectedSale) {
        assertSaleAllPropertiesEquals(expectedSale, getPersistedSale(expectedSale));
    }

    protected void assertPersistedSaleToMatchUpdatableProperties(Sale expectedSale) {
        assertSaleAllUpdatablePropertiesEquals(expectedSale, getPersistedSale(expectedSale));
    }
}
