package Model;

import java.sql.Date;

public class Persona {
    private int ID;
    private String nome;
    private String cognome;
    private Date data_Nascita;
    private String nazionalita;
    private float altezza;
    private String piede;

    // Getter e Setter
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getData_Nascita() {
        return data_Nascita;
    }

    public void setData_Nascita(Date data_Nascita) {
        this.data_Nascita = data_Nascita;
    }

    public String getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public float getAltezza() {
        return altezza;
    }

    public void setAltezza(float altezza) {
        this.altezza = altezza;
    }

    public String getPiede() {
        return piede;
    }

    public void setPiede(String piede) {
        this.piede = piede;
    }

    // Override toString()
    @Override
    public String toString() {
        return "Persona [ID=" + ID + ", nome=" + nome + ", cognome=" + cognome + ", data_Nascita=" + data_Nascita
                + ", nazionalita=" + nazionalita + ", altezza=" + altezza + ", piede=" + piede + "]";
    }
}
