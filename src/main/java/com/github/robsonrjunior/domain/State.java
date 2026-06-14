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
 * A State.
 */
@Entity
@Table(name = "state")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class State implements Serializable {

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
    @Size(min = 2, max = 10)
    @Column(name = "code", length = 10, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private String code;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "state")
    @JsonIgnoreProperties(value = { "suppliers", "customers", "people", "companies", "warehouses", "state" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Set<City> citieses = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "stateses" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Country country;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public void setCitieses(Set<City> cities) {
        if (this.citieses != null) {
            this.citieses.forEach(i -> i.setState(null));
        }
        if (cities != null) {
            cities.forEach(i -> i.setState(this));
        }
        this.citieses = cities;
    }

    public State addCities(City city) {
        this.citieses.add(city);
        city.setState(this);
        return this;
    }

    public State removeCities(City city) {
        this.citieses.remove(city);
        city.setState(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof State)) {
            return false;
        }
        return getId() != null && getId().equals(((State) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "State{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
