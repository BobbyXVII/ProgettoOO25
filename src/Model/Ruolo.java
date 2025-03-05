package Model;

public class Ruolo {
    private String abbrRuolo;
    private String nomeRuolo;
    private String descrizione;

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

    // Override toString()
    @Override
    public String toString() {
        return "Ruolo [abbrRuolo=" + abbrRuolo + ", nomeRuolo=" + nomeRuolo + ", descrizione=" + descrizione + "]";
    }
}
