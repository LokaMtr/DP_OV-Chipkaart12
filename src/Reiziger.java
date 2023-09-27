import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    private int reizigerId;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    private Adres adres;

    public List<OVChipkaart> getOvChipkaartList() {
        return ovChipkaartList;
    }

    public void setOvChipkaartList(List<OVChipkaart> ovChipkaartList) {
        this.ovChipkaartList = ovChipkaartList;
    }

    private List<OVChipkaart> ovChipkaartList = new ArrayList<>();
    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    // Constructor
    public Reiziger(int reizigerId, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.reizigerId = reizigerId;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    // Getter-methoden voor de attributen
    public int getReizigerId1() {
        return reizigerId;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public int getReizigerId() {
        return reizigerId;
    }


    // toString()-methode voor een nette weergave van een Reiziger-object
    public String oldtoString() {
        return "Reiziger ID: " + reizigerId +
                "\nVoorletters: " + voorletters +
                "\nTussenvoegsel: " + tussenvoegsel +
                "\nAchternaam: " + achternaam +
                "\nGeboortedatum: " + geboortedatum.toString();
    }

    @Override
    public String toString() {
        return "Reiziger{" +
                "reizigerId=" + reizigerId +
                ", voorletters='" + voorletters + '\'' +
                ", tussenvoegsel='" + tussenvoegsel + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", geboortedatum=" + geboortedatum +
                ", adres=" + adres +
                ", ovChipkaartList=" + ovChipkaartList +
                '}';
    }
}
