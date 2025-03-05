package Model;
public class Partita {
    private int idPartita;
    private String nomeSquadra;
    private String nomeCompetizione;
    private String annoSvolgimento;
    private int idRosa;
    private boolean inCasa;
    private java.sql.Date data;
    private java.sql.Time ora;
    private String risultato;
    private String nomeStadio;

    public Partita(int idPartita, String nomeSquadra, String nomeCompetizione, String annoSvolgimento, int idRosa, boolean inCasa, java.sql.Date data, java.sql.Time ora, String risultato, String nomeStadio) {
        this.idPartita = idPartita;
        this.nomeSquadra = nomeSquadra;
        this.nomeCompetizione = nomeCompetizione;
        this.annoSvolgimento = annoSvolgimento;
        this.idRosa = idRosa;
        this.inCasa = inCasa;
        this.data = data;
        this.ora = ora;
        this.risultato = risultato;
        this.nomeStadio = nomeStadio;
    }

    public int getIdPartita() { return idPartita; }
    public void setIdPartita(int idPartita) { this.idPartita = idPartita; }
    public String getNomeSquadra() { return nomeSquadra; }
    public void setNomeSquadra(String nomeSquadra) { this.nomeSquadra = nomeSquadra; }
    public String getNomeCompetizione() { return nomeCompetizione; }
    public void setNomeCompetizione(String nomeCompetizione) { this.nomeCompetizione = nomeCompetizione; }
    public String getAnnoSvolgimento() { return annoSvolgimento; }
    public void setAnnoSvolgimento(String annoSvolgimento) { this.annoSvolgimento = annoSvolgimento; }
    public int getIdRosa() { return idRosa; }
    public void setIdRosa(int idRosa) { this.idRosa = idRosa; }
    public boolean isInCasa() { return inCasa; }
    public void setInCasa(boolean inCasa) { this.inCasa = inCasa; }
    public java.sql.Date getData() { return data; }
    public void setData(java.sql.Date data) { this.data = data; }
    public java.sql.Time getOra() { return ora; }
    public void setOra(java.sql.Time ora) { this.ora = ora; }
    public String getRisultato() { return risultato; }
    public void setRisultato(String risultato) { this.risultato = risultato; }
    public String getNomeStadio() { return nomeStadio; }
    public void setNomeStadio(String nomeStadio) { this.nomeStadio = nomeStadio; }

    @Override
    public String toString() {
        return "Partita{" +
                "idPartita=" + idPartita +
                ", nomeSquadra='" + nomeSquadra + '\'' +
                ", nomeCompetizione='" + nomeCompetizione + '\'' +
                ", annoSvolgimento='" + annoSvolgimento + '\'' +
                ", idRosa=" + idRosa +
                ", inCasa=" + inCasa +
                ", data=" + data +
                ", ora=" + ora +
                ", risultato='" + risultato + '\'' +
                ", nomeStadio='" + nomeStadio + '\'' +
                '}';
    }
}
