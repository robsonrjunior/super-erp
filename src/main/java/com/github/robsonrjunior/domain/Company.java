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
 * A Company.
 */
@Entity
@Table(name = "company")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class Company extends SoftDeletableEntity {

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
    @Size(min = 14, max = 18)
    @Column(name = "cnpj", length = 18, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private String cnpj;

    @Size(max = 30)
    @Column(name = "state_registration", length = 30)
    @JsonView({ Multiple.class, Single.class })
    private String stateRegistration;

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

    @JsonIgnoreProperties(value = { "person", "company", "sales", "tenants", "cities" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "company")
    @JsonView({ Multiple.class, Single.class })
    private Customer customer;

    @JsonIgnoreProperties(value = { "person", "company", "rawMaterials", "tenants", "cities" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "company")
    @JsonView({ Multiple.class, Single.class })
    private Supplier supplier;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companies")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companies")
    @JsonIgnoreProperties(value = { "suppliers", "customers", "people", "companies", "warehouses", "state" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Set<City> cities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Company id(Long id) {
        this.setId(id);
        return this;
    }

    public Company legalName(String legalName) {
        this.setLegalName(legalName);
        return this;
    }

    public Company tradeName(String tradeName) {
        this.setTradeName(tradeName);
        return this;
    }

    public Company cnpj(String cnpj) {
        this.setCnpj(cnpj);
        return this;
    }

    public Company stateRegistration(String stateRegistration) {
        this.setStateRegistration(stateRegistration);
        return this;
    }

    public Company email(String email) {
        this.setEmail(email);
        return this;
    }

    public Company phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public Company active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setCustomer(Customer customer) {
        if (this.customer != null) {
            this.customer.setCompany(null);
        }
        if (customer != null) {
            customer.setCompany(this);
        }
        this.customer = customer;
    }

    public Company customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public void setSupplier(Supplier supplier) {
        if (this.supplier != null) {
            this.supplier.setCompany(null);
        }
        if (supplier != null) {
            supplier.setCompany(this);
        }
        this.supplier = supplier;
    }

    public Company supplier(Supplier supplier) {
        this.setSupplier(supplier);
        return this;
    }

    public void setTenants(Set<Tenant> tenants) {
        if (this.tenants != null) {
            this.tenants.forEach(i -> i.setCompanies(null));
        }
        if (tenants != null) {
            tenants.forEach(i -> i.setCompanies(this));
        }
        this.tenants = tenants;
    }

    public Company tenants(Set<Tenant> tenants) {
        this.setTenants(tenants);
        return this;
    }

    public Company addTenant(Tenant tenant) {
        this.tenants.add(tenant);
        tenant.setCompanies(this);
        return this;
    }

    public Company removeTenant(Tenant tenant) {
        this.tenants.remove(tenant);
        tenant.setCompanies(null);
        return this;
    }

    public void setCities(Set<City> cities) {
        if (this.cities != null) {
            this.cities.forEach(i -> i.setCompanies(null));
        }
        if (cities != null) {
            cities.forEach(i -> i.setCompanies(this));
        }
        this.cities = cities;
    }

    public Company cities(Set<City> cities) {
        this.setCities(cities);
        return this;
    }

    public Company addCity(City city) {
        this.cities.add(city);
        city.setCompanies(this);
        return this;
    }

    public Company removeCity(City city) {
        this.cities.remove(city);
        city.setCompanies(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return getId() != null && getId().equals(((Company) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", legalName='" + getLegalName() + "'" +
            ", tradeName='" + getTradeName() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", stateRegistration='" + getStateRegistration() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
