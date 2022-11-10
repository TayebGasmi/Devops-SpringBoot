package com.esprit.examen.repositories;

import com.esprit.examen.entities.DetailFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailFournisseurRepository extends JpaRepository<DetailFournisseur, Long> {

}
