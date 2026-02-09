package cabinetmedical.repository;

import cabinetmedical.model.Pacient;
import java.util.List;

public interface IPacientRepository {

    // Adauga un pacient nou in baza de date
    void save(Pacient pacient);

    // Returneaza lista completa a pacientilor
    List<Pacient> findAll();

    // Actualizeaza informatiile unui pacient existent (identificat prin ID)
    void update(Pacient pacient);

    // Sterge un pacient din baza de date folosind ID-ul unic
    void delete(int id);

    // Filtreaza pacientii dupa nume, prenume sau CNP
    List<Pacient> searchByNameOrCnp(String query);
}