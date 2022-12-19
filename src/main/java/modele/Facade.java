package modele;

import modele.bdd.Bdd;
import modele.entity.Utilisateur;
import modele.exception.EmailDejaUtiliseException;
import modele.exception.NomPrenomDejaUtiliseException;
import modele.exception.UtilisateurInexistantException;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

@Component
public class Facade {

    private Bdd bdd;



    public Facade(){
        this.bdd = new Bdd();
    }

    //Partie Utilisateur

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

        if(bdd.getUtilisateurByEmail(email)){
            throw new EmailDejaUtiliseException();
        }
        if(bdd.getUtilisateurByNomPrenom(nom, prenom)){
            throw new NomPrenomDejaUtiliseException();
        }

        bdd.ajouterUtilisateur(nom, prenom, email, motDepasse, numTel, role);


        return new Utilisateur(nom, prenom, email, numTel);
    }

    /**
     *
     * @param email
     * @return l'identifiant de l'utilisateur
     * @throws UtilisateurInexistantException
     */
    public int getIdUtilisateur(String email) throws UtilisateurInexistantException {
        return getUtilisateurByEmail(email).getIdUtilisateur();
    }

    /**
     *
     * @param email
     * @return Toute l'information sur l'utilisateur
     * @throws UtilisateurInexistantException
     */
    public Utilisateur getUtilisateurByEmail(String email) throws UtilisateurInexistantException {
        if(utilisateurMap.containsKey(email)){
            return this.utilisateurMap.get(email);
        }else throw new UtilisateurInexistantException();
    }

    public void modifierUtilisateurNumTel(){

    }

    public void modifierUtilisateurEmail(){

    }

    public void modifierUtilisateurMotDePasse(){

    }

    //Partie Produits




}
