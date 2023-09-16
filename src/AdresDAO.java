import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {
    void save(Adres adres);
    void update(Adres adres);
    void delete(Adres adres);
    Adres findById(int id);
    List<Adres> findByReiziger(Reiziger reiziger);
    List<Adres> findAll();

    Connection getConnection() throws SQLException;
}
