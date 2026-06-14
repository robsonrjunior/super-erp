package com.github.robsonrjunior.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * A SaleItem.
 */
@Entity
@Table(name = "sale_item")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class SaleItem extends SoftDeletableEntity {

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
    @DecimalMin(value = "0.000001")
    @Column(name = "quantity", precision = 21, scale = 2, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private BigDecimal quantity;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "unit_price", precision = 21, scale = 2, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private BigDecimal unitPrice;

    @DecimalMin(value = "0")
    @Column(name = "discount_amount", precision = 21, scale = 2)
    @JsonView({ Multiple.class, Single.class })
    private BigDecimal discountAmount;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "line_total", precision = 21, scale = 2, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private BigDecimal lineTotal;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "saleItems")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "items")
    @JsonIgnoreProperties(value = { "items", "tenants", "warehouses", "customers" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Set<Sale> sales = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "saleItems")
    @JsonIgnoreProperties(value = { "saleItems", "stockMovements", "tenants" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public SaleItem id(Long id) {
        this.setId(id);
        return this;
    }

    public SaleItem quantity(BigDecimal quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public SaleItem unitPrice(BigDecimal unitPrice) {
        this.setUnitPrice(unitPrice);
        return this;
    }

    public SaleItem discountAmount(BigDecimal discountAmount) {
        this.setDiscountAmount(discountAmount);
        return this;
    }

    public SaleItem lineTotal(BigDecimal lineTotal) {
        this.setLineTotal(lineTotal);
        return this;
    }

    public void setTenants(Set<Tenant> tenants) {
        if (this.tenants != null) {
            this.tenants.forEach(i -> i.setSaleItems(null));
        }
        if (tenants != null) {
            tenants.forEach(i -> i.setSaleItems(this));
        }
        this.tenants = tenants;
    }

    public SaleItem tenants(Set<Tenant> tenants) {
        this.setTenants(tenants);
        return this;
    }

    public SaleItem addTenant(Tenant tenant) {
        this.tenants.add(tenant);
        tenant.setSaleItems(this);
        return this;
    }

    public SaleItem removeTenant(Tenant tenant) {
        this.tenants.remove(tenant);
        tenant.setSaleItems(null);
        return this;
    }

    public void setSales(Set<Sale> sales) {
        if (this.sales != null) {
            this.sales.forEach(i -> i.setItems(null));
        }
        if (sales != null) {
            sales.forEach(i -> i.setItems(this));
        }
        this.sales = sales;
    }

    public SaleItem sales(Set<Sale> sales) {
        this.setSales(sales);
        return this;
    }

    public SaleItem addSale(Sale sale) {
        this.sales.add(sale);
        sale.setItems(this);
        return this;
    }

    public SaleItem removeSale(Sale sale) {
        this.sales.remove(sale);
        sale.setItems(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.setSaleItems(null));
        }
        if (products != null) {
            products.forEach(i -> i.setSaleItems(this));
        }
        this.products = products;
    }

    public SaleItem products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public SaleItem addProduct(Product product) {
        this.products.add(product);
        product.setSaleItems(this);
        return this;
    }

    public SaleItem removeProduct(Product product) {
        this.products.remove(product);
        product.setSaleItems(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaleItem)) {
            return false;
        }
        return getId() != null && getId().equals(((SaleItem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaleItem{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", discountAmount=" + getDiscountAmount() +
            ", lineTotal=" + getLineTotal() +
            "}";
    }
}
