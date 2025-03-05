package Model;

public class Ruolo {
    private String abbrRuolo;
    private String nomeRuolo;
    private String descrizione;

    // Costruttori
    public Ruolo() {
    }

    public Ruolo(String abbrRuolo, String nomeRuolo, String descrizione) {
        this.abbrRuolo = abbrRuolo;
        this.nomeRuolo = nomeRuolo;
        this.descrizione = descrizione;
    }

    // Getter e Setter
    public String getAbbrRuolo() {
        return abbrRuolo;
    }

    public void setAbbrRuolo(String abbrRuolo) {
        this.abbrRuolo = abbrRuolo;
    }

    public String getNomeRuolo() {
        return nomeRuolo;
    }

    public void setNomeRuolo(String nomeRuolo) {
        this.nomeRuolo = nomeRuolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return "Ruolo{" +
                "abbrRuolo='" + abbrRuolo + '\'' +
                ", nomeRuolo='" + nomeRuolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }
}