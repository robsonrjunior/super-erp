package com.github.robsonrjunior.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * A Warehouse.
 */
@Entity
@Table(name = "warehouse")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class Warehouse extends SoftDeletableEntity {

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
    @Size(min = 2, max = 120)
    @Column(name = "name", length = 120, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private String name;

    @NotNull
    @Size(min = 2, max = 30)
    @Column(name = "code", length = 30, nullable = false, unique = true)
    @JsonView({ Multiple.class, Single.class })
    private String code;

    @NotNull
    @Column(name = "active", nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tenants", "warehouses", "products", "rawMaterials" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private StockMovement stockMovements;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "items", "tenants", "warehouses", "customers" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Sale sales;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warehouses")
    @JsonIgnoreProperties(
        value = {
            "customers",
            "suppliers",
            "people",
            "companies",
            "products",
            "rawMaterials",
            "warehouses",
            "sales",
            "saleItems",
            "stockMovements",
        },
        allowSetters = true
    )
    @JsonView({ Multiple.class, Single.class })
    private Set<Tenant> tenants = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warehouses")
    @JsonIgnoreProperties(value = { "suppliers", "customers", "people", "companies", "warehouses", "state" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Set<City> cities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public void setTenants(Set<Tenant> tenants) {
        if (this.tenants != null) {
            this.tenants.forEach(i -> i.setWarehouses(null));
        }
        if (tenants != null) {
            tenants.forEach(i -> i.setWarehouses(this));
        }
        this.tenants = tenants;
    }

    public Warehouse addTenant(Tenant tenant) {
        this.tenants.add(tenant);
        tenant.setWarehouses(this);
        return this;
    }

    public Warehouse removeTenant(Tenant tenant) {
        this.tenants.remove(tenant);
        tenant.setWarehouses(null);
        return this;
    }

    public void setCities(Set<City> cities) {
        if (this.cities != null) {
            this.cities.forEach(i -> i.setWarehouses(null));
        }
        if (cities != null) {
            cities.forEach(i -> i.setWarehouses(this));
        }
        this.cities = cities;
    }

    public Warehouse addCity(City city) {
        this.cities.add(city);
        city.setWarehouses(this);
        return this;
    }

    public Warehouse removeCity(City city) {
        this.cities.remove(city);
        city.setWarehouses(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Warehouse)) {
            return false;
        }
        return getId() != null && getId().equals(((Warehouse) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Warehouse{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
