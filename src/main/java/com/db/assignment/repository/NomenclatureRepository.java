package com.db.assignment.repository;

import com.db.assignment.domain.Nomenclature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NomenclatureRepository extends JpaRepository<Nomenclature, Long> {

    Nomenclature findByOrderId(Long orderId);
}
