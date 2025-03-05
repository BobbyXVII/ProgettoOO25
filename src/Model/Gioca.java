package Model;

public class Gioca {
    private String abbrRuolo;
    private int ID;

    // Costruttori
    public Gioca() {
    }

    public Gioca(String abbrRuolo, int ID) {
        this.abbrRuolo = abbrRuolo;
        this.ID = ID;
    }

    // Getter e Setter
    public String getAbbrRuolo() {
        return abbrRuolo;
    }

    public void setAbbrRuolo(String abbrRuolo) {
        this.abbrRuolo = abbrRuolo;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Gioca{" +
                "abbrRuolo='" + abbrRuolo + '\'' +
                ", ID=" + ID +
                '}';
    }
}