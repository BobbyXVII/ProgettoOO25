package Model;

public class Compete {
    private int id;
    private int idPartita;
    private String nomeSquadra;
    private String tipoAzione;
    private int minutaggio;

    public Compete(int id, int idPartita, String nomeSquadra, String tipoAzione, int minutaggio) {
        this.id = id;
        this.idPartita = idPartita;
        this.nomeSquadra = nomeSquadra;
        this.tipoAzione = tipoAzione;
        this.minutaggio = minutaggio;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdPartita() { return idPartita; }
    public void setIdPartita(int idPartita) { this.idPartita = idPartita; }
    public String getNomeSquadra() { return nomeSquadra; }
    public void setNomeSquadra(String nomeSquadra) { this.nomeSquadra = nomeSquadra; }
    public String getTipoAzione() { return tipoAzione; }
    public void setTipoAzione(String tipoAzione) { this.tipoAzione = tipoAzione; }
    public int getMinutaggio() { return minutaggio; }
    public void setMinutaggio(int minutaggio) { this.minutaggio = minutaggio; }

    @Override
    public String toString() {
        return "Compete{" +
                "id=" + id +
                ", idPartita=" + idPartita +
                ", nomeSquadra='" + nomeSquadra + '\'' +
                ", tipoAzione='" + tipoAzione + '\'' +
                ", minutaggio=" + minutaggio +
                '}';
    }
}
