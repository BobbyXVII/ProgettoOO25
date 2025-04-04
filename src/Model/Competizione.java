package Model;

public class Competizione {
    private String nomeCompetizione;
    private String annoSvolgimento;
    private String tipCompetizione;
    private String nazionalita;

    public Competizione(String nomeCompetizione, String tipCompetizione) {
        this.nomeCompetizione = nomeCompetizione;
        this.tipCompetizione = tipCompetizione;
    }

    public Competizione(String nomeCompetizione, String annoSvolgimento, String tipCompetizione, String nazionalita) {
        this.nomeCompetizione = nomeCompetizione;
        this.annoSvolgimento = annoSvolgimento;
        this.tipCompetizione = tipCompetizione;
        this.nazionalita = nazionalita;
    }



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

    public String getTipCompetizione() {
        return tipCompetizione;
    }

    public void setTipCompetizione(String tipCompetizione) {
        this.tipCompetizione = tipCompetizione;
    }

    public String getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    // Override toString()
    @Override
    public String toString() {
        return "Competizione [nomeCompetizione=" + nomeCompetizione + ", annoSvolgimento=" + annoSvolgimento
                + ", tipCompetizione=" + tipCompetizione + ", nazionalita=" + nazionalita + "]";
    }
}
