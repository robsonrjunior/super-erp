package com.github.robsonrjunior.repository;

import com.github.robsonrjunior.domain.SaleItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SaleItem entity.
 */
@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Long>, JpaSpecificationExecutor<SaleItem> {}
