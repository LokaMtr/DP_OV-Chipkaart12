import java.util.List;

public interface ReizigerDAO {

    // Create (Toevoegen) - Voegt een nieuwe Reiziger toe aan de database
    void voegReizigerToe(Reiziger reiziger);

    // Read (Lezen) - Haalt een Reiziger op basis van hun reiziger-id
    Reiziger getReizigerById(int reizigerId);

    // Read (Lezen) - Haalt alle Reizigers op uit de database
    List<Reiziger> getAllReizigers();

    // Update (Bijwerken) - Werkt een bestaande Reiziger bij
    void updateReiziger(Reiziger reiziger);

    // Delete (Verwijderen) - Verwijdert een Reiziger uit de database op basis van hun reiziger-id
    void verwijderReiziger(int reizigerId);
}
