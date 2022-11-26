package modele.entity;

import utils.Couple;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Frigo {

    private int idUtilisateur;

    private Map<Integer,Couple<Integer,Date>> contenu;

    public Frigo(){

    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Map<Integer, Couple<Integer, Date>> getContenu() {
        return contenu;
    }

    public void setContenu(Map<Integer, Couple<Integer, Date>> contenu) {
        this.contenu = contenu;
    }
}
