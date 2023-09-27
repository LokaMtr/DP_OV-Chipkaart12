import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {

    // Create (Toevoegen)
    void voegReizigerToe(Reiziger reiziger) throws SQLException;

    // Read (Lezen)
    Reiziger getReizigerById(int reizigerId) throws SQLException;

    // Read (Lezen)
    List<Reiziger> getAllReizigers() throws SQLException;

    // Update (Bijwerken)
    void updateReiziger(Reiziger reiziger) throws SQLException;

    // Delete (Verwijderen)
    void verwijderReiziger(int reizigerId) throws SQLException;
}
