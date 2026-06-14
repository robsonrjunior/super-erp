package com.github.robsonrjunior.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.robsonrjunior.domain.enumeration.UnitOfMeasure;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * A RawMaterial.
 */
@Entity
@Table(name = "raw_material")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class RawMaterial extends SoftDeletableEntity {

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
    @Size(min = 2, max = 40)
    @Column(name = "sku", length = 40, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private String sku;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unit_of_measure", nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private UnitOfMeasure unitOfMeasure;

    @NotNull
    @Min(value = 0)
    @Max(value = 6)
    @Column(name = "unit_decimal_places", nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private Integer unitDecimalPlaces;

    @DecimalMin(value = "0")
    @Column(name = "unit_cost", precision = 21, scale = 2)
    @JsonView({ Multiple.class, Single.class })
    private BigDecimal unitCost;

    @DecimalMin(value = "0")
    @Column(name = "min_stock", precision = 21, scale = 2)
    @JsonView({ Multiple.class, Single.class })
    private BigDecimal minStock;

    @NotNull
    @Column(name = "active", nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tenants", "warehouses", "products", "rawMaterials" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private StockMovement stockMovements;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rawMaterials")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rawMaterials")
    @JsonIgnoreProperties(value = { "person", "company", "rawMaterials", "tenants", "cities" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Set<Supplier> primarySuppliers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public RawMaterial id(Long id) {
        this.setId(id);
        return this;
    }

    public RawMaterial name(String name) {
        this.setName(name);
        return this;
    }

    public RawMaterial sku(String sku) {
        this.setSku(sku);
        return this;
    }

    public RawMaterial unitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.setUnitOfMeasure(unitOfMeasure);
        return this;
    }

    public RawMaterial unitDecimalPlaces(Integer unitDecimalPlaces) {
        this.setUnitDecimalPlaces(unitDecimalPlaces);
        return this;
    }

    public RawMaterial unitCost(BigDecimal unitCost) {
        this.setUnitCost(unitCost);
        return this;
    }

    public RawMaterial minStock(BigDecimal minStock) {
        this.setMinStock(minStock);
        return this;
    }

    public RawMaterial active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public RawMaterial stockMovements(StockMovement stockMovement) {
        this.setStockMovements(stockMovement);
        return this;
    }

    public void setTenants(Set<Tenant> tenants) {
        if (this.tenants != null) {
            this.tenants.forEach(i -> i.setRawMaterials(null));
        }
        if (tenants != null) {
            tenants.forEach(i -> i.setRawMaterials(this));
        }
        this.tenants = tenants;
    }

    public RawMaterial tenants(Set<Tenant> tenants) {
        this.setTenants(tenants);
        return this;
    }

    public RawMaterial addTenant(Tenant tenant) {
        this.tenants.add(tenant);
        tenant.setRawMaterials(this);
        return this;
    }

    public RawMaterial removeTenant(Tenant tenant) {
        this.tenants.remove(tenant);
        tenant.setRawMaterials(null);
        return this;
    }

    public void setPrimarySuppliers(Set<Supplier> suppliers) {
        if (this.primarySuppliers != null) {
            this.primarySuppliers.forEach(i -> i.setRawMaterials(null));
        }
        if (suppliers != null) {
            suppliers.forEach(i -> i.setRawMaterials(this));
        }
        this.primarySuppliers = suppliers;
    }

    public RawMaterial primarySuppliers(Set<Supplier> suppliers) {
        this.setPrimarySuppliers(suppliers);
        return this;
    }

    public RawMaterial addPrimarySupplier(Supplier supplier) {
        this.primarySuppliers.add(supplier);
        supplier.setRawMaterials(this);
        return this;
    }

    public RawMaterial removePrimarySupplier(Supplier supplier) {
        this.primarySuppliers.remove(supplier);
        supplier.setRawMaterials(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RawMaterial)) {
            return false;
        }
        return getId() != null && getId().equals(((RawMaterial) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RawMaterial{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sku='" + getSku() + "'" +
            ", unitOfMeasure='" + getUnitOfMeasure() + "'" +
            ", unitDecimalPlaces=" + getUnitDecimalPlaces() +
            ", unitCost=" + getUnitCost() +
            ", minStock=" + getMinStock() +
            ", active='" + getActive() + "'" +
            "}";
    }
}
