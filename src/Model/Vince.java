package Model;

import java.util.Date;

public class Vince {
    private String idTrofeoIN;
    private String idTrofeoDS;
    private int idPersona;
    private Date dataVincita;


    public Vince(String idTrofeoIN, String idTrofeoDS, int idPersona, Date dataVincita) {
        this.idTrofeoIN = idTrofeoIN;
        this.idTrofeoDS = idTrofeoDS;
        this.idPersona = idPersona;
        this.dataVincita = dataVincita;
    }


    // Getter e Setter
    public String getIdTrofeoIN() {
        return idTrofeoIN;
    }

    public void setIdTrofeoIN(String idTrofeoIN) {
        this.idTrofeoIN = idTrofeoIN;
    }

    public String getIdTrofeoDS() {
        return idTrofeoDS;
    }

    public void setIdTrofeoDS(String idTrofeoDS) {
        this.idTrofeoDS = idTrofeoDS;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public Date getDataVincita() {
        return dataVincita;
    }

    public void setDataVincita(Date dataVincita) {
        this.dataVincita = dataVincita;
    }

    // Override toString()
    @Override
    public String toString() {
        return "Vince [idTrofeoIN=" + idTrofeoIN + ", idTrofeoDS=" + idTrofeoDS
                + ", idPersona=" + idPersona + ", dataVincita=" + dataVincita + "]";
    }
}
