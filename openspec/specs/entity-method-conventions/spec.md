# Entity Method Conventions

## Purpose

Defines the conventions for entity class methods in the super-erp domain. Entities rely on Lombok for standard accessors, while hand-written methods are reserved for bidirectional relationship management.

## Requirements

### Requirement: Entity classes expose only standard accessors for simple fields

Entity classes SHALL rely exclusively on Lombok `@Getter`/`@Setter` annotations for simple field accessors. Entity classes MUST NOT define hand-written fluent builder-style methods (methods that accept a value, delegate to a setter, and return `this`) for simple scalar fields.

#### Scenario: Simple field access uses Lombok-generated setters

- **WHEN** production or test code needs to set a simple field on an entity (e.g., `name`, `code`, `active`)
- **THEN** the code SHALL call the Lombok-generated `setXxx(value)` method directly, without an intermediate fluent wrapper

#### Scenario: No builder-style methods exist on any entity class

- **WHEN** inspecting any entity class in `com.github.robsonrjunior.domain`
- **THEN** no method of the form `Entity fieldName(FieldType value)` that delegates to `this.setFieldName(value)` and returns `this` SHALL exist

### Requirement: Custom bidirectional setters are preserved for relationship collections

Entity classes SHALL define hand-written `void setXxx(Set<Entity>)` methods for `@OneToMany` and `@ManyToMany` collections that require bidirectional relationship synchronization. These methods MUST clear back-references on previously associated entities and set back-references on newly associated entities before assigning the field.

#### Scenario: Custom setter clears old back-references

- **WHEN** calling `setTenants(newSet)` on an entity that already has tenants in the collection
- **THEN** each previously-associated entity SHALL have its back-reference cleared (set to `null`)

#### Scenario: Custom setter sets new back-references

- **WHEN** calling `setTenants(newSet)` with a non-null set
- **THEN** each entity in the new set SHALL have its back-reference set to `this`

#### Scenario: Every entity with add/remove methods has a custom setter

- **WHEN** an entity defines `addXxx()` and `removeXxx()` methods for a bidirectional collection
- **THEN** the entity MUST also define a custom `void setXxx(Set<Entity>)` with bidirectional synchronization logic

### Requirement: add/remove methods for child collections are preserved

Entity classes SHALL define `addXxx(Entity)` and `removeXxx(Entity)` methods for child collections that manage bidirectional relationship integrity. These methods MUST add/remove the entity from the collection AND set/clear the back-reference on the child entity.

#### Scenario: add method synchronizes both sides

- **WHEN** calling `parent.addChild(child)` on an entity with a child collection
- **THEN** the child SHALL be added to the parent's collection AND the child's back-reference SHALL be set to the parent

#### Scenario: remove method synchronizes both sides

- **WHEN** calling `parent.removeChild(child)` on an entity with a child collection
- **THEN** the child SHALL be removed from the parent's collection AND the child's back-reference SHALL be set to `null`
