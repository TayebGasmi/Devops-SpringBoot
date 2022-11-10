package com.esprit.examen.repositories;

import com.esprit.examen.entities.DetailFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailFactureRepository extends JpaRepository<DetailFacture, Long> {

}
