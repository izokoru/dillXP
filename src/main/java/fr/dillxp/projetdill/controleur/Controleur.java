package fr.dillxp.projetdill.controleur;

import fr.dillxp.projetdill.modele.Facade;
import fr.dillxp.projetdill.modele.entity.Achat;
import fr.dillxp.projetdill.modele.entity.Magasin;
import fr.dillxp.projetdill.modele.entity.Produit;
import fr.dillxp.projetdill.modele.entity.Utilisateur;
import fr.dillxp.projetdill.modele.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class Controleur {

    //TODO ADD PRODUITS

    public static final String URI_UTILISATEUR = "/utilisateur";
    public static final String URI_ADMIN = "/admin";

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
    public ResponseEntity<String> test() {

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
     *
     * @param username Son username
     * @param nom Son nom
     * @param prenom Son prénom
     * @param numTel Son numéro de téléphone
     * @param email  Son email
     * @return L'utilisateur qui a été enregistré
     */
    @PostMapping("/register")
    public ResponseEntity<Utilisateur> enregistrer(@RequestParam String username, @RequestParam String nom,
                                                   @RequestParam String prenom, @RequestParam String email,
                                                   @RequestParam String motDePasse, @RequestParam String numTel,
                                                   UriComponentsBuilder base) {

        //System.out.println(nom);
        try{
            jdbcUserDetailsManager.createUser(User.withUsername(username).password(passwordEncoder.encode(motDePasse)).roles("USER").build());
            Utilisateur utilisateur = facade.ajouterUtilisateur(username, nom, prenom, email, numTel);
            utilisateur.setUsername(username);
            URI location = base.path("/{idUtilisateur}").buildAndExpand(utilisateur.getUsername()).toUri();
            return ResponseEntity.created(location).body(utilisateur);
        }

        catch(EmailDejaUtiliseException | NomPrenomDejaUtiliseException | UsernameDejaUtiliseException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(SQLException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /**
     * Page d'accueil de l'utilisateur (i.e se connecter)
     * @param username Le username de l'utilisateur
     * @return L'utilisateur
     */
    @GetMapping(URI_UTILISATEUR + "/{username}")
    public ResponseEntity<Utilisateur> accueil(@PathVariable String username, Principal principal){

        try{

            boolean userExist = jdbcUserDetailsManager.userExists(username);
            if(!userExist){
                throw new UtilisateurInexistantException();
            }

            Utilisateur utilisateur = facade.getUtilisateurByUsername(principal.getName());

            System.out.println("Principal: " + utilisateur.getUsername());
            System.out.println("username: " + username);

            if(!Objects.equals(utilisateur.getUsername(), username)){
                System.out.println("Pas bon");
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

    /**
     * Accède au frigo de l'utilisateur
     * @param username Le username de l'utilisateur
     * @return La liste des produits
     */
    @GetMapping(URI_UTILISATEUR + "/{username}/monFrigo")
    public ResponseEntity<List<Produit>> monFrigo(@PathVariable String username, Principal principal){

        try{
            Utilisateur utilisateur = facade.getUtilisateurByUsername(principal.getName());

            System.out.println("Principal: " + utilisateur.getUsername());
            System.out.println("username: " + username);

            if(!Objects.equals(utilisateur.getUsername(), username)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
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
     * @param username Le username de l'utilisateur
     * @param nouveauMdp Le nouveau mot de passe
     * @return Message de confirmation
     */
    @PutMapping(URI_UTILISATEUR + "/{username}/mdp")
    public ResponseEntity<String> modifierMdp(@PathVariable String username, @RequestBody String nouveauMdp, Principal principal){

        try{
            Utilisateur utilisateur = facade.getUtilisateurByUsername(principal.getName());
            if(!Objects.equals(utilisateur.getUsername(), username)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            if(!jdbcUserDetailsManager.userExists(username)){
                throw new UtilisateurInexistantException();
            }

            jdbcUserDetailsManager.updateUser(User.withUsername(username).password(passwordEncoder.encode(nouveauMdp)).roles("USER").build());

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (UtilisateurInexistantException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Récupère la liste des achats de l'utilisateur
     * @param username Le username de l'utilisateur
     * @return La liste des achats de l'utilisateur
     */
    @GetMapping(URI_UTILISATEUR + "/{username}/achats")
    public ResponseEntity<List<Achat>> listeAchats(@PathVariable String username, Principal principal){
        try{
            Utilisateur utilisateur = facade.getUtilisateurByUsername(principal.getName());
            if(!Objects.equals(utilisateur.getUsername(), username)){
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

    /**
     * Ajoute un achat d'un utilisateur
     * @param username Le username de l'utilisateur
     * @param achat L'achat à ajouter
     * @return Message de confirmation
     */
    @PostMapping(URI_UTILISATEUR + "/{username}/ajouterAchat")
    public ResponseEntity<String> ajouterAchat(@PathVariable String username, @RequestBody Achat achat, Principal principal){
        try{
            Utilisateur utilisateur = facade.getUtilisateurByUsername(username);
            if(!Objects.equals(utilisateur.getUsername(), username)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            facade.ajouterAchat(username, achat);
            return ResponseEntity.ok().body("Achat ajouté");

        }
        catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (UtilisateurInexistantException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (MagasinInexistantException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Ajoute un produit dans le réfrégirateur d'un utilisateur
     * @param username Le username de l'utilisateur
     * @param produit Le produit à ajouter
     * @return Message de confirmation
     */
    @PostMapping(URI_UTILISATEUR + "/{username}/ajouterProduit")
    public ResponseEntity<String> ajouterProduit(@PathVariable String username, @RequestBody Produit produit, Principal principal){
        // TODO vérifier qu'il n'existe pas déjà

        System.out.println("Username: " + username);
        System.out.println("utilisateur: " + principal.getName());
        try{
            Utilisateur utilisateur = facade.getUtilisateurByUsername(principal.getName()) /*facade.getUtilisateurByUsername(username)*/;

            System.out.println("Username: " + username);
            System.out.println("utilisateur: " + utilisateur.getUsername());
            if(!Objects.equals(utilisateur.getUsername(), username)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            boolean res = facade.ajouterProduit(username, produit);
            return ResponseEntity.ok("" + res);

        }
        catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (UtilisateurInexistantException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




    /**
     * Enlève 1 à la quantité d'un produit et le supprime s'il est à 0
     * @param username Le username de l'utilisateur
     * @param produit Le produit à modifier
     * @return Message de confirmation
     */
    @PostMapping(URI_UTILISATEUR + "/{username}/supprimerProduit")
    public ResponseEntity<String> supprimerProduit(@PathVariable String username, @RequestBody Produit produit, Principal principal){
        try{
            Utilisateur utilisateur = facade.getUtilisateurByUsername(principal.getName());
            if(!Objects.equals(utilisateur.getUsername(), username)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            boolean res = facade.supprimerProduit(username, produit);
            return ResponseEntity.ok("" + res);

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



    // -------------------------------------- PARTIE ADMIN -------------------------------------

    /**
     * Page d'accueil (i.e connexion) admin
     * @return Message vide
     */
    @GetMapping(URI_ADMIN + "/accueil")
    public ResponseEntity<String> accueilAdmin(Principal principal){
        return ResponseEntity.ok("");
    }

    /**
     * Ajoute un magasin
     * @param magasin Le magasin à ajouter
     * @return Message de confirmation
     */
    @PutMapping(URI_ADMIN + "/ajouterMagasin")
    public ResponseEntity<String> ajouterMagasin(@RequestBody Magasin magasin, Principal principal){

        try{
            Utilisateur utilisateur = facade.getUtilisateurByUsername(principal.getName());
            if(utilisateur == null){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            facade.ajouterMagasin(magasin);
            return ResponseEntity.ok("");
        }


        catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (UtilisateurInexistantException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


}
