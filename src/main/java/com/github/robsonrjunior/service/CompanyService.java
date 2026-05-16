package com.github.robsonrjunior.service;

import com.github.robsonrjunior.domain.Company;
import com.github.robsonrjunior.repository.CompanyRepository;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.robsonrjunior.domain.Company}.
 */
@Service
@Transactional
public class CompanyService {

    private static final Logger LOG = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * Save a company.
     *
     * @param company the entity to save.
     * @return the persisted entity.
     */
    public Company save(Company company) {
        LOG.debug("Request to save Company : {}", company);
        return companyRepository.save(company);
    }

    /**
     * Update a company.
     *
     * @param company the entity to save.
     * @return the persisted entity.
     */
    public Company update(Company company) {
        LOG.debug("Request to update Company : {}", company);
        return companyRepository.save(company);
    }

    /**
     * Partially update a company.
     *
     * @param company the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Company> partialUpdate(Company company) {
        LOG.debug("Request to partially update Company : {}", company);

        return companyRepository
            .findById(company.getId())
            .map(existingCompany -> {
                updateIfPresent(existingCompany::setLegalName, company.getLegalName());
                updateIfPresent(existingCompany::setTradeName, company.getTradeName());
                updateIfPresent(existingCompany::setCnpj, company.getCnpj());
                updateIfPresent(existingCompany::setStateRegistration, company.getStateRegistration());
                updateIfPresent(existingCompany::setEmail, company.getEmail());
                updateIfPresent(existingCompany::setPhone, company.getPhone());
                updateIfPresent(existingCompany::setActive, company.getActive());
                updateIfPresent(existingCompany::setDeletedAt, company.getDeletedAt());

                return existingCompany;
            })
            .map(companyRepository::save);
    }

    /**
     *  Get all the companies where Customer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Company> findAllWhereCustomerIsNull() {
        LOG.debug("Request to get all companies where Customer is null");
        return StreamSupport.stream(companyRepository.findAll().spliterator(), false)
            .filter(company -> company.getCustomer() == null)
            .toList();
    }

    /**
     *  Get all the companies where Supplier is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Company> findAllWhereSupplierIsNull() {
        LOG.debug("Request to get all companies where Supplier is null");
        return StreamSupport.stream(companyRepository.findAll().spliterator(), false)
            .filter(company -> company.getSupplier() == null)
            .toList();
    }

    /**
     * Get one company by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Company> findOne(Long id) {
        LOG.debug("Request to get Company : {}", id);
        return companyRepository.findById(id);
    }

    /**
     * Delete the company by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Company : {}", id);
        companyRepository.deleteById(id);
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
