package com.esprit.examen.services;

import com.esprit.examen.entities.CategorieProduit;
import com.esprit.examen.entities.Produit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class ProduitServiceImplTest {

    static List<CategorieProduit> savedCategories = new ArrayList<CategorieProduit>();
    static List<Produit> savedProduits = new ArrayList<Produit>();
    @Autowired
    ICategorieProduitService categorieProduitService;
    @Autowired
    IProduitService produitService;
    List<Produit> produits = new ArrayList<Produit>() {
        {
            add(Produit.builder().codeProduit("code1").libelleProduit("Business").prix(10).dateCreation(new Date()).build());
            add(Produit.builder().codeProduit("code2").libelleProduit("Sports").prix(50).dateCreation(new Date()).build());
            add(Produit.builder().codeProduit("code3").libelleProduit("Business").prix(20).dateCreation(new Date()).build());
            add(Produit.builder().codeProduit("code4").libelleProduit("Sports").prix(30).dateCreation(new Date()).build());

        }
    };

    List<CategorieProduit> categories = new ArrayList<CategorieProduit>() {
        {
            add(CategorieProduit.builder().codeCategorie("code1").libelleCategorie("Business").build());
            add(CategorieProduit.builder().codeCategorie("code2").libelleCategorie("Sports").build());
        }
    };

    @Order(1)
    @Test
    public void addCategorieProduit() {
        categories.forEach(categorie -> {
            CategorieProduit savedCategorie = categorieProduitService.addCategorieProduit(categorie);
            assertAll("add category",
                    () -> assertNotNull(savedCategorie.getIdCategorieProduit()),
                    () -> assertEquals(categorie.getCodeCategorie(), savedCategorie.getCodeCategorie()),
                    () -> assertEquals(categorie.getLibelleCategorie(), savedCategorie.getLibelleCategorie())
            );
        });
        savedCategories = categorieProduitService.retrieveAllCategorieProduits();
        assertEquals(savedCategories.size(), categories.size());
        log.info("save categorie test passed");
    }


    @Test
    @Order(2)

    public void addProduit() {
        CategorieProduit categorieProduit = savedCategories.get(0);
        produits.forEach(produit -> {
            produit.setCategorieProduit(categorieProduit);
            Produit p = produitService.addProduit(produit);
            assertAll("add produit",
                    () -> assertNotNull(p.getIdProduit()),
                    () -> assertEquals(p.getCodeProduit(), produit.getCodeProduit()),
                    () -> assertEquals(p.getLibelleProduit(), produit.getLibelleProduit()),
                    () -> assertEquals(p.getPrix(), produit.getPrix()),
                    () -> assertEquals(p.getDateCreation(), produit.getDateCreation()),
                    () -> assertEquals(p.getCategorieProduit(), produit.getCategorieProduit())
            );
        });
        savedProduits = produitService.retrieveAllProduits();
        assertEquals(savedProduits.size(), produits.size());
        log.info("product added successfully");
    }



    @Test
    @Order(3)
    public void deleteProduit() {
        savedProduits = produitService.retrieveAllProduits();
        for (Produit produit : savedProduits) {
            produitService.deleteProduit(produit.getIdProduit());
            assertNull(produitService.retrieveProduit(produit.getIdProduit()));
        }
        assertEquals(0, produitService.retrieveAllProduits().size());
        log.info("product deleted successfully");
    }
    @Test
    @Order(4)
    public void deleteCategorieProduit() {
        savedCategories = categorieProduitService.retrieveAllCategorieProduits();
        for (CategorieProduit categorie : savedCategories) {
            categorieProduitService.deleteCategorieProduit(categorie.getIdCategorieProduit());
            assertNull(categorieProduitService.retrieveCategorieProduit(categorie.getIdCategorieProduit()));
        }
        assertEquals(0, categorieProduitService.retrieveAllCategorieProduits().size());
        log.info("category deleted successfully");
    }

}
