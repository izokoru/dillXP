package modele.entity;

public class Utilisateur {

    private int idUtilisateur;

    private String nomUtilisateur;

    private String prenomUtilisateur;

    private String emailUtilisateur;

    private String numTelUtlisateur;

    private Role roleUtilisateur;

    private Frigo monFrigo;

    public Utilisateur(){

    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public String getPrenomUtilisateur() {
        return prenomUtilisateur;
    }

    public String getEmailUtilisateur() {
        return emailUtilisateur;
    }

    public String getNumTelUtlisateur() {
        return numTelUtlisateur;
    }

    public Role getRoleUtilisateur() {
        return roleUtilisateur;
    }

    public Frigo getMonFrigo() {
        return monFrigo;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public void setPrenomUtilisateur(String prenomUtilisateur) {
        this.prenomUtilisateur = prenomUtilisateur;
    }

    public void setEmailUtilisateur(String emailUtilisateur) {
        this.emailUtilisateur = emailUtilisateur;
    }

    public void setNumTelUtlisateur(String numTelUtlisateur) {
        this.numTelUtlisateur = numTelUtlisateur;
    }

    public void setRoleUtilisateur(Role roleUtilisateur) {
        this.roleUtilisateur = roleUtilisateur;
    }

    public void setMonFrigo(Frigo monFrigo) {
        this.monFrigo = monFrigo;
    }
}
