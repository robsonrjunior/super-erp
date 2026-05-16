package com.github.robsonrjunior.service;

import com.github.robsonrjunior.domain.Person;
import com.github.robsonrjunior.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.robsonrjunior.domain.Person}.
 */
@Service
@Transactional
public class PersonService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Save a person.
     *
     * @param person the entity to save.
     * @return the persisted entity.
     */
    public Person save(Person person) {
        LOG.debug("Request to save Person : {}", person);
        return personRepository.save(person);
    }

    /**
     * Update a person.
     *
     * @param person the entity to save.
     * @return the persisted entity.
     */
    public Person update(Person person) {
        LOG.debug("Request to update Person : {}", person);
        return personRepository.save(person);
    }

    /**
     * Partially update a person.
     *
     * @param person the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Person> partialUpdate(Person person) {
        LOG.debug("Request to partially update Person : {}", person);

        return personRepository
            .findById(person.getId())
            .map(existingPerson -> {
                updateIfPresent(existingPerson::setFullName, person.getFullName());
                updateIfPresent(existingPerson::setCpf, person.getCpf());
                updateIfPresent(existingPerson::setBirthDate, person.getBirthDate());
                updateIfPresent(existingPerson::setEmail, person.getEmail());
                updateIfPresent(existingPerson::setPhone, person.getPhone());
                updateIfPresent(existingPerson::setActive, person.getActive());
                updateIfPresent(existingPerson::setDeletedAt, person.getDeletedAt());

                return existingPerson;
            })
            .map(personRepository::save);
    }

    /**
     *  Get all the people where Customer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Person> findAllWhereCustomerIsNull() {
        LOG.debug("Request to get all people where Customer is null");
        return StreamSupport.stream(personRepository.findAll().spliterator(), false)
            .filter(person -> person.getCustomer() == null)
            .toList();
    }

    /**
     *  Get all the people where Supplier is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Person> findAllWhereSupplierIsNull() {
        LOG.debug("Request to get all people where Supplier is null");
        return StreamSupport.stream(personRepository.findAll().spliterator(), false)
            .filter(person -> person.getSupplier() == null)
            .toList();
    }

    /**
     * Get one person by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Person> findOne(Long id) {
        LOG.debug("Request to get Person : {}", id);
        return personRepository.findById(id);
    }

    /**
     * Delete the person by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Person : {}", id);
        personRepository.deleteById(id);
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
