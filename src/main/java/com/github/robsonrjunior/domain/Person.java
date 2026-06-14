package com.github.robsonrjunior.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
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
public class Person extends SoftDeletableEntity {

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

    public void setCustomer(Customer customer) {
        if (this.customer != null) {
            this.customer.setPerson(null);
        }
        if (customer != null) {
            customer.setPerson(this);
        }
        this.customer = customer;
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

    public void setTenants(Set<Tenant> tenants) {
        if (this.tenants != null) {
            this.tenants.forEach(i -> i.setPeople(null));
        }
        if (tenants != null) {
            tenants.forEach(i -> i.setPeople(this));
        }
        this.tenants = tenants;
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
            "}";
    }
}
