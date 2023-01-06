package controleur;


import modele.Facade;
import modele.entity.Utilisateur;
import modele.exception.EmailDejaUtiliseException;
import modele.exception.InformationsIncorrectesException;
import modele.exception.NomPrenomDejaUtiliseException;
import modele.exception.UtilisateurInexistantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.sql.SQLException;

@RestController
@RequestMapping("/api")
public class Controleur {

    public static final String URI_UTILISATEUR = "/utilisateur";

    @Autowired
    Facade facade;


    /**
     * Enregistre un utilisateur
     * @param nom Son nom
     * @param prenom Son prénom
     * @param role Son rôle
     * @param numTel Son numéro de téléphone
     * @param email Son email
     * @return L'utilisateur qui a été enregistré
     */
    @PostMapping("/register")
    public ResponseEntity<Utilisateur> enregistrer(@RequestParam String nom, @RequestParam String prenom,
                                                   @RequestParam String role, @RequestParam String numTel,
                                                   @RequestParam String email, @RequestParam String mdp, UriComponentsBuilder base) {

        try{
            Utilisateur utilisateur = facade.ajouterUtilisateur(nom, prenom, email, mdp, numTel, role);
            utilisateur.setIdUtilisateur(facade.getIdUtilisateur(email));
            URI location = base.path("/{idUtilisateur}").buildAndExpand(utilisateur.getIdUtilisateur()).toUri();
            return ResponseEntity.created(location).build();
        }
        catch (EmailDejaUtiliseException | NomPrenomDejaUtiliseException | UtilisateurInexistantException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch (SQLException | NoSuchAlgorithmException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /**
     * Se connecte à un compte
     * @param email
     * @param mdp
     * @return
     */
    @PostMapping("/connexion")
    public ResponseEntity<Utilisateur> connexion(@RequestParam String email, @RequestParam String mdp, Principal principal){

        try{
            Utilisateur utilisateur = facade.seConnecter(email, mdp);
            return ResponseEntity.ok(utilisateur);

        }
        catch (SQLException | NoSuchAlgorithmException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (InformationsIncorrectesException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

    }


    /**
     * Page d'accueil de l'utilisateur
     * @param idUtilisateur
     * @param principal
     * @return L'utilisateur
     */
    @GetMapping(URI_UTILISATEUR + "/{idUtilisateur}")
    public ResponseEntity<Utilisateur> accueil(@PathVariable int idUtilisateur, Principal principal){

        try{
            Utilisateur utilisateur = facade.getUtilisateurByEmail(principal.getName());
            if(utilisateur.getIdUtilisateur() != idUtilisateur){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return ResponseEntity.ok(utilisateur);
        }
        catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (UtilisateurInexistantException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }




}
