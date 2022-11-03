package com.esprit.examen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.esprit.examen.entities.CategorieProduit;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieProduitRepository extends JpaRepository<CategorieProduit, Long>{

}
