package Model;
public class Rosa {
    private int idRosa;
    private String nomeSquadra;
    private String stagione;

    public Rosa(int idRosa, String nomeSquadra, String stagione) {
        this.idRosa = idRosa;
        this.nomeSquadra = nomeSquadra;
        this.stagione = stagione;
    }

    public int getIdRosa() { return idRosa; }
    public void setIdRosa(int idRosa) { this.idRosa = idRosa; }
    public String getNomeSquadra() { return nomeSquadra; }
    public void setNomeSquadra(String nomeSquadra) { this.nomeSquadra = nomeSquadra; }
    public String getStagione() { return stagione; }
    public void setStagione(String stagione) { this.stagione = stagione; }

    @Override
    public String toString() {
        return "Rosa{" +
                "idRosa=" + idRosa +
                ", nomeSquadra='" + nomeSquadra + '\'' +
                ", stagione='" + stagione + '\'' +
                '}';
    }
}
