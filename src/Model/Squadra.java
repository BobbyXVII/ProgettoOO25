package Model;

public class Squadra {
    private String nomeSquadra;
    private int annoFondazione;
    private String nazionalita;
    private String nomeStadio;

    public Squadra(String nomeSquadra, int annoFondazione, String nazionalita, String nomeStadio) {
        this.nomeSquadra = nomeSquadra;
        this.annoFondazione = annoFondazione;
        this.nazionalita = nazionalita;
        this.nomeStadio = nomeStadio;
    }

    public String getNomeSquadra() { return nomeSquadra; }
    public void setNomeSquadra(String nomeSquadra) { this.nomeSquadra = nomeSquadra; }
    public int getAnnoFondazione() { return annoFondazione; }
    public void setAnnoFondazione(int annoFondazione) { this.annoFondazione = annoFondazione; }
    public String getNazionalita() { return nazionalita; }
    public void setNazionalita(String nazionalita) { this.nazionalita = nazionalita; }
    public String getNomeStadio() { return nomeStadio; }
    public void setNomeStadio(String nomeStadio) { this.nomeStadio = nomeStadio; }

    @Override
    public String toString() {
        return "Squadra{" +
                "nomeSquadra='" + nomeSquadra + '\'' +
                ", annoFondazione=" + annoFondazione +
                ", nazionalita='" + nazionalita + '\'' +
                ", nomeStadio='" + nomeStadio + '\'' +
                '}';
    }
}

// Le altre classi model seguiranno lo stesso schema per ROSA, PARTITA, FA_PARTE, COMPETE
