package Model;

public class TrofeoDiSquadra {
    private String idTrofeoDS;
    private String nomeCompetizione;
    private String annoSvolgimento;
    private String nomeSquadra;

    public TrofeoDiSquadra(String nomeSquadra, String annoSvolgimento) {
        this.nomeSquadra = nomeSquadra;
        this.annoSvolgimento = annoSvolgimento;
    }

    public TrofeoDiSquadra() {
    }

    // Getter e Setter
    public String getIdTrofeoDS() {
        return idTrofeoDS;
    }

    public void setIdTrofeoDS(String idTrofeoDS) {
        this.idTrofeoDS = idTrofeoDS;
    }

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

    // Override toString()
    @Override
    public String toString() {
        return "TrofeoDiSquadra [idTrofeoDS=" + idTrofeoDS + ", nomeCompetizione=" + nomeCompetizione
                + ", annoSvolgimento=" + annoSvolgimento + ", nomeSquadra=" + nomeSquadra + "]";
    }
}
