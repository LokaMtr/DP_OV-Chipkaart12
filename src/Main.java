import java.sql.*;


public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/ovchip";
        String user = "postgres";
        String password = DatabaseProperties.getPassword();

        try {
            Class.forName("org.postgresql.Driver");
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

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Verbinding mislukt! Fout: " + e.getMessage());
        }
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
