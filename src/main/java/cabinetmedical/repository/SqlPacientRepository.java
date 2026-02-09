package cabinetmedical.repository;

import cabinetmedical.model.Pacient;
import cabinetmedical.util.CryptoUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlPacientRepository implements IPacientRepository {

    @Override
    public void save(Pacient p) {
        String sql = "INSERT INTO pacienti (nume, prenume, cnp, diagnostic, simptome, alergii, tratament, data_programare, observatii) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.getNume());
            pstmt.setString(2, p.getPrenume());
            pstmt.setString(3, CryptoUtils.encrypt(p.getCnp()));
            pstmt.setString(4, p.getDiagnostic());
            pstmt.setString(5, p.getSimptome());
            pstmt.setString(6, p.getAlergii());
            pstmt.setString(7, p.getTratament());
            pstmt.setTimestamp(8, Timestamp.valueOf(p.getDataOraProgramarii()));
            pstmt.setString(9, p.getObservatii());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pacient> findAll() {
        List<Pacient> pacienti = new ArrayList<>();
        String sql = "SELECT * FROM pacienti";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pacienti.add(extractPacient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacienti;
    }

    @Override
    public void update(Pacient p) {
        String sql = "UPDATE pacienti SET nume=?, prenume=?, cnp=?, diagnostic=?, simptome=?, alergii=?, tratament=?, data_programare=?, observatii=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.getNume());
            pstmt.setString(2, p.getPrenume());
            pstmt.setString(3, CryptoUtils.encrypt(p.getCnp()));
            pstmt.setString(4, p.getDiagnostic());
            pstmt.setString(5, p.getSimptome());
            pstmt.setString(6, p.getAlergii());
            pstmt.setString(7, p.getTratament());
            pstmt.setTimestamp(8, Timestamp.valueOf(p.getDataOraProgramarii()));
            pstmt.setString(9, p.getObservatii());
            pstmt.setInt(10, p.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM pacienti WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pacient> searchByNameOrCnp(String text) {
        List<Pacient> rezultate = new ArrayList<>();

        String sql = "SELECT * FROM pacienti WHERE nume LIKE ? OR prenume LIKE ? OR cnp = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String queryLike = "%" + text + "%";
            pstmt.setString(1, queryLike);
            pstmt.setString(2, queryLike);
            pstmt.setString(3, CryptoUtils.encrypt(text));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    rezultate.add(extractPacient(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultate;
    }

    private Pacient extractPacient(ResultSet rs) throws SQLException {
        return new Pacient(
                rs.getInt("id"),
                rs.getString("nume"),
                rs.getString("prenume"),
                CryptoUtils.decrypt(rs.getString("cnp")),
                rs.getString("diagnostic"),
                rs.getString("simptome"),
                rs.getString("alergii"),
                rs.getString("tratament"),
                rs.getTimestamp("data_programare").toLocalDateTime(),
                rs.getString("observatii")
        );
    }
}