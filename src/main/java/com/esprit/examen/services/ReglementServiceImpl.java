package com.esprit.examen.services;

import com.esprit.examen.entities.Reglement;
import com.esprit.examen.repositories.FactureRepository;
import com.esprit.examen.repositories.ReglementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReglementServiceImpl implements IReglementService {

    @Autowired
    FactureRepository factureRepository;
    @Autowired
    ReglementRepository reglementRepository;

    @Override
    public List<Reglement> retrieveAllReglements() {
        return (List<Reglement>) reglementRepository.findAll();
    }

    @Override
    public Reglement addReglement(Reglement r) {
        return reglementRepository.save(r);

    }

    @Override
    public Reglement retrieveReglement(Long id) {
        return reglementRepository.findById(id).orElse(null);


    }

    @Override
    public List<Reglement> retrieveReglementByFacture(Long idFacture) {
        return reglementRepository.retrieveReglementByFacture(idFacture);


    }

    @Override
    public float getChiffreAffaireEntreDeuxDate(Date startDate, Date endDate) {
        return reglementRepository.getChiffreAffaireEntreDeuxDate(startDate, endDate);
    }

}
