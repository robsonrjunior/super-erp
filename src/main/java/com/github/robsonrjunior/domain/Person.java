package com.github.robsonrjunior.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class Person implements Serializable {

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
    @Column(name = "full_name", length = 120, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private String fullName;

    @NotNull
    @Size(min = 11, max = 14)
    @Column(name = "cpf", length = 14, nullable = false)
    @JsonView({ Multiple.class, Single.class })
    private String cpf;

    @Column(name = "birth_date")
    @JsonView({ Multiple.class, Single.class })
    private LocalDate birthDate;

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

    @Column(name = "deleted_at")
    @JsonView({ Multiple.class, Single.class })
    private Instant deletedAt;

    @JsonIgnoreProperties(value = { "person", "company", "sales", "tenants", "cities" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "person")
    @JsonView({ Multiple.class, Single.class })
    private Customer customer;

    @JsonIgnoreProperties(value = { "person", "company", "rawMaterials", "tenants", "cities" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "person")
    @JsonView({ Multiple.class, Single.class })
    private Supplier supplier;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "people")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "people")
    @JsonIgnoreProperties(value = { "suppliers", "customers", "people", "companies", "warehouses", "state" }, allowSetters = true)
    @JsonView({ Multiple.class, Single.class })
    private Set<City> cities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Person id(Long id) {
        this.setId(id);
        return this;
    }

    public Person fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public Person cpf(String cpf) {
        this.setCpf(cpf);
        return this;
    }

    public Person birthDate(LocalDate birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public Person email(String email) {
        this.setEmail(email);
        return this;
    }

    public Person phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public Person active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public Person deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setCustomer(Customer customer) {
        if (this.customer != null) {
            this.customer.setPerson(null);
        }
        if (customer != null) {
            customer.setPerson(this);
        }
        this.customer = customer;
    }

    public Person customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public void setSupplier(Supplier supplier) {
        if (this.supplier != null) {
            this.supplier.setPerson(null);
        }
        if (supplier != null) {
            supplier.setPerson(this);
        }
        this.supplier = supplier;
    }

    public Person supplier(Supplier supplier) {
        this.setSupplier(supplier);
        return this;
    }

    public void setTenants(Set<Tenant> tenants) {
        if (this.tenants != null) {
            this.tenants.forEach(i -> i.setPeople(null));
        }
        if (tenants != null) {
            tenants.forEach(i -> i.setPeople(this));
        }
        this.tenants = tenants;
    }

    public Person tenants(Set<Tenant> tenants) {
        this.setTenants(tenants);
        return this;
    }

    public Person addTenant(Tenant tenant) {
        this.tenants.add(tenant);
        tenant.setPeople(this);
        return this;
    }

    public Person removeTenant(Tenant tenant) {
        this.tenants.remove(tenant);
        tenant.setPeople(null);
        return this;
    }

    public void setCities(Set<City> cities) {
        if (this.cities != null) {
            this.cities.forEach(i -> i.setPeople(null));
        }
        if (cities != null) {
            cities.forEach(i -> i.setPeople(this));
        }
        this.cities = cities;
    }

    public Person cities(Set<City> cities) {
        this.setCities(cities);
        return this;
    }

    public Person addCity(City city) {
        this.cities.add(city);
        city.setPeople(this);
        return this;
    }

    public Person removeCity(City city) {
        this.cities.remove(city);
        city.setPeople(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return getId() != null && getId().equals(((Person) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", active='" + getActive() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
