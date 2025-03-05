package Model;

public class Skills {
    private String nomeSkill;
    private String descrizione;

    // Getter e Setter
    public String getNomeSkill() {
        return nomeSkill;
    }

    public void setNomeSkill(String nomeSkill) {
        this.nomeSkill = nomeSkill;
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
        return "Skills [nomeSkill=" + nomeSkill + ", descrizione=" + descrizione + "]";
    }
}
