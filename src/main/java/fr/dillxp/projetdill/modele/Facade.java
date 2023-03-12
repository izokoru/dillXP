package fr.dillxp.projetdill.modele;

import fr.dillxp.projetdill.modele.bdd.Bdd;
import fr.dillxp.projetdill.modele.entity.Magasin;
import fr.dillxp.projetdill.modele.exception.*;
import fr.dillxp.projetdill.modele.entity.Achat;
import fr.dillxp.projetdill.modele.entity.Produit;
import fr.dillxp.projetdill.modele.entity.Utilisateur;
import org.springframework.stereotype.Component;
import java.sql.SQLException;
import java.util.List;

@Component
public class Facade {

    private final Bdd bdd;

    public Facade() throws SQLException {
        this.bdd = new Bdd();
    }

    //---------------------------------Partie Utilisateur--------------------------------------

    /**
     *
     * @param nom Le nom de l'utilisateur
     * @param prenom Le prénom de l'utilisateur
     * @param email L'email de l'utilisateur
     * @return Le nouvel utilisateur
     * @throws EmailDejaUtiliseException Si l'email est déjà utilisé
     */
    public Utilisateur ajouterUtilisateur(String username, String nom, String prenom, String email, String numTel)
            throws EmailDejaUtiliseException, SQLException, NomPrenomDejaUtiliseException, UsernameDejaUtiliseException {

        if(bdd.verifierUtilisateurByEmail(email)){
            throw new EmailDejaUtiliseException();
        }
        if(bdd.verifierUtilisateurByNomPrenom(nom, prenom)){
            throw new NomPrenomDejaUtiliseException();
        }
        if(bdd.verifierUtilisateurByUsername(username)){
            throw new UsernameDejaUtiliseException();
        }

        bdd.ajouterUtilisateur(username, nom, prenom, email, numTel);


        return new Utilisateur(nom, prenom, email, numTel);
    }


    /**
     * Récupère les produit du frigo de l'utilisateur
     * @param username Le username de l'utilisateur
     * @return La liste des produits
     */
    public List<Produit> getFrigo(String username) throws SQLException {

        return bdd.getFrigo(username);

    }

    /**
     * Récupère les informations de l'utilisateur
     * @param email L'email de l'utilisateur
     * @return Toute l'information sur l'utilisateur
     * @throws UtilisateurInexistantException Si l'utilisateur n'existe pas
     */
    public Utilisateur getUtilisateurByEmail(String email) throws UtilisateurInexistantException, SQLException {

        Utilisateur utilisateur = bdd.getUtilisateurByEmail(email);
        if(utilisateur == null){
            throw new UtilisateurInexistantException();
        }
        return utilisateur;
    }

    /**
     * Récupère l'utilisateur grâce à son username
     * @param username Le username de l'utilisateur
     * @return L'utilisateur s'il existe
     * @throws UtilisateurInexistantException Si l'utilisateur n'existe pas
     */
    public Utilisateur getUtilisateurByUsername(String username) throws UtilisateurInexistantException, SQLException {

        Utilisateur utilisateur = bdd.getUtilisateurByUsername(username);
        if(utilisateur == null){
            throw new UtilisateurInexistantException();
        }
        return utilisateur;
    }

    public void modifierUtilisateurNumTel(){

    }

    public void modifierUtilisateurEmail(){

    }


    //Partie Produits


    /**
     * Récupère la liste des achats d'un utilisateur
     * @param username Le username de l'utilisateur
     * @return La liste des achats de l'utilisateur
     */
    public List<Achat> getListeAchats(String username) throws SQLException {

        return bdd.getListeAchats(username);

    }

    /**
     * Ajoute un achat d'un utilisateur
     * @param username Le username de l'utilisateur
     * @param achat L'achat à ajouter
     * @return true si l'achat a été effectué false sinon
     * @throws MagasinInexistantException Si le magasin n'existe pas
     */
    public boolean ajouterAchat(String username, Achat achat) throws SQLException, MagasinInexistantException {
        return bdd.ajouterAchat(username, achat);
    }

    /**
     * Ajoute un produit dans le frigo de l'utilisateur
     * @param username Le username de l'utilisateur
     * @param produit Le produit à ajouter
     * @return true si le produit a été ajouté false sinon
     */
    public boolean ajouterProduit(String username, Produit produit) throws SQLException {
        return bdd.ajouterProduit(username, produit);
    }

    /**
     * Enlève 1 à la quantité d'un produit
     * @param username Le username de l'utilisateur
     * @param produit Le produit à modifier
     * @return true si la produit a été modifié false sinon
     */
    public boolean supprimerProduit(String username, Produit produit) throws SQLException {
        return bdd.supprimerProduit(username, produit);
    }

    public void modifInfosCompte(int idUtilisateur, String nom, String email, String prenom, String numTel){

    }



    // -------------------------------- PARTIE ADMIN -----------------------------//

    /**
     * Ajoute un magasin
     * @param magasin Le magasin à ajouter
     * @return true si le magasin a été ajouté false sinon
     */
    public boolean ajouterMagasin(Magasin magasin) throws SQLException {

        return this.bdd.ajouterMagasin(magasin);

    }


}
