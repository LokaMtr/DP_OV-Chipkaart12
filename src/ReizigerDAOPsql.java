import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection connection;

    // Constructor met dependency injection voor de databaseverbinding
    public ReizigerDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void voegReizigerToe(Reiziger reiziger) throws SQLException {
        String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, reiziger.getReizigerId1());
        preparedStatement.setString(2, reiziger.getVoorletters());
        preparedStatement.setString(3, reiziger.getTussenvoegsel());
        preparedStatement.setString(4, reiziger.getAchternaam());
        preparedStatement.setDate(5, reiziger.getGeboortedatum());

        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

    @Override
    public Reiziger getReizigerById(int reizigerId) throws SQLException{
        String query = "SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum " +
                "FROM reiziger WHERE reiziger_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, reizigerId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String voorletters = resultSet.getString("voorletters");
            String tussenvoegsel = resultSet.getString("tussenvoegsel");
            String achternaam = resultSet.getString("achternaam");
            Date geboortedatum = resultSet.getDate("geboortedatum");

            return new Reiziger(reizigerId, voorletters, tussenvoegsel, achternaam, geboortedatum);
        }

        resultSet.close();
        preparedStatement.close();

        return null;
    }

    @Override
    public List<Reiziger> getAllReizigers() throws SQLException {
        List<Reiziger> reizigers = new ArrayList<>();

        String query = "SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int reizigerId = resultSet.getInt("reiziger_id");
            String voorletters = resultSet.getString("voorletters");
            String tussenvoegsel = resultSet.getString("tussenvoegsel");
            String achternaam = resultSet.getString("achternaam");
            Date geboortedatum = resultSet.getDate("geboortedatum");

            Reiziger reiziger = new Reiziger(reizigerId, voorletters, tussenvoegsel, achternaam, geboortedatum);
            reizigers.add(reiziger);
        }

        resultSet.close();
        preparedStatement.close();

        return reizigers;
    }

    @Override
    public void updateReiziger(Reiziger reiziger) throws SQLException{
        String query = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, " +
                "geboortedatum = ? WHERE reiziger_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, reiziger.getVoorletters());
        preparedStatement.setString(2, reiziger.getTussenvoegsel());
        preparedStatement.setString(3, reiziger.getAchternaam());
        preparedStatement.setDate(4, reiziger.getGeboortedatum());
        preparedStatement.setInt(5, reiziger.getReizigerId1());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


    @Override
    public void verwijderReiziger(int reizigerId) throws SQLException {
        String query = "DELETE FROM reiziger WHERE reiziger_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, reizigerId);

        preparedStatement.executeUpdate();
        preparedStatement.close();

    }
}
