package com.esprit.examen.services;

import com.esprit.examen.entities.Produit;
import com.esprit.examen.entities.Stock;
import com.esprit.examen.repositories.ProduitRepository;
import com.esprit.examen.repositories.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ProduitServiceImplMockTest {
    @Mock
    ProduitRepository produitRepository;
    @Mock
    StockRepository stockRepository;
    @InjectMocks
    ProduitServiceImpl produitService;
    @InjectMocks
    StockServiceImpl stockService;
    Stock stock = Stock.builder().libelleStock("stock 1").qte(10).qteMin(5).build();
    Produit produit = Produit.builder().codeProduit("code1").libelleProduit("Business").prix(10).dateCreation(new Date()).build();

    @Test
    public void assignProduitToStock() {
        Mockito.when(produitRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(produit));
        Mockito.when(stockRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(stock));
        Mockito.when(produitRepository.save(Mockito.any(Produit.class))).thenReturn(produit);
        produitService.assignProduitToStock(1L, 2L);
        Produit produit1 = produitService.retrieveProduit(1L);
        assertEquals(produit1.getStock(), stock);
        verify(produitRepository, times(1)).save(Mockito.any(Produit.class));
        verify(produitRepository, times(2)).findById(Mockito.anyLong());
    }
}
