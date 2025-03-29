package Model;

public class Stadio {
    private String nomeStadio;
    private int capacita;
    public Stadio(String nomeStadio,int capacita){
        this.nomeStadio=nomeStadio;
        this.capacita = capacita;
    }

    public  Stadio(){

    }
    // Getter e Setter
    public String getNomeStadio() {
        return nomeStadio;
    }

    public void setNomeStadio(String nomeStadio) {
        this.nomeStadio = nomeStadio;
    }

    public int getCapacita() {
        return capacita;
    }

    public void setCapacita(int capacita) {
        this.capacita = capacita;
    }

    // Override toString()
    @Override
    public String toString() {
        return "Stadio [nomeStadio=" + nomeStadio + ", capacita=" + capacita + "]";
    }
}
