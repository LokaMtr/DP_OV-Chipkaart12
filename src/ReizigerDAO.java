import java.util.List;

public interface ReizigerDAO {

    // Create (Toevoegen)
    void voegReizigerToe(Reiziger reiziger);

    // Read (Lezen)
    Reiziger getReizigerById(int reizigerId);

    // Read (Lezen)
    List<Reiziger> getAllReizigers();

    // Update (Bijwerken)
    void updateReiziger(Reiziger reiziger);

    // Delete (Verwijderen)
    void verwijderReiziger(int reizigerId);
}
