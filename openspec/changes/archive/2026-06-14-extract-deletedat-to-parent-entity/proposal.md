## Why

The `deletedAt` soft-delete field is duplicated across 11 domain entities (Tenant, Supplier, Customer, Person, Company, Product, RawMaterial, Warehouse, StockMovement, Sale, SaleItem) with identical boilerplate: field declaration, Lombok-generated getter/setter, fluent setter, and toString inclusion. This violates DRY and makes it harder to consistently apply soft-delete behavior or change the field definition across all entities.

## What Changes

- **Add** a `SoftDeletableEntity` `@MappedSuperclass` extending `AbstractAuditingEntity` (or standalone) that declares the `deletedAt` field with its `@Column` mapping, fluent setter, and `@JsonView` annotation
- **Refactor** 11 domain entities to extend `SoftDeletableEntity` instead of implementing `Serializable` directly, removing their duplicated `deletedAt` field, fluent setter, and toString reference
- **Preserve** existing behavior: services, query services, criteria classes, REST endpoints, Liquibase migrations, and the entire Angular frontend continue to work without changes since the `deletedAt` field remains accessible via inheritance

## Capabilities

### New Capabilities

- `soft-deletable-entity`: A `@MappedSuperclass` for domain entities that need soft-delete support, providing a standardized `deletedAt` field with JPA column mapping, JSON serialization, and fluent accessor

### Modified Capabilities

<!-- No existing specs to modify -->

## Impact

- **Domain layer**: 11 entities modified (extend new parent, remove duplicated field/setter/toString); 1 new file (`SoftDeletableEntity.java`)
- **No changes needed**: Services, query services, criteria, REST controllers, Liquibase migrations, Angular frontend (models, services, templates) — all reference `deletedAt` through the entity's public getter/JSON serialization which remains identical
- **Reference entities** (Country, State, City) are unaffected — they don't need soft-delete
