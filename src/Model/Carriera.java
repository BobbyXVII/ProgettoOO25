package Model;

import java.util.Date;

public class Carriera {
    private int id;
    private String nomeSquadra;
    private Date dataInizioCarriera;
    private Date dataFineCarriera;
    private int cartelliniRossiAnnuali;
    private int cartelliniGialliAnnuali;
    private String tipologia;
    private int infortuniAnnuali;
    private int goalSubitiAnnuali;
    private int goalEseguitiAnnuali;
    private int valoreDiMercato;
    private Date dataRitiro;

    // Getter e Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeSquadra() {
        return nomeSquadra;
    }

    public void setNomeSquadra(String nomeSquadra) {
        this.nomeSquadra = nomeSquadra;
    }

    public Date getDataInizioCarriera() {
        return dataInizioCarriera;
    }

    public void setDataInizioCarriera(Date dataInizioCarriera) {
        this.dataInizioCarriera = dataInizioCarriera;
    }

    public Date getDataFineCarriera() {
        return dataFineCarriera;
    }

    public void setDataFineCarriera(Date dataFineCarriera) {
        this.dataFineCarriera = dataFineCarriera;
    }

    public int getCartelliniRossiAnnuali() {
        return cartelliniRossiAnnuali;
    }

    public void setCartelliniRossiAnnuali(int cartelliniRossiAnnuali) {
        this.cartelliniRossiAnnuali = cartelliniRossiAnnuali;
    }

    public int getCartelliniGialliAnnuali() {
        return cartelliniGialliAnnuali;
    }

    public void setCartelliniGialliAnnuali(int cartelliniGialliAnnuali) {
        this.cartelliniGialliAnnuali = cartelliniGialliAnnuali;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public int getInfortuniAnnuali() {
        return infortuniAnnuali;
    }

    public void setInfortuniAnnuali(int infortuniAnnuali) {
        this.infortuniAnnuali = infortuniAnnuali;
    }

    public int getGoalSubitiAnnuali() {
        return goalSubitiAnnuali;
    }

    public void setGoalSubitiAnnuali(int goalSubitiAnnuali) {
        this.goalSubitiAnnuali = goalSubitiAnnuali;
    }

    public int getGoalEseguitiAnnuali() {
        return goalEseguitiAnnuali;
    }

    public void setGoalEseguitiAnnuali(int goalEseguitiAnnuali) {
        this.goalEseguitiAnnuali = goalEseguitiAnnuali;
    }

    public int getValoreDiMercato() {
        return valoreDiMercato;
    }

    public void setValoreDiMercato(int valoreDiMercato) {
        this.valoreDiMercato = valoreDiMercato;
    }

    public Date getDataRitiro() {
        return dataRitiro;
    }

    public void setDataRitiro(Date dataRitiro) {
        this.dataRitiro = dataRitiro;
    }

    // Override toString()
    @Override
    public String toString() {
        return "Carriera [id=" + id + ", nomeSquadra=" + nomeSquadra + ", dataInizioCarriera=" + dataInizioCarriera
                + ", dataFineCarriera=" + dataFineCarriera + ", cartelliniRossiAnnuali=" + cartelliniRossiAnnuali
                + ", cartelliniGialliAnnuali=" + cartelliniGialliAnnuali + ", tipologia=" + tipologia
                + ", infortuniAnnuali=" + infortuniAnnuali + ", goalSubitiAnnuali=" + goalSubitiAnnuali
                + ",  goalEseguitiAnnuali=" + goalEseguitiAnnuali + ", valoreDiMercato=" + valoreDiMercato
                + ", dataRitiro=" + dataRitiro + "]";
    }
}