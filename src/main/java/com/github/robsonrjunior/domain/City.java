package com.github.robsonrjunior.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * A City.
 */
@Entity
@Table(name = "city")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class City implements Serializable {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "person", "company", "rawMaterials", "tenants", "cities" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Supplier suppliers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "person", "company", "sales", "tenants", "cities" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Customer customers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "customer", "supplier", "tenants", "cities" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Person people;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "customer", "supplier", "tenants", "cities" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Company companies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "stockMovements", "sales", "tenants", "cities" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Warehouse warehouses;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "citieses", "country" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private State state;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public City id(Long id) {
        this.setId(id);
        return this;
    }

    public City name(String name) {
        this.setName(name);
        return this;
    }

    public City suppliers(Supplier supplier) {
        this.setSuppliers(supplier);
        return this;
    }

    public City customers(Customer customer) {
        this.setCustomers(customer);
        return this;
    }

    public City people(Person person) {
        this.setPeople(person);
        return this;
    }

    public City companies(Company company) {
        this.setCompanies(company);
        return this;
    }

    public City warehouses(Warehouse warehouse) {
        this.setWarehouses(warehouse);
        return this;
    }

    public City state(State state) {
        this.setState(state);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof City)) {
            return false;
        }
        return getId() != null && getId().equals(((City) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "City{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
