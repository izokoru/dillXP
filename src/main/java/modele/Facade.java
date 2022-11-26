package modele;

import modele.bdd.Bdd;
import modele.entity.Utilisateur;
import modele.exception.EmailDejaUtiliseException;
import modele.exception.UtilisateurInexistantException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Facade {

    private Bdd bdd;

    public final Map<String,Utilisateur> utilisateurMap;


    public Facade(Map<String, Utilisateur> utilisateurMap){
        this.utilisateurMap = utilisateurMap;
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
    public Utilisateur ajouterUtilisateur(String nom,String prenom,String email,String motDepasse) throws EmailDejaUtiliseException {
        Utilisateur user = new Utilisateur(nom,prenom,email);
        if(utilisateurMap.containsKey(email)){
            throw new EmailDejaUtiliseException();
        }
        else {
            Utilisateur utilisateur = new Utilisateur(nom,prenom,email);
            utilisateurMap.put(utilisateur.getEmailUtilisateur(),utilisateur);
            return utilisateur;
        }
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
