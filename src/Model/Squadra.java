package Model;

public class Squadra {
    private String nomeSquadra;
    private int annoFondazione;
    private String nazionalita;
    private String nomeStadio;

    private String citta;

    public Squadra(String nomeSquadra, int annoFondazione, String nazionalita, String nomeStadio) {
        this.nomeSquadra = nomeSquadra;
        this.annoFondazione = annoFondazione;
        this.nazionalita = nazionalita;
        this.nomeStadio = nomeStadio;
    }

    public Squadra(String nomeSquadra, int annoFondazione, String citta, String nazionalita, String nomeStadio) {
        this.nomeSquadra = nomeSquadra;
        this.annoFondazione = annoFondazione;
        this.citta = citta;
        this.nazionalita = nazionalita;
        this.nomeStadio = nomeStadio;
    }

    public String getNomeSquadra() { return nomeSquadra; }
    public void setNomeSquadra(String nomeSquadra) { this.nomeSquadra = nomeSquadra; }
    public int getAnnoFondazione() { return annoFondazione; }
    public void setAnnoFondazione(int annoFondazione) { this.annoFondazione = annoFondazione; }
    public String getNazionalita() { return nazionalita; }
    public void setNazionalita(String nazionalita) { this.nazionalita = nazionalita; }
    public String getCitta() { return citta; }
    public void setCitta(String citta) { this.citta = citta; }
    public String getNomeStadio() { return nomeStadio; }
    public void setNomeStadio(String nomeStadio) { this.nomeStadio = nomeStadio; }

    @Override
    public String toString() {
        return "Squadra{" +
                "nomeSquadra='" + nomeSquadra + '\'' +
                ", annoFondazione=" + annoFondazione +
                ", citta=" + citta +  '\'' +
                ", nazionalita='" + nazionalita + '\'' +
                ", nomeStadio='" + nomeStadio + '\'' +
                '}';
    }
}