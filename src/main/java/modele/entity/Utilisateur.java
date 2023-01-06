package modele.entity;

import java.util.ArrayList;
import java.util.List;

import static utils.Constante.ROLE_UTILISATEUR;

public class Utilisateur {

    private int idUtilisateur;
    private String nomUtilisateur;

    private String prenomUtilisateur;

    private String emailUtilisateur;

    private String numTelUtlisateur;

    private String roleUtilisateur;

    private Frigo monFrigo;

    private List<Achat> listeAchats;

    public Utilisateur(){

    }

    public Utilisateur(String nom, String prenom, String email, String numTel) {
        this.nomUtilisateur=nom;
        this.prenomUtilisateur=prenom;
        this.emailUtilisateur=email;
        this.roleUtilisateur=ROLE_UTILISATEUR;
        this.numTelUtlisateur= numTel;
        this.listeAchats = new ArrayList<>();

    }

    public Utilisateur(String nom, String prenom, String email, String numTel, int idUtilisateur) {
        this.nomUtilisateur=nom;
        this.prenomUtilisateur=prenom;
        this.emailUtilisateur=email;
        this.roleUtilisateur=ROLE_UTILISATEUR;
        this.numTelUtlisateur= numTel;
        this.listeAchats = new ArrayList<>();
        this.idUtilisateur = idUtilisateur;

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

    public int getIdUtilisateur() {
        return idUtilisateur;
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

    public void setRoleUtilisateur(String roleUtilisateur) {
        this.roleUtilisateur = roleUtilisateur;
    }

    public void setMonFrigo(Frigo monFrigo) {
        this.monFrigo = monFrigo;
    }

    public List<Achat> getListeAchats() {
        return listeAchats;
    }

    public void setListeAchats(List<Achat> listeAchats) {
        this.listeAchats = listeAchats;
    }
}
