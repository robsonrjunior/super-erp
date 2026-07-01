## 1. Create parent mapped superclass

- [x] 1.1 Create `SoftDeletableEntity.java` in `domain/` package with `@MappedSuperclass`, `Serializable`, Lombok `@Getter`/`@Setter`, and `deletedAt` field with `@Column(name = "deleted_at")`

## 2. Refactor entities to extend SoftDeletableEntity

- [x] 2.1 Refactor Tenant: change `implements Serializable` to `extends SoftDeletableEntity`, remove `deletedAt` field/fluent-setter/toString-reference
- [x] 2.2 Refactor Supplier: change `implements Serializable` to `extends SoftDeletableEntity`, remove `deletedAt` field/fluent-setter/toString-reference
- [x] 2.3 Refactor Customer: change `implements Serializable` to `extends SoftDeletableEntity`, remove `deletedAt` field/fluent-setter/toString-reference
- [x] 2.4 Refactor Person: change `implements Serializable` to `extends SoftDeletableEntity`, remove `deletedAt` field/fluent-setter/toString-reference
- [x] 2.5 Refactor Company: change `implements Serializable` to `extends SoftDeletableEntity`, remove `deletedAt` field/fluent-setter/toString-reference
- [x] 2.6 Refactor Product: change `implements Serializable` to `extends SoftDeletableEntity`, remove `deletedAt` field/fluent-setter/toString-reference
- [x] 2.7 Refactor RawMaterial: change `implements Serializable` to `extends SoftDeletableEntity`, remove `deletedAt` field/fluent-setter/toString-reference
- [x] 2.8 Refactor Warehouse: change `implements Serializable` to `extends SoftDeletableEntity`, remove `deletedAt` field/fluent-setter/toString-reference
- [x] 2.9 Refactor StockMovement: change `implements Serializable` to `extends SoftDeletableEntity`, remove `deletedAt` field/fluent-setter/toString-reference
- [x] 2.10 Refactor Sale: change `implements Serializable` to `extends SoftDeletableEntity`, remove `deletedAt` field/fluent-setter/toString-reference
- [x] 2.11 Refactor SaleItem: change `implements Serializable` to `extends SoftDeletableEntity`, remove `deletedAt` field/fluent-setter/toString-reference

## 3. Verification

- [x] 3.1 Run Gradle build (`./gradlew compileJava`) to verify all entities compile
- [x] 3.2 Run backend tests (`./gradlew test`) to verify no regressions
- [x] 3.3 Verify Country, State, City remain unchanged (no `deletedAt`, no `extends SoftDeletableEntity`)
