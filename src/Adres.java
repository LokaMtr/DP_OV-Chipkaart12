import java.util.Objects;

public class Adres {
    private int adres_id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private Reiziger reiziger; // Voeg een Reiziger-veld toe
    private int reizigerId;

    public Adres(int adres_id, String postcode, String huisnummer, String straat, String woonplaats, int reizigerId) {
        this.adres_id = adres_id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reizigerId = reizigerId;
    }

    // Getter en setter methoden voor de reiziger-ID
    public int getReizigerId() {
        return reizigerId;
    }

    public void setReizigerId(int reizigerId) {
        this.reizigerId = reizigerId;
    }


    public int getAdres_id() {
        return adres_id;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Adres {")
                .append("#").append(adres_id)
                .append(" ").append(postcode)
                .append("-").append(huisnummer)
                .append(", ").append(straat)
                .append(", ").append(woonplaats);

        if (reiziger != null) {
            builder.append(", ").append("Reiziger {#").append(reiziger.getReizigerId()).append("}");
        }

        builder.append("}");

        return builder.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adres adres = (Adres) o;
        return adres_id == adres.adres_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(adres_id);
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

}
