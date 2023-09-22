import java.util.List;

public interface OVChipkaartDAO {
    void createOVChipkaart(OVChipkaart ovChipkaart);
    OVChipkaart findOVChipkaartByKaartnummer(int kaartnummer);
    List<OVChipkaart> findOVChipkaartenByReizigerId(int reizigerId);
    void updateOVChipkaart(OVChipkaart ovChipkaart);
    void deleteOVChipkaart(int kaartnummer);

    // Create (Toevoegen)
    void save(OVChipkaart ovChipkaart);

    // Read (Lezen)
    OVChipkaart getOVChipkaartByKaartnummer(int kaartnummer);

    List<OVChipkaart> getAllOVChipkaarten();

    // Update (Bijwerken)
    void update(OVChipkaart ovChipkaart);

    // Delete (Verwijderen)
    void delete(OVChipkaart ovChipkaart);
}
