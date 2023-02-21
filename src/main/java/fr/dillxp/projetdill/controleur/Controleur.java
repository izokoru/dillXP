package fr.dillxp.projetdill.controleur;


import fr.dillxp.projetdill.modele.Facade;
import fr.dillxp.projetdill.modele.entity.Achat;
import fr.dillxp.projetdill.modele.entity.Produit;
import fr.dillxp.projetdill.modele.entity.Utilisateur;
import fr.dillxp.projetdill.modele.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Controleur {

    public static final String URI_UTILISATEUR = "/utilisateur";

    @Autowired
    Facade facade;

    @Autowired
    JdbcUserDetailsManager jdbcUserDetailsManager;

    @Autowired
    PasswordEncoder passwordEncoder;


    /*@Autowired
    private Environment environment;

    private String ip = InetAddress.getLoopbackAddress().getHostAddress();*/

    /*@Value("${local.server.port}")
    private int serverPort;*/


    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Test");
    }

    /*public Controleur() throws UnknownHostException {
        //afficherInfos();
        System.out.println(ip);
        //System.out.println(serverPort);
    }

    private void afficherInfos() throws UnknownHostException {
        System.out.println(environment.getProperty("java.rmi.server.hostname"));
        System.out.println(environment.getProperty("local.server.port"));
        System.out.println(InetAddress.getLocalHost().getHostAddress());
    }*/

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
                                                   @RequestParam String email, @RequestParam String mdp,
                                                   UriComponentsBuilder base) {

        try{
            System.out.println("TEEEEEEEEEEEEEEEEEEEST");
            System.out.println(nom);
            Utilisateur utilisateur = facade.ajouterUtilisateur(nom, prenom, email, mdp, numTel, role);
            utilisateur.setIdUtilisateur(facade.getIdUtilisateur(email));
            URI location = base.path("/{idUtilisateur}").buildAndExpand(utilisateur.getIdUtilisateur()).toUri();
            return ResponseEntity.created(location).body(utilisateur);
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
    public ResponseEntity<Utilisateur> connexion(@RequestParam String email, @RequestParam String mdp){

        try{
            System.out.println("Coucou");
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
     * @param username
     * @param principal
     * @return L'utilisateur
     */
    @GetMapping(URI_UTILISATEUR + "/{username}")
    public ResponseEntity<Utilisateur> accueil(@PathVariable String username/*, Principal principal*/){

        try{
            Utilisateur utilisateur = facade.getUtilisateurByUsername(username);
            /*Utilisateur utilisateur = facade.getUtilisateurByEmail(principal.getName());
            if(utilisateur.getIdUtilisateur() != idUtilisateur){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }*/
            return (ResponseEntity<Utilisateur>) ResponseEntity.ok(utilisateur);
        }
        catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (UtilisateurInexistantException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Accède au frigo de l'utilisateur
     * @param username
     * @param principal
     * @return
     */
    @GetMapping(URI_UTILISATEUR + "/{username}/monFrigo")
    public ResponseEntity<List<Produit>> monFrigo(@PathVariable String username/*, Principal principal*/){

        try{
            Utilisateur utilisateur = facade.getUtilisateurByUsername(username);
            /*Utilisateur utilisateur = facade.getUtilisateurByEmail(principal.getName());
            if(utilisateur.getIdUtilisateur() != idUtilisateur){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }*/
            List<Produit> produits = facade.getFrigo(username);
            return ResponseEntity.ok(produits);
        }
        catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (UtilisateurInexistantException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Modifie le mot de passe de l'utilisateur
     * @param username
     * @param ancienMdp
     * @param nouveauMdp
     * @return
     */
    @PutMapping(URI_UTILISATEUR + "/{username}/mdp")
    public ResponseEntity<String> modifierMdp(@PathVariable String username, @RequestParam String ancienMdp, @RequestParam String nouveauMdp/*, Principal principal*/){

        try{
            Utilisateur utilisateur = /*facade.getUtilisateurByEmail(principal.getName())*/ facade.getUtilisateurByUsername(username);
            if(utilisateur.getUsername() != username){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            boolean res = facade.modifierUtilisateurMotDePasse(ancienMdp, nouveauMdp, username);
            if(!res){
                throw new InformationsIncorrectesException();
            }
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        catch (SQLException | NoSuchAlgorithmException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (UtilisateurInexistantException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (InformationsIncorrectesException | MotDePasseDifferentsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Récupère la liste des achats de l'utilisateur
     * @param username
     * @return
     */
    @GetMapping(URI_UTILISATEUR + "/{username}/achats")
    public ResponseEntity<List<Achat>> listeAchats(@PathVariable String username/*, Principal principal*/){
        try{
            Utilisateur utilisateur = /*facade.getUtilisateurByEmail(principal.getName())*/ facade.getUtilisateurByUsername(username);
            if(utilisateur.getUsername() != username){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            List<Achat> achats = facade.getListeAchats(username);
            return ResponseEntity.ok(achats);

        }
        catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (UtilisateurInexistantException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*@PatchMapping(URI_UTILISATEUR + "/{idUtilisateur}/compte")
    public ResponseEntity<String> modifierCompte(@PathVariable int idUtilisateur*//*, Principal principal*//*, @RequestParam(required = false) String nom, @RequestParam(required = false) String email, @RequestParam(required = false) String prenom){


    }*/

    // Modifier nom, etc...



}
