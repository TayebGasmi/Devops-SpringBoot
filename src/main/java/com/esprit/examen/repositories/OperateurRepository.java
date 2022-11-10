package com.esprit.examen.repositories;

import com.esprit.examen.entities.Operateur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperateurRepository extends CrudRepository<Operateur, Long> {

}
