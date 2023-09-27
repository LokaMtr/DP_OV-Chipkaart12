import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    void createOVChipkaart(OVChipkaart ovChipkaart) throws SQLException;
    OVChipkaart findOVChipkaartByKaartnummer(int kaartnummer) throws SQLException;
    List<OVChipkaart> findOVChipkaartenByReizigerId(int reizigerId) throws SQLException;
    void updateOVChipkaart(OVChipkaart ovChipkaart) throws SQLException;
    void deleteOVChipkaart(int kaartnummer) throws SQLException;

    // Create (Toevoegen)
    void save(OVChipkaart ovChipkaart) throws SQLException;

    // Read (Lezen)
    OVChipkaart getOVChipkaartByKaartnummer(int kaartnummer) throws SQLException;

    List<OVChipkaart> getAllOVChipkaarten() throws SQLException;

    List<OVChipkaart> getOVChipkaartenByReiziger(Reiziger reiziger) throws SQLException;

    // Update (Bijwerken)
    void update(OVChipkaart ovChipkaart) throws SQLException;

    // Delete (Verwijderen)
    void delete(OVChipkaart ovChipkaart) throws SQLException;
}
