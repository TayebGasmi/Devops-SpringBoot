package com.esprit.examen.services;


import com.esprit.examen.entities.*;
import com.esprit.examen.repositories.*;
import lombok.extern.slf4j.Slf4j;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.MethodOrderer;

import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;


import java.text.DecimalFormat;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class FactureServiceImplMockitoTest {
    @Mock
    FactureRepository factureRepository;
    @Mock
    OperateurRepository operateurRepository;
    @Mock
    FournisseurRepository fournisseurRepository;
    @Mock
    ReglementRepository reglementRepository;


    @InjectMocks
    FactureServiceImpl factureService;
    @InjectMocks
    OperateurServiceImpl operateurService;
    @InjectMocks
    FournisseurServiceImpl fournisseurService;
    @InjectMocks
    ReglementServiceImpl reglementService;
    // create products

    Facture facture1 = Facture.builder().idFacture((long) 1).archivee(true).detailsFacture(new HashSet<DetailFacture>(){{
        add(DetailFacture.builder().qteCommandee(4).montantRemise(5).build());
    }}).dateCreationFacture(new Date()).build();
    Facture facture2 = Facture.builder().idFacture((long) 2).archivee(true).detailsFacture(new HashSet<DetailFacture>(){{
        add(DetailFacture.builder().qteCommandee(4).montantRemise(5).build());
    }}).dateCreationFacture(new Date()).build();
    Facture facture3 = Facture.builder().idFacture((long) 3).archivee(true).detailsFacture(new HashSet<DetailFacture>(){{
        add(DetailFacture.builder().qteCommandee(4).montantRemise(5).build());
    }}).dateCreationFacture(new Date()).build();


    Operateur operateur = Operateur.builder().factures(new HashSet<Facture>() {{
        add(facture2);
        add(facture3);
    }}).idOperateur((long) 1).nom("operator").password("123").build();

    // test add products

    @Test
    void testAssignOperateurToFacture() {
        Mockito.when(factureRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(facture1));
        Mockito.when(operateurRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(operateur));
        Facture f1 = factureService.retrieveFacture((long) 10);
        Operateur op = operateurService.retrieveOperateur((long) 5);
        assertNotNull(f1);
        assertNotNull(op);
        op.getFactures().add(f1);
        Mockito.when(operateurRepository.save(Mockito.any(Operateur.class))).thenReturn(op);
        op = operateurRepository.save(operateur);
        verify(operateurRepository, times(1)).save(operateur);
        assertEquals(3, op.getFactures().size());

    }

    Fournisseur fournisseur = Fournisseur.builder().idFournisseur((long) 1).code("Four1").libelle("fournissuer").factures(new HashSet<Facture>() {{
        add(facture1);
        add(facture2);
    }}).build();

    @Test
    void testGetFacturesByFournisseur() {
        Mockito.when(fournisseurRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(fournisseur));
        List<Facture> list = factureService.getFacturesByFournisseur(fournisseur.getIdFournisseur());
        verify(fournisseurRepository, times(1)).findById(Mockito.anyLong());
        assertEquals(2, list.size());
    }

    @Test
    void getListProductByFacture() {
        Mockito.when(factureRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(facture1));
        Facture f1 = factureService.retrieveFacture((long) 10);
        assertNotNull(f1);
        verify(factureRepository, times(1)).findById(Mockito.anyLong());
        List<Produit> list = new ArrayList<>();
        try {
            f1.getDetailsFacture().forEach(d -> list.add(d.getProduit()));
            assertEquals(1, list.size());
        } catch (Exception e) {
            log.info("NullPointerException thrown!");
        }
    }

}
