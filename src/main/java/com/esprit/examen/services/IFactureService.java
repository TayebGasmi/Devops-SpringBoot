package com.esprit.examen.services;

import com.esprit.examen.entities.Facture;
import com.esprit.examen.entities.Produit;

import java.util.Date;
import java.util.List;

public interface IFactureService {
    List<Facture> retrieveAllFactures();

    List<Facture> getFacturesByFournisseur(Long idFournisseur);

    Facture addFacture(Facture f);

    void cancelFacture(Long id);

    Facture retrieveFacture(Long id);

    void assignOperateurToFacture(Long idOperateur, Long idFacture);

    float pourcentageRecouvrement(Date startDate, Date endDate);

    List<Produit> getListProductByfactue(Long idFacture);
}
