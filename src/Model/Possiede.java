package Model;

public class Possiede {
    private int id;
    private String nomeSkill;

    public Possiede(int id, String nomeSkill) {
        this.id = id;
        this.nomeSkill = nomeSkill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeSkill() {
        return nomeSkill;
    }

    public void setNomeSkill(String nomeSkill) {
        this.nomeSkill = nomeSkill;
    }

    @Override
    public String toString() {
        return "Possiede{" +
                "id=" + id +
                ", nomeSkill='" + nomeSkill + '\'' +
                '}';
    }
}
