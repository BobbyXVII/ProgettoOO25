package Model;

public class FaParte {
    private int idRosa;
    private int idCalciatore;
    private boolean titolare;
    private String posizione;

    public FaParte(int idRosa, int idCalciatore, boolean titolare, String posizione) {
        this.idRosa = idRosa;
        this.idCalciatore = idCalciatore;
        this.titolare = titolare;
        this.posizione = posizione;
    }

    public int getIdRosa() { return idRosa; }
    public void setIdRosa(int idRosa) { this.idRosa = idRosa; }
    public int getIdCalciatore() { return idCalciatore; }
    public void setIdCalciatore(int idCalciatore) { this.idCalciatore = idCalciatore; }
    public boolean isTitolare() { return titolare; }
    public void setTitolare(boolean titolare) { this.titolare = titolare; }
    public String getPosizione() { return posizione; }
    public void setPosizione(String posizione) { this.posizione = posizione; }

    @Override
    public String toString() {
        return "FaParte{" +
                "idRosa=" + idRosa +
                ", idCalciatore=" + idCalciatore +
                ", titolare=" + titolare +
                ", posizione='" + posizione + '\'' +
                '}';
    }
}
