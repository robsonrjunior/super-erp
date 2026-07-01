## ADDED Requirements

### Requirement: Soft-deletable entity mapped superclass

The system SHALL provide a `SoftDeletableEntity` abstract class annotated with `@MappedSuperclass` that domain entities can extend to inherit a standardized soft-delete field.

The class SHALL:
- Be located in the `com.github.robsonrjunior.domain` package
- Be annotated with `@MappedSuperclass`
- Implement `Serializable`
- Contain a `private Instant deletedAt` field mapped to database column `deleted_at`
- Use Lombok `@Getter` and `@Setter` at the class level

#### Scenario: Entity extends SoftDeletableEntity

- **WHEN** a JPA entity class extends `SoftDeletableEntity`
- **THEN** the entity inherits the `deletedAt` property with getter and setter
- **THEN** the `deleted_at` column is present in the entity's database table
- **THEN** the field is serialized to JSON in REST responses

#### Scenario: Reference entity does not extend SoftDeletableEntity

- **WHEN** a reference entity like `Country` does not extend `SoftDeletableEntity`
- **THEN** the entity SHALL NOT have a `deletedAt` field or `deleted_at` column

### Requirement: Entities with soft-delete extend SoftDeletableEntity

The following domain entities SHALL extend `SoftDeletableEntity` instead of implementing `Serializable` directly:
- Tenant
- Supplier
- Customer
- Person
- Company
- Product
- RawMaterial
- Warehouse
- StockMovement
- Sale
- SaleItem

#### Scenario: Entity inherits deletedAt via SoftDeletableEntity

- **WHEN** `Tenant` (or any of the 11 listed entities) is loaded by JPA
- **THEN** the `deletedAt` property is accessible via `getDeletedAt()`
- **THEN** the column mapping to `deleted_at` matches the current database schema

#### Scenario: Entity no longer declares deletedAt directly

- **WHEN** inspecting the source code of `Tenant` (or any of the 11 entities)
- **THEN** there SHALL NOT be a `private Instant deletedAt` field declaration
- **THEN** there SHALL NOT be a fluent `deletedAt(Instant)` setter method
- **THEN** the `toString()` method SHALL NOT reference `getDeletedAt()`

### Requirement: Backward compatibility with existing queries and filters

The inheritance-based approach SHALL preserve compatibility with:
- JPA metamodel references (e.g., `Tenant_.deletedAt`)
- Criteria filter classes (e.g., `TenantCriteria.deletedAt`)
- Query service Specification builders (e.g., `buildRangeSpecification(criteria.getDeletedAt(), Tenant_.deletedAt)`)
- REST endpoint JSON serialization (except view-filtered endpoints, see design.md)

#### Scenario: JPA metamodel reference still compiles

- **WHEN** `TenantQueryService` references `Tenant_.deletedAt`
- **THEN** the code SHALL compile without changes

#### Scenario: Angular frontend receives deletedAt in JSON

- **WHEN** the Angular frontend calls any entity REST endpoint (list, get, create, update)
- **THEN** the `deletedAt` field SHALL be present in the JSON response
- **THEN** the Angular model, service, form, and template code SHALL work without changes

### Requirement: Lombok compatibility with inherited fields

The `@Getter` and `@Setter` Lombok annotations on subclass entities SHALL generate correct getter and setter methods for the inherited `deletedAt` field.

#### Scenario: Tenant entity uses Lombok on subclass

- **WHEN** `Tenant` is annotated with `@Getter @Setter` and extends `SoftDeletableEntity`
- **THEN** `tenant.getDeletedAt()` SHALL return the entity's deletedAt value
- **THEN** `tenant.setDeletedAt(instant)` SHALL set the entity's deletedAt value
