package com.esprit.examen.services;

import com.esprit.examen.entities.CategorieProduit;
import com.esprit.examen.repositories.CategorieProduitRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Slf4j
public class CategorieProduitTest {
    @Mock
    CategorieProduitRepository categorieProduitRepository;
    @InjectMocks
    CategorieProduitServiceImpl categorieProduitService;
    CategorieProduit categorieProduit = CategorieProduit.builder().libelleCategorie("libelleCategorie").codeCategorie("codeCategorie").build();
    List<CategorieProduit> categorieProduits =new ArrayList<CategorieProduit>(){{
        add(CategorieProduit.builder().libelleCategorie("libelleCategorie").codeCategorie("codeCategorie").build());
        add(CategorieProduit.builder().libelleCategorie("libelleCategorie").codeCategorie("codeCategorie").build());
    }};

    @Test
    void addCategorieProduit() {
        Mockito.when(categorieProduitRepository.save(Mockito.any(CategorieProduit.class))).thenReturn(categorieProduit);
        CategorieProduit categorieProduit1 = categorieProduitService.addCategorieProduit(categorieProduit);
        assertAll("addCategorieProduit",
                () -> assertEquals(categorieProduit1.getLibelleCategorie(), categorieProduit.getLibelleCategorie()),
                () -> assertEquals(categorieProduit1.getCodeCategorie(), categorieProduit.getCodeCategorie())
        );
        verify(categorieProduitRepository, times(1)).save(Mockito.any(CategorieProduit.class));
    }
    @Test
    void retrieveAllCategorieProduit() {
        Mockito.when(categorieProduitRepository.findAll()).thenReturn(categorieProduits);
        List<CategorieProduit> categorieProduits1 = categorieProduitService.retrieveAllCategorieProduits();
        assertEquals(categorieProduits1.size(), categorieProduits.size());
        verify(categorieProduitRepository, times(1)).findAll();
    }
    @Test
    void updateCategorieProduit() {
        categorieProduit.setCodeCategorie("update");
        Mockito.when(categorieProduitRepository.save(Mockito.any(CategorieProduit.class))).thenReturn(categorieProduit);
        CategorieProduit categorieProduit1 = categorieProduitService.updateCategorieProduit(categorieProduit);
        assertAll("updateCategorieProduit",
                () -> assertEquals(categorieProduit1.getLibelleCategorie(), categorieProduit.getLibelleCategorie()),
                () -> assertEquals(categorieProduit1.getCodeCategorie(), categorieProduit.getCodeCategorie())
        );
        verify(categorieProduitRepository, times(1)).save(Mockito.any(CategorieProduit.class));
    }
    @Test
    void retrieveAllCategorieProduits() {
        Mockito.when(categorieProduitRepository.findAll()).thenReturn(categorieProduits);
        List<CategorieProduit> categorieProduits1 = categorieProduitService.retrieveAllCategorieProduits();
        assertEquals(categorieProduits1.size(), categorieProduits.size());
        verify(categorieProduitRepository, times(1)).findAll();
    }
}
