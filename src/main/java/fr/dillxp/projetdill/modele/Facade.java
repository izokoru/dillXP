package fr.dillxp.projetdill.modele;

import fr.dillxp.projetdill.modele.bdd.Bdd;
import fr.dillxp.projetdill.modele.exception.EmailDejaUtiliseException;
import fr.dillxp.projetdill.modele.exception.NomPrenomDejaUtiliseException;
import fr.dillxp.projetdill.modele.entity.Achat;
import fr.dillxp.projetdill.modele.entity.Produit;
import fr.dillxp.projetdill.modele.entity.Utilisateur;
import fr.dillxp.projetdill.modele.exception.InformationsIncorrectesException;
import fr.dillxp.projetdill.modele.exception.UtilisateurInexistantException;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

@Component
public class Facade {

    private Bdd bdd;

    public Facade() throws SQLException {
        this.bdd = new Bdd();
    }

    //---------------------------------Partie Utilisateur--------------------------------------

    /**
     *
     * @param nom
     * @param prenom
     * @param email
     * @param motDepasse
     * @return Le nouvel utilisateur
     * @throws EmailDejaUtiliseException
     */
    public Utilisateur ajouterUtilisateur(String nom, String prenom, String email, String motDepasse, String numTel, String role)
            throws EmailDejaUtiliseException, SQLException, NomPrenomDejaUtiliseException, NoSuchAlgorithmException {

        if(bdd.verifierUtilisateurByEmail(email)){
            throw new EmailDejaUtiliseException();
        }
        if(bdd.verifierUtilisateurByNomPrenom(nom, prenom)){
            throw new NomPrenomDejaUtiliseException();
        }

        bdd.ajouterUtilisateur(nom, prenom, email, motDepasse, numTel, role);


        return new Utilisateur(nom, prenom, email, numTel);
    }

    /**
     * Se connecte à un compte
     * @param email
     * @param mdp
     * @return L'utilisateur
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws InformationsIncorrectesException
     */
    public Utilisateur seConnecter(String email, String mdp) throws SQLException, NoSuchAlgorithmException, InformationsIncorrectesException {

        Utilisateur utilisateur = bdd.connexion(email, mdp);
        if(utilisateur == null){
            throw new InformationsIncorrectesException();
        }
        return utilisateur;
    }


    /**
     * Récupère les produit du frigo de l'utilisateur
     * @param idUtilisateur
     * @return
     */
    public List<Produit> getFrigo(int idUtilisateur) throws SQLException {

        return bdd.getFrigo(idUtilisateur);

    }

    /**
     *
     * @param email
     * @return l'identifiant de l'utilisateur
     * @throws UtilisateurInexistantException
     */
    public int getIdUtilisateur(String email) throws UtilisateurInexistantException, SQLException {
        return getUtilisateurByEmail(email).getIdUtilisateur();
    }

    /**
     *
     * @param email
     * @return Toute l'information sur l'utilisateur
     * @throws UtilisateurInexistantException
     */
    public Utilisateur getUtilisateurByEmail(String email) throws UtilisateurInexistantException, SQLException {

        Utilisateur utilisateur = bdd.getUtilisateurByEmail(email);
        if(utilisateur == null){
            throw new UtilisateurInexistantException();
        }
        return utilisateur;
    }

    public void modifierUtilisateurNumTel(){

    }

    public void modifierUtilisateurEmail(){

    }

    /**
     * Modifie le mot de passe d'un utilisateur
     * @param ancienMdp
     * @param nouveauMdp
     */
    public boolean modifierUtilisateurMotDePasse(String ancienMdp, String nouveauMdp, int idUtilisateur) throws SQLException {

        return bdd.modifierMdp(ancienMdp, nouveauMdp, idUtilisateur);

    }

    //Partie Produits


    /**
     * Récupère la liste des achats d'un utilisateur
     * @param idUtilisateur
     * @return
     * @throws SQLException
     */
    public List<Achat> getListeAchats(int idUtilisateur) throws SQLException {

        return bdd.getListeAchats(idUtilisateur);

    }

    public void modifInfosCompte(int idUtilisateur, String nom, String email, String prenom, String numTle){

    }



}