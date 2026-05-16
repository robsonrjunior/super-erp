package com.github.robsonrjunior.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

/**
 * A Tenant.
 */
@Entity
@Table(name = "tenant")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class Tenant implements Serializable {

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
    @Size(min = 2, max = 30)
    @Column(name = "code", length = 30, nullable = false, unique = true)
    @JsonView({ Multiple.class, Single.class })
    private String code;

    @NotNull
    @Column(name = "active", nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private Boolean active;

    @Column(name = "deleted_at")
    @JsonView({ Multiple.class, Single.class })
    private Instant deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "person", "company", "sales", "tenants", "cities" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Customer customers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "person", "company", "rawMaterials", "tenants", "cities" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Supplier suppliers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "customer", "supplier", "tenants", "cities" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Person people;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "customer", "supplier", "tenants", "cities" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Company companies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "saleItems", "stockMovements", "tenants" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Product products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "stockMovements", "tenants", "primarySuppliers" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private RawMaterial rawMaterials;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "stockMovements", "sales", "tenants", "cities" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Warehouse warehouses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "items", "tenants", "warehouses", "customers" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Sale sales;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tenants", "sales", "products" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private SaleItem saleItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tenants", "warehouses", "products", "rawMaterials" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private StockMovement stockMovements;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Tenant id(Long id) {
        this.setId(id);
        return this;
    }

    public Tenant name(String name) {
        this.setName(name);
        return this;
    }

    public Tenant code(String code) {
        this.setCode(code);
        return this;
    }

    public Tenant active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public Tenant deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public Tenant customers(Customer customer) {
        this.setCustomers(customer);
        return this;
    }

    public Tenant suppliers(Supplier supplier) {
        this.setSuppliers(supplier);
        return this;
    }

    public Tenant people(Person person) {
        this.setPeople(person);
        return this;
    }

    public Tenant companies(Company company) {
        this.setCompanies(company);
        return this;
    }

    public Tenant products(Product product) {
        this.setProducts(product);
        return this;
    }

    public Tenant rawMaterials(RawMaterial rawMaterial) {
        this.setRawMaterials(rawMaterial);
        return this;
    }

    public Tenant warehouses(Warehouse warehouse) {
        this.setWarehouses(warehouse);
        return this;
    }

    public Tenant sales(Sale sale) {
        this.setSales(sale);
        return this;
    }

    public Tenant saleItems(SaleItem saleItem) {
        this.setSaleItems(saleItem);
        return this;
    }

    public Tenant stockMovements(StockMovement stockMovement) {
        this.setStockMovements(stockMovement);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tenant)) {
            return false;
        }
        return getId() != null && getId().equals(((Tenant) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tenant{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", active='" + getActive() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
