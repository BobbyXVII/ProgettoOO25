package Model;

import java.util.Date;

public class TrofeoIndividuale {
    private String idTrofeoIN;
    private String nomeAssegnazione;
    private Date dataSvolgimento;

    public TrofeoIndividuale(String idTrofeoIN, String nomeAssegnazione, Date dataSvolgimento) {
        this.idTrofeoIN = idTrofeoIN;
        this.nomeAssegnazione = nomeAssegnazione;
        this.dataSvolgimento = dataSvolgimento;
    }

    // Getter e Setter
    public String getIdTrofeoIN() {
        return idTrofeoIN;
    }

    public void setIdTrofeoIN(String idTrofeoIN) {
        this.idTrofeoIN = idTrofeoIN;
    }

    public String getNomeAssegnazione() {
        return nomeAssegnazione;
    }

    public void setNomeAssegnazione(String nomeAssegnazione) {
        this.nomeAssegnazione = nomeAssegnazione;
    }

    public Date getDataSvolgimento() {
        return dataSvolgimento;
    }

    public void setDataSvolgimento(Date dataSvolgimento) {
        this.dataSvolgimento = dataSvolgimento;
    }

    // Override toString()
    @Override
    public String toString() {
        return "TrofeoIndividuale [idTrofeoIN=" + idTrofeoIN + ", nomeAssegnazione=" + nomeAssegnazione
                + ", dataSvolgimento=" + dataSvolgimento + "]";
    }
}
