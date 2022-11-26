package modele;

import modele.bdd.Bdd;
import org.springframework.stereotype.Component;

@Component
public class Facade {

    private Bdd bdd;


    public Facade(){
        this.bdd = new Bdd();
    }





}
