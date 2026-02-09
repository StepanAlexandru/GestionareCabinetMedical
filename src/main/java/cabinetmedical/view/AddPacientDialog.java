package cabinetmedical.view;

import cabinetmedical.controller.CabinetService;
import cabinetmedical.model.Pacient;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.text.ParseException;

public class AddPacientDialog extends JDialog {
    private JPanel contentPane;
    private JTextField txtNume;
    private JTextField txtPrenume;
    private JTextField txtCNP;
    private JTextField txtDiagnostic;
    private JTextArea txtObservatii;
    private JButton btnSalveaza;
    private JButton btnAnuleaza;
    private JFormattedTextField txtData;
    private JTextField txtSimptome;
    private JTextField txtAlergii;
    private JTextField txtTratament;

    private final CabinetService service;
    private Pacient pacientExistent = null;

    public AddPacientDialog(JFrame parent) {
        this(parent, null);
    }

    public AddPacientDialog(JFrame parent, Pacient pacient) {
        super(parent, "Informatii Pacient", true);
        this.service = new CabinetService();
        this.pacientExistent = pacient;

        setupDateMask();
        setContentPane(contentPane);

        if (pacientExistent != null) {
            populeazaCampuri(pacientExistent);
            setTitle("Editeaza Pacient");
        } else {
            setTitle("Adauga Pacient Nou");
            txtData.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")));
        }

        pack();
        setSize(500, 600);
        setResizable(true);
        setLocationRelativeTo(parent);

        btnSalveaza.addActionListener(e -> salveaza());
        btnAnuleaza.addActionListener(e -> dispose());
    }

    private void setupDateMask() {
        try {
            MaskFormatter dateMask = new MaskFormatter("##-##-####");
            dateMask.setPlaceholderCharacter('_');
            dateMask.install(txtData);
        } catch (ParseException e) {
            System.err.println("Eroare la configurarea măștii de dată: " + e.getMessage());
        }
    }

    private void populeazaCampuri(Pacient p) {
        txtNume.setText(p.getNume());
        txtPrenume.setText(p.getPrenume());
        txtCNP.setText(p.getCnp());
        txtDiagnostic.setText(p.getDiagnostic());
        txtSimptome.setText(p.getSimptome());
        txtAlergii.setText(p.getAlergii());
        txtTratament.setText(p.getTratament());
        txtObservatii.setText(p.getObservatii());

        if (p.getDataOraProgramarii() != null) {
            txtData.setText(p.getDataOraProgramarii().format(DateTimeFormatter.ofPattern("ddMMyyyy")));
        }
    }

    private void salveaza() {
        if (txtNume.getText().trim().isEmpty() || txtPrenume.getText().trim().isEmpty() || txtCNP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Numele, Prenumele și CNP-ul sunt obligatorii!", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String rawDate = txtData.getText().trim();
        LocalDateTime dataProgramata;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate datePart = LocalDate.parse(rawDate, formatter);

            if (datePart.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Eroare: Data programării nu poate fi în trecut!", "Dată Invalidă", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dataProgramata = LocalDateTime.of(datePart, LocalTime.now().withSecond(0).withNano(0));

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Eroare: Data este incompletă sau invalidă (DD-MM-YYYY)!", "Format Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nume = txtNume.getText().trim();
        String prenume = txtPrenume.getText().trim();
        String cnp = txtCNP.getText().trim();
        String diagnostic = txtDiagnostic.getText().trim();
        String simptome = txtSimptome.getText().trim();
        String alergii = txtAlergii.getText().trim();
        String tratament = txtTratament.getText().trim();
        String observatii = txtObservatii.getText().trim();

        if (pacientExistent == null) {
            Pacient nou = new Pacient(
                    nume, prenume, cnp, diagnostic,
                    simptome, alergii, tratament,
                    dataProgramata, observatii
            );

            String mesaj = service.adaugaPacient(nou);
            JOptionPane.showMessageDialog(this, mesaj);
            if (mesaj.contains("Succes")) dispose();

        } else {
            pacientExistent.setNume(nume);
            pacientExistent.setPrenume(prenume);
            pacientExistent.setCnp(cnp);
            pacientExistent.setDiagnostic(diagnostic);
            pacientExistent.setSimptome(simptome);
            pacientExistent.setAlergii(alergii);
            pacientExistent.setTratament(tratament);
            pacientExistent.setDataOraProgramarii(dataProgramata);
            pacientExistent.setObservatii(observatii);

            service.actualizeazaPacient(pacientExistent);
            JOptionPane.showMessageDialog(this, "Pacient actualizat cu succes!");
            dispose();
        }
    }
}