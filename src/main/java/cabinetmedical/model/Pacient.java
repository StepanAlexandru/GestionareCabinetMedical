package cabinetmedical.model;

import java.time.LocalDateTime;

public class Pacient {
    private int id;
    private String nume;
    private String prenume;
    private String cnp;
    private String diagnostic;
    private String simptome;
    private String alergii;
    private String tratament;
    private LocalDateTime dataOraProgramarii;
    private String observatii;

    public Pacient(int id, String nume, String prenume, String cnp, String diagnostic,
                   String simptome, String alergii, String tratament,
                   LocalDateTime dataOraProgramarii, String observatii) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.diagnostic = diagnostic;
        this.simptome = simptome;
        this.alergii = alergii;
        this.tratament = tratament;
        this.dataOraProgramarii = dataOraProgramarii;
        this.observatii = observatii;
    }

    public Pacient(String nume, String prenume, String cnp, String diagnostic,
                   String simptome, String alergii, String tratament,
                   LocalDateTime dataOraProgramarii, String observatii) {
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.diagnostic = diagnostic;
        this.simptome = simptome;
        this.alergii = alergii;
        this.tratament = tratament;
        this.dataOraProgramarii = dataOraProgramarii;
        this.observatii = observatii;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public String getPrenume() { return prenume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }

    public String getCnp() { return cnp; }
    public void setCnp(String cnp) { this.cnp = cnp; }

    public String getDiagnostic() { return diagnostic; }
    public void setDiagnostic(String diagnostic) { this.diagnostic = diagnostic; }

    public String getSimptome() { return simptome; }
    public void setSimptome(String simptome) { this.simptome = simptome; }

    public String getAlergii() { return alergii; }
    public void setAlergii(String alergii) { this.alergii = alergii; }

    public String getTratament() { return tratament; }
    public void setTratament(String tratament) { this.tratament = tratament; }

    public LocalDateTime getDataOraProgramarii() { return dataOraProgramarii; }
    public void setDataOraProgramarii(LocalDateTime dataOraProgramarii) { this.dataOraProgramarii = dataOraProgramarii; }

    public String getObservatii() { return observatii; }
    public void setObservatii(String observatii) { this.observatii = observatii; }

    public String getNumeComplet() {
        return nume + " " + prenume;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", diagnostic='" + diagnostic + '\'' +
                ", alergii='" + alergii + '\'' +
                '}';
    }
}