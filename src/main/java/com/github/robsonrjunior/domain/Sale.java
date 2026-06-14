package com.github.robsonrjunior.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.robsonrjunior.domain.enumeration.SaleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * A Sale.
 */
@Entity
@Table(name = "sale")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class Sale extends SoftDeletableEntity {

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
    @Column(name = "sale_date", nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private Instant saleDate;

    @NotNull
    @Size(min = 2, max = 40)
    @Column(name = "sale_number", length = 40, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private String saleNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private SaleStatus status;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "gross_amount", precision = 21, scale = 2, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private BigDecimal grossAmount;

    @DecimalMin(value = "0")
    @Column(name = "discount_amount", precision = 21, scale = 2)
    @JsonView({ Multiple.class, Single.class })
    private BigDecimal discountAmount;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "net_amount", precision = 21, scale = 2, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private BigDecimal netAmount;

    @Size(max = 500)
    @Column(name = "notes", length = 500)
    @JsonView({ Multiple.class, Single.class })
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tenants", "sales", "products" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private SaleItem items;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sales")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sales")
    @JsonIgnoreProperties(value = { "stockMovements", "sales", "tenants", "cities" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Set<Warehouse> warehouses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sales")
    @JsonIgnoreProperties(value = { "person", "company", "sales", "tenants", "cities" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Set<Customer> customers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Sale id(Long id) {
        this.setId(id);
        return this;
    }

    public Sale saleDate(Instant saleDate) {
        this.setSaleDate(saleDate);
        return this;
    }

    public Sale saleNumber(String saleNumber) {
        this.setSaleNumber(saleNumber);
        return this;
    }

    public Sale status(SaleStatus status) {
        this.setStatus(status);
        return this;
    }

    public Sale grossAmount(BigDecimal grossAmount) {
        this.setGrossAmount(grossAmount);
        return this;
    }

    public Sale discountAmount(BigDecimal discountAmount) {
        this.setDiscountAmount(discountAmount);
        return this;
    }

    public Sale netAmount(BigDecimal netAmount) {
        this.setNetAmount(netAmount);
        return this;
    }

    public Sale notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public Sale items(SaleItem saleItem) {
        this.setItems(saleItem);
        return this;
    }

    public void setTenants(Set<Tenant> tenants) {
        if (this.tenants != null) {
            this.tenants.forEach(i -> i.setSales(null));
        }
        if (tenants != null) {
            tenants.forEach(i -> i.setSales(this));
        }
        this.tenants = tenants;
    }

    public Sale tenants(Set<Tenant> tenants) {
        this.setTenants(tenants);
        return this;
    }

    public Sale addTenant(Tenant tenant) {
        this.tenants.add(tenant);
        tenant.setSales(this);
        return this;
    }

    public Sale removeTenant(Tenant tenant) {
        this.tenants.remove(tenant);
        tenant.setSales(null);
        return this;
    }

    public void setWarehouses(Set<Warehouse> warehouses) {
        if (this.warehouses != null) {
            this.warehouses.forEach(i -> i.setSales(null));
        }
        if (warehouses != null) {
            warehouses.forEach(i -> i.setSales(this));
        }
        this.warehouses = warehouses;
    }

    public Sale warehouses(Set<Warehouse> warehouses) {
        this.setWarehouses(warehouses);
        return this;
    }

    public Sale addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
        warehouse.setSales(this);
        return this;
    }

    public Sale removeWarehouse(Warehouse warehouse) {
        this.warehouses.remove(warehouse);
        warehouse.setSales(null);
        return this;
    }

    public void setCustomers(Set<Customer> customers) {
        if (this.customers != null) {
            this.customers.forEach(i -> i.setSales(null));
        }
        if (customers != null) {
            customers.forEach(i -> i.setSales(this));
        }
        this.customers = customers;
    }

    public Sale customers(Set<Customer> customers) {
        this.setCustomers(customers);
        return this;
    }

    public Sale addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.setSales(this);
        return this;
    }

    public Sale removeCustomer(Customer customer) {
        this.customers.remove(customer);
        customer.setSales(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sale)) {
            return false;
        }
        return getId() != null && getId().equals(((Sale) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sale{" +
            "id=" + getId() +
            ", saleDate='" + getSaleDate() + "'" +
            ", saleNumber='" + getSaleNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", grossAmount=" + getGrossAmount() +
            ", discountAmount=" + getDiscountAmount() +
            ", netAmount=" + getNetAmount() +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
