## Context

The codebase has 11 domain entities that each independently declare an identical `deletedAt` field for soft-delete support. An `AbstractAuditingEntity` mapped superclass already exists (used only by `User`) providing audit fields. The existing boilerplate per entity:

```java
// In each of 11 entity files:
@Column(name = "deleted_at")
@JsonView({ Multiple.class, Single.class })
private Instant deletedAt;

// Fluent setter (uses Lombok-generated setDeletedAt):
public EntityName deletedAt(Instant deletedAt) {
    this.setDeletedAt(deletedAt);
    return this;
}

// toString includes getDeletedAt()
```

The duplicate code makes it tedious to add soft-delete to new entities or change the field definition (e.g., adding a `deletedBy` field later).

Constraints:
- Must not break existing JSON serialization (services, Angular frontend rely on the field)
- Must not require Liquibase migration changes
- Reference entities (Country, State, City) must not gain `deletedAt`
- JHipster regeneration may overwrite entity files

## Goals / Non-Goals

**Goals:**
- Centralize the `deletedAt` field definition in a single `@MappedSuperclass`
- Remove duplicated field, fluent setter, and toString references from 11 entities
- Preserve identical runtime behavior (column mapping, JSON output, filtering, sorting, forms)
- Future entities can opt into soft-delete by extending the parent

**Non-Goals:**
- No changes to services, query services, criteria, REST controllers, Liquibase migrations
- No changes to the Angular frontend (models, services, forms, templates)
- No soft-delete query filtering logic (that's a separate concern)
- No changes to Country, State, City (they remain non-soft-deletable)
- No changes to `AbstractAuditingEntity` or its sole subclass `User`

## Decisions

### D1: `@MappedSuperclass` over `@Entity` with inheritance strategy

**Chosen**: `@MappedSuperclass`

| Factor | `@MappedSuperclass` | `@Entity` + `@Inheritance` |
|---|---|---|
| Separate table | No (fields inlined into subclass tables) | Yes (JOINED/SINGLE_TABLE) |
| Queries | Direct select from entity table | May require joins |
| Liquibase impact | None | New table + FK columns |
| KISS | Simple field reuse | Adds DB complexity |

Rationale: There's no need for a separate `deleted_at` table. The mapped superclass simply embeds the column definition into each subclass's table — exactly what exists today.

### D2: Standalone class, not extension of `AbstractAuditingEntity`

**Chosen**: `SoftDeletableEntity` as a standalone `@MappedSuperclass` implementing `Serializable`

| Factor | Extend `AbstractAuditingEntity` | Standalone |
|---|---|---|
| Audit fields | Forces createdBy/createdDate/etc. on all soft-deletable entities | Entities choose their own audit approach |
| Generic type param | Must specify `<Long>` which conflicts if ID type changes | No generic parameter |
| User entity | Already extends `AbstractAuditingEntity<Long>` | Unaffected |
| Coupling | Ties soft-delete to auditing concern | Separation of concerns |

Rationale: Soft-delete and auditing are orthogonal concerns. Not all entities that need soft-delete need Spring Data audit fields. Keeping them separate allows independent evolution.

### D3: No `@JsonView` on parent class field

**Chosen**: Declare `deletedAt` without `@JsonView` annotation

Entity-specific view interfaces (`Tenant.Single`/`Tenant.Multiple`, etc.) are inner classes of each entity. They cannot be referenced from a shared parent. Creating shared view interfaces adds complexity for minimal benefit — only 1 of 14 controllers (ProductResource) uses `@JsonView`. The 5 Product endpoints that use views will simply not include `deletedAt` (acceptable; soft-delete is internal metadata).

Alternative considered: Define shared `ViewInterfaces.Single`/`.Multiple` in a common package, with entity views extending them. Rejected as over-engineering for current needs.

### D4: No fluent setter in parent class

**Chosen**: Omit the `deletedAt(Instant)` fluent setter from the parent

The fluent setter pattern returns `this` (entity type), which can't be expressed in the parent without generics. Since `deletedAt` is set programmatically during soft-delete operations (not in builder chains), a fluent setter is unnecessary. Lombok still generates the standard `getDeletedAt()`/`setDeletedAt(Instant)`.

### D5: Entity structural changes

Each of the 11 entities changes from:
```java
public class Tenant implements Serializable {
    // ...
    @Column(name = "deleted_at")
    @JsonView({ Multiple.class, Single.class })
    private Instant deletedAt;
    // ...
    public Tenant deletedAt(Instant deletedAt) { ... }
    // toString includes getDeletedAt()
}
```
to:
```java
public class Tenant extends SoftDeletableEntity {
    // deletedAt field removed (inherited)
    // fluent setter removed
    // toString: deletedAt reference removed
    // equals/hashCode: unchanged (based only on id)
}
```

## Risks / Trade-offs

[ProductResource `@JsonView` endpoints lose `deletedAt`] → Minimal impact; only 5 endpoints on 1 controller. If `deletedAt` is needed in view-filtered responses later, add shared view interfaces as a follow-up.

[JHipster regeneration overwrites changes] → JHipster's `jhipster-needle-*` comments mark insertion points. Entity class declarations and field regions outside these markers may be overwritten. This change modifies the class declaration (`extends SoftDeletableEntity`) which is outside needle regions. Future regenerations will need manual reconciliation, but this is the case for any manual entity customization in JHipster.

[Lombok `@Getter`/`@Setter` interaction] → Verified safe: Lombok generates getters/setters for inherited fields when `@Getter`/`@Setter` is on the subclass. The subclass `@Getter @Setter` on each entity works correctly with inherited `deletedAt`.
