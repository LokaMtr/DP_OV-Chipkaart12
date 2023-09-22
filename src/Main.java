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

        // Maak een ReizigerDAO instantie en geef deze door aan testAdresDAO
        ReizigerDAO reizigerDAO = new ReizigerDAOPsql(connection);
        AdresDAO adao = new AdresDAOPsql(connection);
        testAdresDAO(adao, reizigerDAO);

        OVChipkaartDAO odao = new OVChipkaartDAOPsql(connection);
        testOVChipkaartDAO(odao, reizigerDAO);

//        printReizigersEnAdressen(connection);

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

    private static void testAdresDAO(AdresDAO adresDAO, ReizigerDAO reizigerDAO) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");


        // Haal alle adressen op uit de database
        List<Adres> adressen = adresDAO.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres adres : adressen) {
            System.out.println(adres);
        }
        System.out.println();

        // Maak een nieuwe Reiziger aan om te testen met findByReiziger
        String gbdatum = "1981-03-14";
        Reiziger testReiziger = new Reiziger(6, "T", "van", "Tester", java.sql.Date.valueOf(gbdatum));
        reizigerDAO.voegReizigerToe(testReiziger);


        // Maak een nieuw adres aan en persisteer deze in de database
        Adres nieuwAdres = new Adres(6, "12345", "123", "Teststraat", "Teststad", 6);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adresDAO.save(nieuwAdres);
        adressen = adresDAO.findAll();
        System.out.println(adressen.size() + " adressen\n");

        // Test updateAdres
        System.out.println("[Test] Test updateAdres:");
        nieuwAdres.setPostcode("54321");
        adresDAO.update(nieuwAdres);
        Adres gewijzigdAdres = adresDAO.findById(nieuwAdres.getAdres_id());
        System.out.println("Na update: " + gewijzigdAdres);

        // Test findByReiziger
        System.out.println("\n[Test] Test findByReiziger:");
        List<Adres> adressenVanReiziger = adresDAO.findByReiziger(testReiziger);
        for (Adres adres : adressenVanReiziger) {
            System.out.println("Adres van TestReiziger: " + adres);
        }


        // Test deleteAdres
        System.out.println("\n[Test] Test deleteAdres:");
        for (Adres adres : adressenVanReiziger) {
            adresDAO.delete(adres);
        }
        adresDAO.delete(nieuwAdres);
        adressen = adresDAO.findAll();
        System.out.println("Na verwijderen van adres: " + adressen.size() + " adressen");


        // Verwijder de testReiziger om de database normaal te maken
        reizigerDAO.verwijderReiziger(testReiziger.getReizigerId1());
//        System.out.println("\nReizigers plus adres: ");
    }


//    private static void printReizigersEnAdressen(Connection connection) throws SQLException {
//        String query = "SELECT r.reiziger_id, r.voorletters, r.tussenvoegsel, r.achternaam, r.geboortedatum, a.adres_id, a.postcode, a.huisnummer " +
//                "FROM reiziger r " +
//                "LEFT JOIN adres a ON r.reiziger_id = a.reiziger_id";
//
//        PreparedStatement preparedStatement = connection.prepareStatement(query);
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        while (resultSet.next()) {
//            int reizigerId = resultSet.getInt("reiziger_id");
//            String voorletters = resultSet.getString("voorletters");
//            String tussenvoegsel = resultSet.getString("tussenvoegsel");
//            String achternaam = resultSet.getString("achternaam");
//            String geboortedatum = resultSet.getString("geboortedatum");
//
//            int adresId = resultSet.getInt("adres_id");
//            String postcode = resultSet.getString("postcode");
//            String huisnummer = resultSet.getString("huisnummer");
//
//            System.out.println("Reiziger {#" + reizigerId + " " + formatName(voorletters, tussenvoegsel, achternaam) + ", geb. " + geboortedatum + ", Adres {#" + adresId + " " + postcode + "-" + huisnummer + "}}");
//        }
//
//        resultSet.close();
//        preparedStatement.close();
//    }
//
    // Test alle CRUD-operaties voor OVChipkaart
    private static void testOVChipkaartDAO(OVChipkaartDAO odao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test OVChipkaartDAO -------------");

        // Haal alle OVChipkaarten op uit de database
        List<OVChipkaart> ovChipkaarten = odao.getAllOVChipkaarten();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende OVChipkaarten:");
        for (OVChipkaart ov : ovChipkaarten) {
            System.out.println(ov);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger reiziger = new Reiziger(88, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        rdao.voegReizigerToe(reiziger);

        int reizigerId = 88; // Bijvoorbeeld
        // Maak een nieuwe OVChipkaart aan en persisteer deze in de database
        OVChipkaart ovChipkaart = new OVChipkaart(12345, new Date(System.currentTimeMillis()), 2, 25.0, reizigerId);
        System.out.print("[Test] Eerst " + ovChipkaarten.size() + " OVChipkaarten, na OVChipkaartDAO.save() ");
        odao.save(ovChipkaart);
        ovChipkaarten = odao.getAllOVChipkaarten();
        System.out.println(ovChipkaarten.size() + " OVChipkaarten\n");

        // Test updateOVChipkaart
        System.out.println("[Test] Test updateOVChipkaart:");
        ovChipkaart.setKlasse(1);
        odao.update(ovChipkaart);
        OVChipkaart gewijzigdeOVChipkaart = odao.getOVChipkaartByKaartnummer(ovChipkaart.getKaartnummer());
        System.out.println("Na update: " + gewijzigdeOVChipkaart);

        // Test verwijderOVChipkaart
        System.out.println("\n[Test] Test verwijderOVChipkaart:");
        odao.delete(ovChipkaart);
        ovChipkaarten = odao.getAllOVChipkaarten();
        System.out.println("Na verwijderen van OVChipkaart: " + ovChipkaarten.size() + " OVChipkaarten");

        // Verwijder de testreiziger om de database normaal te maken
        rdao.verwijderReiziger(reiziger.getReizigerId1());
    }



}
