package modele;

import modele.bdd.Bdd;
import modele.entity.Utilisateur;
import org.springframework.stereotype.Component;

@Component
public class Facade {

    private Bdd bdd;


    public Facade(){
        this.bdd = new Bdd();
    }

    public void ajouterUtilisateur(String nom,String prenom,String email,String motDepasse){
        Utilisateur user = new Utilisateur(nom,prenom,email);

    }





}
