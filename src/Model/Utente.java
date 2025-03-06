package Model;

public class Utente {
    private final String username;
    private final String password;
    private final String ruolo;
    private final Integer idCalciatore;

    public Utente(String username, String password, String ruolo, Integer idCalciatore) {
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
        this.idCalciatore = idCalciatore;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRuolo() { return ruolo; }
    public Integer getIdCalciatore() { return idCalciatore; }

    public boolean isAdmin() { return "ADMIN".equals(ruolo); }
    public boolean isCalciatore() { return "CALCIATORE".equals(ruolo); }
    public boolean isUtente() { return "UTENTE".equals(ruolo); }
}
