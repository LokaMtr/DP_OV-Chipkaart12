import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
    private Connection connection;

    public ProductDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Product product) {
        try {
            String query = "INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, product.getProductNummer());
            preparedStatement.setString(2, product.getNaam());
            preparedStatement.setString(3, product.getBeschrijving());
            preparedStatement.setDouble(4, product.getPrijs());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Product product) {
        try {
            String query = "UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getNaam());
            preparedStatement.setString(2, product.getBeschrijving());
            preparedStatement.setDouble(3, product.getPrijs());
            preparedStatement.setInt(4, product.getProductNummer());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Product product) {
        try {
            String query = "DELETE FROM product WHERE product_nummer = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, product.getProductNummer());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product findByProductNummer(int product_nummer) {
        try {
            String query = "SELECT naam, beschrijving, prijs FROM product WHERE product_nummer = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, product_nummer);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String naam = resultSet.getString("naam");
                String beschrijving = resultSet.getString("beschrijving");
                Double prijs = resultSet.getDouble("prijs");

                return new Product(product_nummer, naam, beschrijving, prijs);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        try {
            String query = "SELECT product_nummer, naam, beschrijving, prijs FROM product";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int product_nummer = resultSet.getInt("product_nummer");
                String naam = resultSet.getString("naam");
                String beschrijving = resultSet.getString("beschrijving");
                Double prijs = resultSet.getDouble("prijs");

                Product product = new Product(product_nummer, naam, beschrijving, prijs);
                products.add(product);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        List<Product> products = new ArrayList<>();

        try {
            String query = "SELECT product.* FROM product " +
                    "JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer " +
                    "WHERE ov_chipkaart_product.kaart_nummer = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ovChipkaart.getKaartnummer());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int productNummer = resultSet.getInt("product_nummer");
                String productNaam = resultSet.getString("product_naam");
                String beschrijving = resultSet.getString("beschrijving");
                double prijs = resultSet.getDouble("prijs");

                Product product = new Product(productNummer, productNaam, beschrijving, prijs);
                products.add(product);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}
