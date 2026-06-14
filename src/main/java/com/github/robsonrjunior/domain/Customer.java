package com.github.robsonrjunior.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.robsonrjunior.domain.enumeration.PartyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class Customer extends SoftDeletableEntity {

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
    @Column(name = "legal_name", length = 120, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private String legalName;

    @Size(max = 120)
    @Column(name = "trade_name", length = 120)
    @JsonView({ Multiple.class, Single.class })
    private String tradeName;

    @NotNull
    @Size(min = 11, max = 20)
    @Column(name = "tax_id", length = 20, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private String taxId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "party_type", nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private PartyType partyType;

    @Size(max = 120)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", length = 120)
    @JsonView({ Multiple.class, Single.class })
    private String email;

    @Size(max = 30)
    @Column(name = "phone", length = 30)
    @JsonView({ Multiple.class, Single.class })
    private String phone;

    @NotNull
    @Column(name = "active", nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private Boolean active;

    @JsonIgnoreProperties(value = { "customer", "supplier", "tenants", "cities" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    @JsonView({ Multiple.class, Single.class })
    private Person person;

    @JsonIgnoreProperties(value = { "customer", "supplier", "tenants", "cities" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    @JsonView({ Multiple.class, Single.class })
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "items", "tenants", "warehouses", "customers" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Sale sales;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customers")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customers")
    @JsonIgnoreProperties(value = { "suppliers", "customers", "people", "companies", "warehouses", "state" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Set<City> cities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public void setTenants(Set<Tenant> tenants) {
        if (this.tenants != null) {
            this.tenants.forEach(i -> i.setCustomers(null));
        }
        if (tenants != null) {
            tenants.forEach(i -> i.setCustomers(this));
        }
        this.tenants = tenants;
    }

    public Customer addTenant(Tenant tenant) {
        this.tenants.add(tenant);
        tenant.setCustomers(this);
        return this;
    }

    public Customer removeTenant(Tenant tenant) {
        this.tenants.remove(tenant);
        tenant.setCustomers(null);
        return this;
    }

    public void setCities(Set<City> cities) {
        if (this.cities != null) {
            this.cities.forEach(i -> i.setCustomers(null));
        }
        if (cities != null) {
            cities.forEach(i -> i.setCustomers(this));
        }
        this.cities = cities;
    }

    public Customer addCity(City city) {
        this.cities.add(city);
        city.setCustomers(this);
        return this;
    }

    public Customer removeCity(City city) {
        this.cities.remove(city);
        city.setCustomers(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return getId() != null && getId().equals(((Customer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", legalName='" + getLegalName() + "'" +
            ", tradeName='" + getTradeName() + "'" +
            ", taxId='" + getTaxId() + "'" +
            ", partyType='" + getPartyType() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
