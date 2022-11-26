package modele.entity;

import static utils.Constante.ROLE_UTILISATEUR;

public class Utilisateur {
    private String nomUtilisateur;

    private String prenomUtilisateur;

    private String emailUtilisateur;

    private String numTelUtlisateur;

    private String roleUtilisateur;

    private Frigo monFrigo;

    public Utilisateur(){

    }

    public Utilisateur(String nom, String prenom, String email) {
        this.nomUtilisateur=nom;
        this.prenomUtilisateur=prenom;
        this.emailUtilisateur=email;
        this.roleUtilisateur=ROLE_UTILISATEUR;
        this.numTelUtlisateur="";

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

    public String  getRoleUtilisateur() {
        return roleUtilisateur;
    }

    public Frigo getMonFrigo() {
        return monFrigo;
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

    public void setRoleUtilisateur(String roleUtilisateur) {
        this.roleUtilisateur = roleUtilisateur;
    }

    public void setMonFrigo(Frigo monFrigo) {
        this.monFrigo = monFrigo;
    }
}
