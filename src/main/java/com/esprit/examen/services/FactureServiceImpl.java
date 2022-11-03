package com.esprit.examen.services;

import java.util.*;

import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esprit.examen.entities.DetailFacture;
import com.esprit.examen.entities.Facture;
import com.esprit.examen.entities.Fournisseur;
import com.esprit.examen.entities.Operateur;
import com.esprit.examen.entities.Produit;
import com.esprit.examen.repositories.DetailFactureRepository;
import com.esprit.examen.repositories.FactureRepository;
import com.esprit.examen.repositories.FournisseurRepository;
import com.esprit.examen.repositories.OperateurRepository;
import com.esprit.examen.repositories.ProduitRepository;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("ALL")
@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class FactureServiceImpl implements IFactureService {

    FactureRepository factureRepository;

    OperateurRepository operateurRepository;

    DetailFactureRepository detailFactureRepository;

    FournisseurRepository fournisseurRepository;

    ProduitRepository produitRepository;

    ReglementServiceImpl reglementService;


    @Override
    public List<Facture> retrieveAllFactures() {
        List<Facture> factures = factureRepository.findAll();
        for (Facture facture : factures) {
            log.info(" facture : " + facture);
        }
        return factures;
    }


    public Facture addFacture(Facture f) {
        return addDetailsFacture(factureRepository.save(f), f.getDetailsFacture());
    }

    /*
     * calculer les montants remise et le montant total d'un détail facture
     * ainsi que les montants d'une facture
     */
    private Facture addDetailsFacture(Facture f, Set<DetailFacture> detailsFacture) {
        float montantFacture = 0;
        float montantRemise = 0;
        for (DetailFacture detail : detailsFacture) {
            //Récuperer le produit


            Produit produit = produitRepository.findById(detail.getProduit().getIdProduit()).orElseThrow(NullPointerException::new);
            //Calculer le montant total pour chaque détail Facture
            float prixTotalDetail = detail.getQteCommandee() * produit.getPrix();
            //Calculer le montant remise pour chaque détail Facture
            float montantRemiseDetail = (prixTotalDetail * detail.getPourcentageRemise()) / 100;
            float prixTotalDetailRemise = prixTotalDetail - montantRemiseDetail;
            detail.setMontantRemise(montantRemiseDetail);
            detail.setPrixTotalDetail(prixTotalDetailRemise);
            //Calculer le montant total pour la facture
            montantFacture = montantFacture + prixTotalDetailRemise;
            //Calculer le montant remise pour la facture
            montantRemise = montantRemise + montantRemiseDetail;
            detail.setFacture(f);
            detailFactureRepository.save(detail);

        }
        f.setMontantFacture(montantFacture);
        f.setMontantRemise(montantRemise);
        return f;
    }

    @Override
    public void cancelFacture(Long factureId) {
        // Méthode 01
        //Facture facture = factureRepository.findById(factureId).get();
        Facture facture = factureRepository.findById(factureId).orElse(new Facture());
        facture.setArchivee(true);
        factureRepository.save(facture);
        //Méthode 02 (Avec JPQL)
        factureRepository.updateFacture(factureId);
    }

    @Override
    public Facture retrieveFacture(Long factureId) {

        Facture facture = factureRepository.findById(factureId).orElse(null);
        log.info("facture :" + facture);
        return facture;
    }

    @Override
    public List<Facture> getFacturesByFournisseur(Long idFournisseur) {
        Fournisseur fournisseur = fournisseurRepository.findById(idFournisseur).orElseThrow(NullPointerException::new);
        return new ArrayList<>(fournisseur.getFactures());
    }

    @Override
    public void assignOperateurToFacture(Long idOperateur, Long idFacture) {
        Facture facture = factureRepository.findById(idFacture).orElseThrow(NullPointerException::new);
        Operateur operateur = operateurRepository.findById(idOperateur).orElseThrow(NullPointerException::new);
        operateur.getFactures().add(facture);
        operateurRepository.save(operateur);
    }

    @Override
    public float pourcentageRecouvrement(Date startDate, Date endDate) {
        float totalFacturesEntreDeuxDates = factureRepository.getTotalFacturesEntreDeuxDates(startDate, endDate);
        float totalRecouvrementEntreDeuxDates = reglementService.getChiffreAffaireEntreDeuxDate(startDate, endDate);
        return (totalRecouvrementEntreDeuxDates / totalFacturesEntreDeuxDates) * 100;
    }

    @Override
    public List<Produit> getListProductByfactue(Long idFacture) {
        Facture facture = factureRepository.findById(idFacture).orElseThrow(NullPointerException::new);
        List<Produit> list = new ArrayList<>();

        try {
            facture.getDetailsFacture().forEach(d -> list.add(d.getProduit()));
            return list;
        } catch (Exception e) {
            log.info("NullPointerException thrown!");
            return Collections.emptyList();
        }

    }


}