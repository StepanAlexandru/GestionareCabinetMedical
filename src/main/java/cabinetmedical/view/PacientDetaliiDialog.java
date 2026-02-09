package cabinetmedical.view;

import cabinetmedical.model.Pacient;
import javax.swing.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;

public class PacientDetaliiDialog extends JDialog {
    private JPanel contentPane;
    private JButton btnInchide;
    private JTextArea txtNume;
    private JTextArea txtPrenume;
    private JTextArea txtCNP;
    private JTextArea txtSimptome;
    private JTextArea txtAlergii;
    private JTextArea txtDiagnostic;
    private JTextArea txtTratament;
    private JTextArea txtObservatii;
    private JTextArea txtData;

    public PacientDetaliiDialog(JFrame parent, Pacient p) {
        super(parent, "Fisa Pacient: " + p.getNumeComplet(), true);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnInchide);


        txtNume.setText(p.getNume());
        txtPrenume.setText(p.getPrenume());
        txtCNP.setText(p.getCnp());
        txtSimptome.setText(p.getSimptome());
        txtAlergii.setText(p.getAlergii());
        txtDiagnostic.setText(p.getDiagnostic());
        txtTratament.setText(p.getTratament());
        txtObservatii.setText(p.getObservatii());

        if (p.getDataOraProgramarii() != null) {
            txtData.setText(p.getDataOraProgramarii().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        }

        configurareaVizuala();

        btnInchide.addActionListener(e -> dispose());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setSize(500, 600);
        setLocationRelativeTo(parent);
    }

    private void configurareaVizuala() {
        JTextArea[] campuri = {txtNume, txtPrenume, txtCNP, txtSimptome, txtAlergii,
                txtDiagnostic, txtTratament, txtObservatii, txtData};

        for (JTextArea area : campuri) {
            area.setEditable(false);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setFocusable(false);
            area.setOpaque(false);
        }
    }
}