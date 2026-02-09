package cabinetmedical.controller;

import cabinetmedical.model.Pacient;
import cabinetmedical.repository.IPacientRepository;
import cabinetmedical.repository.SqlPacientRepository;
import cabinetmedical.util.ValidatorUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CabinetService {
    private final IPacientRepository repository;

    public CabinetService() {
        this.repository = new SqlPacientRepository();
    }

    public String adaugaPacient(Pacient p) {
        String eroare = ValidatorUtils.getValidationErrors(p.getNume(), p.getCnp(), p.getDiagnostic());

        if (eroare == null) {
            repository.save(p);
            return "Succes: Pacientul a fost înregistrat!";
        }
        return eroare;
    }

    public List<Pacient> obtineTotiPacientii() {
        return repository.findAll();
    }

    public void actualizeazaPacient(Pacient p) {
        repository.update(p);
    }

    public void stergePacient(int id) {
        repository.delete(id);
    }

    public List<Pacient> cautaPacienti(String text) {
        if (text == null || text.trim().isEmpty()) {
            return repository.findAll();
        }
        return repository.searchByNameOrCnp(text.trim());
    }

    public void exportaRaport(Pacient p) {
        String numeFisier = "Raport_" + p.getNume() + "_" + p.getPrenume() + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(numeFisier))) {
            writer.println("       RAPORT MEDICAL - CABINET MEDICAL       ");
            writer.println("PACIENT: " + p.getNume().toUpperCase() + " " + p.getPrenume());
            writer.println("CNP: " + p.getCnp());
            writer.println("DATA CONSULTATIEI: " + p.getDataOraProgramarii());
            writer.println("DIAGNOSTIC: " + p.getDiagnostic());
            writer.println("SIMPTOME: " + (p.getSimptome() != null ? p.getSimptome() : "Nespecificat"));
            writer.println("ALERGII: " + (p.getAlergii() != null ? p.getAlergii() : "Nu se cunosc"));
            writer.println("TRATAMENT RECOMANDAT: " + (p.getTratament() != null ? p.getTratament() : "Nespecificat"));
            writer.println("OBSERVATII FINALE:");
            writer.println(p.getObservatii() != null && !p.getObservatii().isEmpty() ? p.getObservatii() : "Fara observatii suplimentare.");
            writer.println("Generat la data: " + java.time.LocalDateTime.now());

            System.out.println("Raport generat cu succes: " + numeFisier);
        } catch (IOException e) {
            System.err.println("Eroare la exportul raportului: " + e.getMessage());
        }
    }
}