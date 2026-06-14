package com.github.robsonrjunior.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class Country implements Serializable {

    public static interface Multiple {}

    public static interface Single {}

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    @JsonView({ Multiple.class, Single.class })
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private String name;

    @NotNull
    @Size(min = 2, max = 3)
    @Column(name = "iso_code", length = 3, nullable = false, unique = true)
    @JsonView({ Multiple.class, Single.class })
    private String isoCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    @JsonIgnoreProperties(value = { "citieses", "country" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Set<State> stateses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public void setStateses(Set<State> states) {
        if (this.stateses != null) {
            this.stateses.forEach(i -> i.setCountry(null));
        }
        if (states != null) {
            states.forEach(i -> i.setCountry(this));
        }
        this.stateses = states;
    }

    public Country addStates(State state) {
        this.stateses.add(state);
        state.setCountry(this);
        return this;
    }

    public Country removeStates(State state) {
        this.stateses.remove(state);
        state.setCountry(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return getId() != null && getId().equals(((Country) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isoCode='" + getIsoCode() + "'" +
            "}";
    }
}
