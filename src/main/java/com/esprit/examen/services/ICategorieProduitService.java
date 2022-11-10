package com.esprit.examen.services;

import com.esprit.examen.entities.CategorieProduit;

import java.util.List;


public interface ICategorieProduitService {

    List<CategorieProduit> retrieveAllCategorieProduits();

    CategorieProduit addCategorieProduit(CategorieProduit cp);

    void deleteCategorieProduit(Long id);

    CategorieProduit updateCategorieProduit(CategorieProduit cp);

    CategorieProduit retrieveCategorieProduit(Long id);

}
