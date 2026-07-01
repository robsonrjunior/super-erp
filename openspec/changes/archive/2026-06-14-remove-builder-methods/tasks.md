## 1. Fix Product.java — add missing custom setter

- [x] 1.1 Add custom `void setTenants(Set<Tenant>)` to Product.java following the standard pattern (clear old back-references, set new back-references, assign field)
- [x] 1.2 Remove the builder method `Product tenants(Set<Tenant>)` from Product.java (it delegates to the setter — the custom setter will handle it now)

## 2. Remove builder methods from entity classes

- [x] 2.1 Remove builder methods from Tenant.java (id, name, code, active, customers, suppliers, people, companies, products, rawMaterials, warehouses, sales, saleItems, stockMovements)
- [x] 2.2 Remove builder methods from Country.java (id, name, isoCode) — keep addStates/removeStates and custom setStateses
- [x] 2.3 Remove builder methods from State.java (id, name, code) — keep addCities/removeCities and custom setCitieses
- [x] 2.4 Remove builder methods from City.java (id, name, suppliers, customers, people, companies, warehouses, state)
- [x] 2.5 Remove builder methods from Supplier.java (id, legalName, tradeName, taxId, partyType, email, phone, active, person, company, rawMaterials, tenants, cities) — keep add/remove and custom setters
- [x] 2.6 Remove builder methods from Customer.java (id, legalName, tradeName, taxId, partyType, email, phone, active, person, company, sales, tenants, cities) — keep add/remove and custom setters
- [x] 2.7 Remove builder methods from Person.java (id, fullName, cpf, birthDate, email, phone, active, customer, supplier, tenants, cities) — keep add/remove and custom setters
- [x] 2.8 Remove builder methods from Company.java (id, legalName, tradeName, cnpj, stateRegistration, email, phone, active, customer, supplier, tenants, cities) — keep add/remove and custom setters
- [x] 2.9 Remove builder methods from Product.java (id, name, sku, unitOfMeasure, unitDecimalPlaces, salePrice, costPrice, minStock, active, saleItems, stockMovements) — keep addTenant/removeTenant
- [x] 2.10 Remove builder methods from RawMaterial.java (id, name, sku, unitOfMeasure, unitDecimalPlaces, unitCost, minStock, active, stockMovements, tenants, primarySuppliers) — keep add/remove and custom setters
- [x] 2.11 Remove builder methods from Warehouse.java (id, name, code, active, stockMovements, sales, tenants, cities) — keep add/remove and custom setters
- [x] 2.12 Remove builder methods from StockMovement.java (id, movementDate, movementType, quantity, unitCost, referenceNumber, notes, tenants, warehouses, products, rawMaterials) — keep add/remove and custom setters
- [x] 2.13 Remove builder methods from Sale.java (id, saleDate, saleNumber, status, grossAmount, discountAmount, netAmount, notes, items, tenants, warehouses, customers) — keep add/remove and custom setters
- [x] 2.14 Remove builder methods from SaleItem.java (id, quantity, unitPrice, discountAmount, lineTotal, tenants, sales, products) — keep add/remove and custom setters

## 3. Convert builder chains to setters in test sample files

- [x] 3.1 Update TenantTestSamples.java
- [x] 3.2 Update CountryTestSamples.java
- [x] 3.3 Update StateTestSamples.java
- [x] 3.4 Update CityTestSamples.java
- [x] 3.5 Update SupplierTestSamples.java
- [x] 3.6 Update CustomerTestSamples.java
- [x] 3.7 Update PersonTestSamples.java
- [x] 3.8 Update CompanyTestSamples.java
- [x] 3.9 Update ProductTestSamples.java
- [x] 3.10 Update RawMaterialTestSamples.java
- [x] 3.11 Update WarehouseTestSamples.java
- [x] 3.12 Update StockMovementTestSamples.java
- [x] 3.13 Update SaleTestSamples.java
- [x] 3.14 Update SaleItemTestSamples.java
- [x] 3.15 Update AuthorityTestSamples.java

## 4. Convert builder chains to setters in integration test files

- [x] 4.1 Update TenantResourceIT.java
- [x] 4.2 Update CountryResourceIT.java
- [x] 4.3 Update StateResourceIT.java
- [x] 4.4 Update CityResourceIT.java
- [x] 4.5 Update SupplierResourceIT.java
- [x] 4.6 Update CustomerResourceIT.java
- [x] 4.7 Update PersonResourceIT.java
- [x] 4.8 Update CompanyResourceIT.java
- [x] 4.9 Update ProductResourceIT.java
- [x] 4.10 Update RawMaterialResourceIT.java
- [x] 4.11 Update WarehouseResourceIT.java
- [x] 4.12 Update StockMovementResourceIT.java
- [x] 4.13 Update SaleResourceIT.java
- [x] 4.14 Update SaleItemResourceIT.java

## 5. Verify

- [x] 5.1 Run `./gradlew compileJava` to confirm entity classes compile
- [x] 5.2 Run `./gradlew compileTestJava` to confirm test files compile
