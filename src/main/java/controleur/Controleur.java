package controleur;


import modele.Facade;
import modele.entity.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controleur {

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
                                                   @RequestParam String email, @RequestParam String mdp){



    }




}
