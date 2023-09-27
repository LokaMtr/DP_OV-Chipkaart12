import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection connection;

    // Constructor met dependency injection voor de databaseverbinding
    public OVChipkaartDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        String query = "INSERT INTO ov_chipkaart (kaartnummer, geldig_tot, klasse, saldo, reiziger_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, ovChipkaart.getKaartnummer());
        preparedStatement.setDate(2, ovChipkaart.getGeldigTot());
        preparedStatement.setInt(3, ovChipkaart.getKlasse());
        preparedStatement.setDouble(4, ovChipkaart.getSaldo());
        preparedStatement.setInt(5, ovChipkaart.getReizigerId());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public OVChipkaart findOVChipkaartByKaartnummer(int kaartnummer) throws SQLException{
        String query = "SELECT kaartnummer, geldig_tot, klasse, saldo, reiziger_id FROM ov_chipkaart WHERE kaartnummer = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, kaartnummer);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Date geldigTot = resultSet.getDate("geldig_tot");
            int klasse = resultSet.getInt("klasse");
            double saldo = resultSet.getDouble("saldo");
            int reizigerId = resultSet.getInt("reiziger_id");

            ReizigerDAO reizigerDAO = new ReizigerDAOPsql(connection);
            Reiziger reiziger = reizigerDAO.getReizigerById(reizigerId);

            return new OVChipkaart(kaartnummer, geldigTot, klasse, saldo, reizigerId);
        }

        resultSet.close();
        preparedStatement.close();


        return null;
    }

    @Override
    public List<OVChipkaart> findOVChipkaartenByReizigerId(int reizigerId) throws SQLException {
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();

        try {
            String query = "SELECT kaart_nummer, geldig_tot, klasse, saldo FROM ov_chipkaart WHERE reiziger_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reizigerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int kaartnummer = resultSet.getInt("kaartnummer");
                Date geldigTot = resultSet.getDate("geldig_tot");
                int klasse = resultSet.getInt("klasse");
                double saldo = resultSet.getDouble("saldo");

                OVChipkaart ovChipkaart = new OVChipkaart(kaartnummer, geldigTot, klasse, saldo, reizigerId);
                ovChipkaarten.add(ovChipkaart);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ovChipkaarten;
    }
    public List<OVChipkaart> getOVChipkaartenByReiziger(Reiziger reiziger) {
        List<OVChipkaart> gevondenOVChipkaarten = new ArrayList<>();

        try {
            // Query om OV-chipkaarten van de reiziger op te halen
            String query = "SELECT kaart_nummer, geldig_tot, klasse, saldo FROM ov_chipkaart WHERE reiziger_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reiziger.getReizigerId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int kaartnummer = resultSet.getInt("kaart_nummer");
                Date geldigTot = resultSet.getDate("geldig_tot");
                int klasse = resultSet.getInt("klasse");
                double saldo = resultSet.getDouble("saldo");

                OVChipkaart ovChipkaart = new OVChipkaart(kaartnummer, geldigTot, klasse, saldo, reiziger.getReizigerId());
                gevondenOVChipkaarten.add(ovChipkaart);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gevondenOVChipkaarten;
    }

    @Override
    public void updateOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        String query = "UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ? WHERE kaartnummer = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, ovChipkaart.getGeldigTot());
        preparedStatement.setInt(2, ovChipkaart.getKlasse());
        preparedStatement.setDouble(3, ovChipkaart.getSaldo());
        preparedStatement.setInt(4, ovChipkaart.getKaartnummer());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public void deleteOVChipkaart(int kaartnummer) throws SQLException {
        String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, kaartnummer);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public void save(OVChipkaart ovChipkaart) throws SQLException {
        String query = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, ovChipkaart.getKaartNummer());
        preparedStatement.setDate(2, ovChipkaart.getGeldigTot());
        preparedStatement.setInt(3, ovChipkaart.getKlasse());
        preparedStatement.setDouble(4, ovChipkaart.getSaldo());
        preparedStatement.setInt(5, ovChipkaart.getReizigerId());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public OVChipkaart getOVChipkaartByKaartnummer(int kaartnummer) throws SQLException {
        String query = "SELECT kaart_nummer, geldig_tot, klasse, saldo, reiziger_id " +
                "FROM ov_chipkaart WHERE kaart_nummer = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, kaartnummer);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Date geldigTot = resultSet.getDate("geldig_tot");
            int klasse = resultSet.getInt("klasse");
            double saldo = resultSet.getDouble("saldo");
            int reizigerId = resultSet.getInt("reiziger_id");

            ReizigerDAO reizigerDAO = new ReizigerDAOPsql(connection);
            Reiziger reiziger = reizigerDAO.getReizigerById(reizigerId);

            return new OVChipkaart(kaartnummer, geldigTot, klasse, saldo, reizigerId);
        }

        resultSet.close();
        preparedStatement.close();
        return null;
    }

    @Override
    public List<OVChipkaart> getAllOVChipkaarten() throws SQLException {
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        String query = "SELECT kaart_nummer, geldig_tot, klasse, saldo, reiziger_id FROM ov_chipkaart";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int kaartnummer = resultSet.getInt("kaart_nummer");
            Date geldigTot = resultSet.getDate("geldig_tot");
            int klasse = resultSet.getInt("klasse");
            double saldo = resultSet.getDouble("saldo");
            int reizigerId = resultSet.getInt("reiziger_id");

            ReizigerDAO reizigerDAO = new ReizigerDAOPsql(connection);
            Reiziger reiziger = reizigerDAO.getReizigerById(reizigerId);

            OVChipkaart ovChipkaart = new OVChipkaart(kaartnummer, geldigTot, klasse, saldo, reizigerId);
            ovChipkaarten.add(ovChipkaart);
        }

        resultSet.close();
        preparedStatement.close();
        return ovChipkaarten;
    }

    @Override
    public void update(OVChipkaart ovChipkaart) throws SQLException {
        String query = "UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? " +
                "WHERE kaart_nummer = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, ovChipkaart.getGeldigTot());
        preparedStatement.setInt(2, ovChipkaart.getKlasse());
        preparedStatement.setDouble(3, ovChipkaart.getSaldo());
        preparedStatement.setInt(4, ovChipkaart.getReizigerId());
        preparedStatement.setInt(5, ovChipkaart.getKaartNummer());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public void delete(OVChipkaart ovChipkaart) throws SQLException {
        String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, ovChipkaart.getKaartNummer());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

}

