package com.github.robsonrjunior.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.robsonrjunior.domain.enumeration.MovementType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * A StockMovement.
 */
@Entity
@Table(name = "stock_movement")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class StockMovement implements Serializable {

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
    @Column(name = "movement_date", nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private Instant movementDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private MovementType movementType;

    @NotNull
    @DecimalMin(value = "0.000001")
    @Column(name = "quantity", precision = 21, scale = 2, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private BigDecimal quantity;

    @DecimalMin(value = "0")
    @Column(name = "unit_cost", precision = 21, scale = 2)
    @JsonView({ Multiple.class, Single.class })
    private BigDecimal unitCost;

    @Size(max = 60)
    @Column(name = "reference_number", length = 60)
    @JsonView({ Multiple.class, Single.class })
    private String referenceNumber;

    @Size(max = 500)
    @Column(name = "notes", length = 500)
    @JsonView({ Multiple.class, Single.class })
    private String notes;

    @Column(name = "deleted_at")
    @JsonView({ Multiple.class, Single.class })
    private Instant deletedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stockMovements")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stockMovements")
    @JsonIgnoreProperties(value = { "stockMovements", "sales", "tenants", "cities" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Set<Warehouse> warehouses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stockMovements")
    @JsonIgnoreProperties(value = { "saleItems", "stockMovements", "tenants" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Set<Product> products = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stockMovements")
    @JsonIgnoreProperties(value = { "stockMovements", "tenants", "primarySuppliers" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Set<RawMaterial> rawMaterials = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public StockMovement id(Long id) {
        this.setId(id);
        return this;
    }

    public StockMovement movementDate(Instant movementDate) {
        this.setMovementDate(movementDate);
        return this;
    }

    public StockMovement movementType(MovementType movementType) {
        this.setMovementType(movementType);
        return this;
    }

    public StockMovement quantity(BigDecimal quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public StockMovement unitCost(BigDecimal unitCost) {
        this.setUnitCost(unitCost);
        return this;
    }

    public StockMovement referenceNumber(String referenceNumber) {
        this.setReferenceNumber(referenceNumber);
        return this;
    }

    public StockMovement notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public StockMovement deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setTenants(Set<Tenant> tenants) {
        if (this.tenants != null) {
            this.tenants.forEach(i -> i.setStockMovements(null));
        }
        if (tenants != null) {
            tenants.forEach(i -> i.setStockMovements(this));
        }
        this.tenants = tenants;
    }

    public StockMovement tenants(Set<Tenant> tenants) {
        this.setTenants(tenants);
        return this;
    }

    public StockMovement addTenant(Tenant tenant) {
        this.tenants.add(tenant);
        tenant.setStockMovements(this);
        return this;
    }

    public StockMovement removeTenant(Tenant tenant) {
        this.tenants.remove(tenant);
        tenant.setStockMovements(null);
        return this;
    }

    public void setWarehouses(Set<Warehouse> warehouses) {
        if (this.warehouses != null) {
            this.warehouses.forEach(i -> i.setStockMovements(null));
        }
        if (warehouses != null) {
            warehouses.forEach(i -> i.setStockMovements(this));
        }
        this.warehouses = warehouses;
    }

    public StockMovement warehouses(Set<Warehouse> warehouses) {
        this.setWarehouses(warehouses);
        return this;
    }

    public StockMovement addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
        warehouse.setStockMovements(this);
        return this;
    }

    public StockMovement removeWarehouse(Warehouse warehouse) {
        this.warehouses.remove(warehouse);
        warehouse.setStockMovements(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.setStockMovements(null));
        }
        if (products != null) {
            products.forEach(i -> i.setStockMovements(this));
        }
        this.products = products;
    }

    public StockMovement products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public StockMovement addProduct(Product product) {
        this.products.add(product);
        product.setStockMovements(this);
        return this;
    }

    public StockMovement removeProduct(Product product) {
        this.products.remove(product);
        product.setStockMovements(null);
        return this;
    }

    public void setRawMaterials(Set<RawMaterial> rawMaterials) {
        if (this.rawMaterials != null) {
            this.rawMaterials.forEach(i -> i.setStockMovements(null));
        }
        if (rawMaterials != null) {
            rawMaterials.forEach(i -> i.setStockMovements(this));
        }
        this.rawMaterials = rawMaterials;
    }

    public StockMovement rawMaterials(Set<RawMaterial> rawMaterials) {
        this.setRawMaterials(rawMaterials);
        return this;
    }

    public StockMovement addRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterials.add(rawMaterial);
        rawMaterial.setStockMovements(this);
        return this;
    }

    public StockMovement removeRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterials.remove(rawMaterial);
        rawMaterial.setStockMovements(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockMovement)) {
            return false;
        }
        return getId() != null && getId().equals(((StockMovement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockMovement{" +
            "id=" + getId() +
            ", movementDate='" + getMovementDate() + "'" +
            ", movementType='" + getMovementType() + "'" +
            ", quantity=" + getQuantity() +
            ", unitCost=" + getUnitCost() +
            ", referenceNumber='" + getReferenceNumber() + "'" +
            ", notes='" + getNotes() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
