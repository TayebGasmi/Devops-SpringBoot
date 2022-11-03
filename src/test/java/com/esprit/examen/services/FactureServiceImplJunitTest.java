package com.esprit.examen.services;

import com.esprit.examen.entities.DetailFacture;
import com.esprit.examen.entities.Facture;
import com.esprit.examen.entities.Produit;
import com.esprit.examen.repositories.FactureRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.text.DecimalFormat;
import java.util.*;
import java.text.SimpleDateFormat;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
 class FactureServiceImplJunitTest {

    @Autowired
    IProduitService iProduitService;
    @Autowired
    IFactureService iFactureService;

    @Autowired
    FactureRepository factureRepository;


    // create  list of products
    List<Produit> productList = new ArrayList<Produit>(){
        {

            add(Produit.builder().codeProduit("CODE5").dateCreation(new Date()).libelleProduit("product5").prix(70).build());
            add(Produit.builder().codeProduit("CODE1").dateCreation(new Date()).libelleProduit("product1").prix(150).build());
            add(Produit.builder().codeProduit("CODE5").dateCreation(new Date()).libelleProduit("product55").prix(55).build());
            add(Produit.builder().codeProduit("CODE8").dateCreation(new Date()).libelleProduit("product8").prix(88).build());
            add(Produit.builder().codeProduit("CODE9").dateCreation(new Date()).libelleProduit("product9").prix(99).build());
            add(Produit.builder().codeProduit("CODE10").dateCreation(new Date()).libelleProduit("product10").prix(100).build());

        }
    };
   static List <Produit>products = new ArrayList<>();

    @Test
    @Order(1)
    void testAddProducts(){
        productList.forEach(p->{
            iProduitService.addProduit(p);
            assertAll( "test add products",
                    () -> assertNotNull(p.getIdProduit()),
                    () -> assertNotNull(p.getCodeProduit()),
                    () -> assertNotNull(p.getLibelleProduit()),
                    () -> assertNotNull(p.getDateCreation())
            );
        });
        products=iProduitService.retrieveAllProduits();
        assertEquals(productList.size(),products.size());

    }

    @Test
    @Order(2)
    void testAddFacture(){

        // create e list of detailFactures
        Set <DetailFacture> detailFactures = new HashSet<DetailFacture>(){
            {
                add(DetailFacture.builder().pourcentageRemise(7).qteCommandee(3).produit(products.get(0)).build());
                add(DetailFacture.builder().pourcentageRemise(6).qteCommandee(3).produit(products.get(1)).build());
                add(DetailFacture.builder().pourcentageRemise(10).qteCommandee(4).produit(products.get(2)).build());
                add(DetailFacture.builder().pourcentageRemise(18).qteCommandee(10).produit(products.get(3)).build());
                add(DetailFacture.builder().pourcentageRemise(14).qteCommandee(8).produit(products.get(4)).build());

            }
        };
        Facture facture = Facture.builder().archivee(true).dateCreationFacture(new Date()).detailsFacture(detailFactures).build();

        iFactureService.addFacture(facture);
        assertNotNull(facture.getIdFacture());
        assertEquals(5, facture.getDetailsFacture().size());
        assertEquals(2219.02,Double.parseDouble(new DecimalFormat("##.##").format(facture.getMontantFacture())));
        assertEquals(332.98,Double.parseDouble(new DecimalFormat("##.##").format(facture.getMontantRemise())));
    }
    @Test
    @Order(3)
    void testDeleteAllProducts(){

        iProduitService.retrieveAllProduits().forEach(p->{
            iProduitService.deleteProduit(p.getIdProduit());
        });
        assertEquals(0,iProduitService.retrieveAllProduits().size());
    }

    @Test
    @Order(4)
    void testDeleteFacture(){
        factureRepository.deleteAll(iFactureService.retrieveAllFactures());
        assertEquals(0,iFactureService.retrieveAllFactures().size());
    }
}
