## Why

All 14 business entity classes carry hand-written builder-style fluent setter methods (e.g., `name(String)` returning `this`) that duplicate what Lombok's `@Setter` already generates. These methods add verbosity without value — Lombok provides standard JavaBean setters, and the project consistently uses them in production code. Removing them simplifies entities, reduces maintenance burden, and eliminates a latent bug in Product's bidirectional `setTenants()` (currently routed through Lombok without synchronizing back-references).

## What Changes

- **REMOVE** all builder-style fluent methods (`id(Long)`, `name(String)`, `field(FieldType)`, and collection-passthrough methods) from all 14 entity classes. Only field-specific builder methods are affected — add/remove collection methods and custom bidirectional setters are preserved.
- **ADD** a custom `setTenants(Set<Tenant>)` method to `Product.java` following the existing project pattern (currently it delegates to Lombok, breaking bidirectional sync).
- **UPDATE** all integration tests (`*IT.java`) and entity test samples (`*TestSamples.java`) to use standard setters instead of builder chaining.

## Capabilities

### New Capabilities

- `entity-method-conventions`: Entities expose only Lombok-generated getters/setters for simple fields, hand-written `add`/`remove` methods for child collections (with back-reference sync), and custom setters for bidirectional relationships where Lombok's generated setter is insufficient.

### Modified Capabilities

<!-- No existing spec requirements change -->

## Impact

- **14 entity classes** in `com.github.robsonrjunior.domain` — builder methods removed, Product gains custom `setTenants()`
- **14 resource IT files** (`*ResourceIT.java`) — builder chains converted to setter calls
- **15 test sample files** (`*TestSamples.java`) — builder chains converted to setter calls
- **11 domain unit test files** (`*Test.java`) — **no changes** (only use add/remove methods, which are preserved)
- Zero production code changes needed (no `src/main` files use entity builder methods)
