import java.util.List;

public interface ProductDAO {
    void save(Product product);

    void update(Product product);

    void delete(Product product);

    Product findByProductNummer(int product_nummer);

    List<Product> findAll();

    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
}
