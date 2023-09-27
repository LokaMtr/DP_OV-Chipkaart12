import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private final Connection connection;

    public AdresDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() throws SQLException {
        // Hier maak je een databaseverbinding en retourneer je deze
        String url = "jdbc:postgresql://localhost:5432/ovchip"; // Vervang met je eigen database-URL
        String user = "postgres"; // Vervang met je eigen gebruikersnaam
        String password = "0684284771"; // Vervang met je eigen wachtwoord

        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void save(Adres adres) throws SQLException {
        String query = "INSERT INTO adres (postcode, huisnummer, straat, woonplaats, reiziger_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, adres.getPostcode());
        preparedStatement.setString(2, adres.getHuisnummer());
        preparedStatement.setString(3, adres.getStraat());
        preparedStatement.setString(4, adres.getWoonplaats());
        preparedStatement.setInt(5,adres.getReizigerId());

        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

    @Override
    public void update(Adres adres) throws SQLException {
        String query = "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ? " +
                "WHERE adres_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, adres.getPostcode());
        preparedStatement.setString(2, adres.getHuisnummer());
        preparedStatement.setString(3, adres.getStraat());
        preparedStatement.setString(4, adres.getWoonplaats());
        preparedStatement.setInt(5, adres.getAdres_id());

        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

    @Override
    public void delete(Adres adres) throws SQLException {
        String query = "DELETE FROM adres WHERE adres_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, adres.getAdres_id());

        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

    @Override
    public Adres findById(int id) throws SQLException {
        String query = "SELECT postcode, huisnummer, straat, woonplaats, reiziger_id " +
                "FROM adres WHERE adres_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String postcode = resultSet.getString("postcode");
            String huisnummer = resultSet.getString("huisnummer");
            String straat = resultSet.getString("straat");
            String woonplaats = resultSet.getString("woonplaats");
            int reizigerId = resultSet.getInt("reiziger_id");

            ReizigerDAO reizigerDAO = new ReizigerDAOPsql(connection);
            Reiziger reiziger = reizigerDAO.getReizigerById(reizigerId);

            return new Adres(id, postcode, huisnummer, straat, woonplaats, reizigerId);
        }

        resultSet.close();
        preparedStatement.close();


        return null;
    }

    @Override
    public List<Adres> findByReiziger(Reiziger reiziger) throws SQLException {
        List<Adres> adressen = new ArrayList<>();

        String query = "SELECT adres_id, postcode, huisnummer, straat, woonplaats FROM adres " +
                "WHERE reiziger_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, reiziger.getReizigerId1());
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int adresId = resultSet.getInt("adres_id");
            String postcode = resultSet.getString("postcode");
            String huisnummer = resultSet.getString("huisnummer");
            String straat = resultSet.getString("straat");
            String woonplaats = resultSet.getString("woonplaats");

            // Geen reiziger_id nodig hier, omdat deze niet in de tabel zit
            Adres adres = new Adres(adresId, postcode, huisnummer, straat, woonplaats, reiziger.getReizigerId1());
            adressen.add(adres);
        }

        resultSet.close();
        preparedStatement.close();


        return adressen;
    }


    @Override
    public List<Adres> findAll() throws SQLException {
        List<Adres> adressen = new ArrayList<>();

        String query = "SELECT adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id FROM adres";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int adresId = resultSet.getInt("adres_id");
            String postcode = resultSet.getString("postcode");
            String huisnummer = resultSet.getString("huisnummer");
            String straat = resultSet.getString("straat");
            String woonplaats = resultSet.getString("woonplaats");
            int reizigerId = resultSet.getInt("reiziger_id");

            ReizigerDAO reizigerDAO = new ReizigerDAOPsql(connection);
            Reiziger reiziger = reizigerDAO.getReizigerById(reizigerId);

            Adres adres = new Adres(adresId, postcode, huisnummer, straat, woonplaats, reizigerId);
            adressen.add(adres);
        }

        resultSet.close();
        preparedStatement.close();

        return adressen;
    }
}
