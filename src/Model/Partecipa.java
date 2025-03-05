package Model;

public class Partecipa {
    private String nomeCompetizione;
    private String annoSvolgimento;
    private String nomeSquadra;
    private int posizioneFinale;

    // Getter e Setter
    public String getNomeCompetizione() {
        return nomeCompetizione;
    }

    public void setNomeCompetizione(String nomeCompetizione) {
        this.nomeCompetizione = nomeCompetizione;
    }

    public String getAnnoSvolgimento() {
        return annoSvolgimento;
    }

    public void setAnnoSvolgimento(String annoSvolgimento) {
        this.annoSvolgimento = annoSvolgimento;
    }

    public String getNomeSquadra() {
        return nomeSquadra;
    }

    public void setNomeSquadra(String nomeSquadra) {
        this.nomeSquadra = nomeSquadra;
    }

    public int getPosizioneFinale() {
        return posizioneFinale;
    }

    public void setPosizioneFinale(int posizioneFinale) {
        this.posizioneFinale = posizioneFinale;
    }

    // Override toString()
    @Override
    public String toString() {
        return "Partecipa [nomeCompetizione=" + nomeCompetizione + ", annoSvolgimento=" + annoSvolgimento
                + ", nomeSquadra=" + nomeSquadra + ", posizioneFinale=" + posizioneFinale + "]";
    }
}
