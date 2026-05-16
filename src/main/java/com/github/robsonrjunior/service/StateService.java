package com.github.robsonrjunior.service;

import com.github.robsonrjunior.domain.State;
import com.github.robsonrjunior.repository.StateRepository;
import java.util.Optional;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.robsonrjunior.domain.State}.
 */
@Service
@Transactional
public class StateService {

    private static final Logger LOG = LoggerFactory.getLogger(StateService.class);

    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    /**
     * Save a state.
     *
     * @param state the entity to save.
     * @return the persisted entity.
     */
    public State save(State state) {
        LOG.debug("Request to save State : {}", state);
        return stateRepository.save(state);
    }

    /**
     * Update a state.
     *
     * @param state the entity to save.
     * @return the persisted entity.
     */
    public State update(State state) {
        LOG.debug("Request to update State : {}", state);
        return stateRepository.save(state);
    }

    /**
     * Partially update a state.
     *
     * @param state the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<State> partialUpdate(State state) {
        LOG.debug("Request to partially update State : {}", state);

        return stateRepository
            .findById(state.getId())
            .map(existingState -> {
                updateIfPresent(existingState::setName, state.getName());
                updateIfPresent(existingState::setCode, state.getCode());

                return existingState;
            })
            .map(stateRepository::save);
    }

    /**
     * Get one state by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<State> findOne(Long id) {
        LOG.debug("Request to get State : {}", id);
        return stateRepository.findById(id);
    }

    /**
     * Delete the state by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete State : {}", id);
        stateRepository.deleteById(id);
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
