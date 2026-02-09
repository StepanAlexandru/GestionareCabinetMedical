package cabinetmedical.view;

import cabinetmedical.controller.CabinetService;
import cabinetmedical.model.Pacient;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JTextField txtCautare;
    private JTable pacientTable;
    private JButton btnAdauga;
    private JButton btnEditeaza;
    private JButton btnSterge;
    private JButton btnRaport;
    private JButton detaliiButton;

    private final CabinetService service;

    public MainFrame() {
        this.service = new CabinetService();

        setTitle("Sistem Gestiune Cabinet Medical");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(700, 600);
        setLocationRelativeTo(null);

        refreshTable();

        txtCautare.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { executaFiltrare(); }
            @Override
            public void removeUpdate(DocumentEvent e) { executaFiltrare(); }
            @Override
            public void changedUpdate(DocumentEvent e) { executaFiltrare(); }

            private void executaFiltrare() {
                String query = txtCautare.getText().trim();
                if (!query.isEmpty()) {
                    populeazaTabel(service.cautaPacienti(query));
                } else {
                    refreshTable();
                }
            }
        });


        detaliiButton.addActionListener(e -> deschideDetalii());


        pacientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Dublu click
                    deschideDetalii();
                }
            }
        });



        btnAdauga.addActionListener(e -> {
            AddPacientDialog dialog = new AddPacientDialog(this);
            dialog.setVisible(true);
            txtCautare.setText("");
            refreshTable();
        });

        btnEditeaza.addActionListener(e -> {
            int selectedRow = pacientTable.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) pacientTable.getValueAt(selectedRow, 0);
                Pacient p = gasestePacientDupaId(id);

                if (p != null) {
                    AddPacientDialog dialog = new AddPacientDialog(this, p);
                    dialog.setVisible(true);
                    refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selectați un pacient pentru editare!");
            }
        });

        btnSterge.addActionListener(e -> {
            int selectedRow = pacientTable.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) pacientTable.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Sigur ștergeți pacientul?", "Confirmare", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    service.stergePacient(id);
                    refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selectați un pacient pentru ștergere!");
            }
        });

        btnRaport.addActionListener(e -> {
            int selectedRow = pacientTable.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) pacientTable.getValueAt(selectedRow, 0);
                Pacient p = gasestePacientDupaId(id);

                if (p != null) {
                    service.exportaRaport(p);
                    JOptionPane.showMessageDialog(this, "Raport generat cu succes pentru " + p.getNume() + " " + p.getPrenume());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selectați un pacient pentru a genera raportul!");
            }
        });
    }


    private void deschideDetalii() {
        int selectedRow = pacientTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) pacientTable.getValueAt(selectedRow, 0);
            Pacient p = gasestePacientDupaId(id);
            if (p != null) {
                PacientDetaliiDialog dialog = new PacientDetaliiDialog(this, p);
                dialog.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selectați un pacient pentru a vedea detaliile!");
        }
    }

    private Pacient gasestePacientDupaId(int id) {
        return service.obtineTotiPacientii().stream()
                .filter(pac -> pac.getId() == id)
                .findFirst().orElse(null);
    }

    private void refreshTable() {
        populeazaTabel(service.obtineTotiPacientii());
    }

    private void populeazaTabel(List<Pacient> lista) {
        String[] coloane = {"ID", "Nume", "Prenume", "CNP", "Diagnostic", "Data Programarii"};

        DefaultTableModel model = new DefaultTableModel(coloane, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Pacient p : lista) {
            Object[] rand = {
                    p.getId(),
                    p.getNume(),
                    p.getPrenume(),
                    p.getCnp(),
                    p.getDiagnostic(),
                    p.getDataOraProgramarii()
            };
            model.addRow(rand);
        }
        pacientTable.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}