package com.github.robsonrjunior.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.robsonrjunior.domain.enumeration.UnitOfMeasure;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class Product extends SoftDeletableEntity {

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

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "sale_price", precision = 21, scale = 2, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private BigDecimal salePrice;

    @DecimalMin(value = "0")
    @Column(name = "cost_price", precision = 21, scale = 2)
    @JsonView({ Multiple.class, Single.class })
    private BigDecimal costPrice;

    @DecimalMin(value = "0")
    @Column(name = "min_stock", precision = 21, scale = 2)
    @JsonView({ Multiple.class, Single.class })
    private BigDecimal minStock;

    @NotNull
    @Column(name = "active", nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tenants", "sales", "products" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private SaleItem saleItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tenants", "warehouses", "products", "rawMaterials" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private StockMovement stockMovements;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "products")
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

    public void setTenants(Set<Tenant> tenants) {
        if (this.tenants != null) {
            this.tenants.forEach(i -> i.setProducts(null));
        }
        if (tenants != null) {
            tenants.forEach(i -> i.setProducts(this));
        }
        this.tenants = tenants;
    }

    public Product addTenant(Tenant tenant) {
        this.tenants.add(tenant);
        tenant.setProducts(this);
        return this;
    }

    public Product removeTenant(Tenant tenant) {
        this.tenants.remove(tenant);
        tenant.setProducts(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return getId() != null && getId().equals(((Product) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Product{" +
            "id=" +
            getId() +
            ", name='" +
            getName() +
            "'" +
            ", sku='" +
            getSku() +
            "'" +
            ", unitOfMeasure='" +
            getUnitOfMeasure() +
            "'" +
            ", unitDecimalPlaces=" +
            getUnitDecimalPlaces() +
            ", salePrice=" +
            getSalePrice() +
            ", costPrice=" +
            getCostPrice() +
            ", minStock=" +
            getMinStock() +
            ", active='" +
            getActive() +
            "'" +
            "}"
        );
    }
}
