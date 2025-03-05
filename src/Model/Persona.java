package Model;

import java.util.Date;

public class Persona {
    private int id;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String nazionalita;
    private float altezza;
    private String piede;

    public Persona(int id, String nome, String cognome, Date dataNascita, String nazionalita, float altezza, String piede) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.nazionalita = nazionalita;
        this.altezza = altezza;
        this.piede = piede;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public Date getDataNascita() { return dataNascita; }
    public void setDataNascita(Date dataNascita) { this.dataNascita = dataNascita; }
    public String getNazionalita() { return nazionalita; }
    public void setNazionalita(String nazionalita) { this.nazionalita = nazionalita; }
    public float getAltezza() { return altezza; }
    public void setAltezza(float altezza) { this.altezza = altezza; }
    public String getPiede() { return piede; }
    public void setPiede(String piede) { this.piede = piede; }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                ", nazionalita='" + nazionalita + '\'' +
                ", altezza=" + altezza +
                ", piede='" + piede + '\'' +
                '}';
    }
}