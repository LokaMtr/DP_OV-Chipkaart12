import java.sql.*;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/ovchip";
        String user = "postgres";
        String password = DatabaseProperties.getPassword();

        Connection connection = DriverManager.getConnection(url, user, password);

        String query = "SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        System.out.println("Alle reizigers:");
        int count = 0;
        while (resultSet.next()) {
            count++;
            int reizigerId = resultSet.getInt("reiziger_id");
            String voorletters = resultSet.getString("voorletters");
            String tussenvoegsel = resultSet.getString("tussenvoegsel");
            String achternaam = resultSet.getString("achternaam");
            String geboortedatum = resultSet.getString("geboortedatum");

            System.out.println("#" + reizigerId + ": " + formatName(voorletters, tussenvoegsel, achternaam) + " (" + geboortedatum + ")");
        }

        ReizigerDAO rdao = new ReizigerDAOPsql(connection);
        testReizigerDAO(rdao);

        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.getAllReizigers();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sam = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.voegReizigerToe(sam);
        reizigers = rdao.getAllReizigers();
        System.out.println(reizigers.size() + " reizigers\n");

        // Test updateReiziger
        System.out.println("[Test] Test updateReiziger:");
        rdao.updateReiziger(sam);
        Reiziger sietskeNaUpdate = rdao.getReizigerById(77);
        System.out.println("Na update: " + sietskeNaUpdate);

        // Test verwijderReiziger
        System.out.println("\n[Test] Test verwijderReiziger:");
        rdao.verwijderReiziger(77);
        reizigers = rdao.getAllReizigers();
        System.out.println("Na verwijderen van sam: " + reizigers.size() + " reizigers");
    }
    private static String formatName(String voorletters, String tussenvoegsel, String achternaam) {
        StringBuilder formattedName = new StringBuilder();

        formattedName.append(voorletters).append(" ");

        if (tussenvoegsel != null && !tussenvoegsel.isEmpty()) {
            formattedName.append(tussenvoegsel).append(" ");
        }

        formattedName.append(achternaam);

        return formattedName.toString();
    }
}
