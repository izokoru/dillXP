package fr.dillxp.projetdill.modele.entity;

public class Produit {

    private int idProduit;

    private Magasin magasin;

    private String referenceProduit;

    private String nomProduit;

    private String description;

    private String dlc;

    public Produit(){}

    public Produit(int idProduit, Magasin magasin, String referenceProduit, String nomProduit, String description, String dlc) {
        this.idProduit = idProduit;
        this.magasin = magasin;
        this.referenceProduit = referenceProduit;
        this.nomProduit = nomProduit;
        this.description = description;
        this.dlc = dlc;
    }

    public Produit(int idProduit, String referenceProduit, String nomProduit, String description, String dlc) {
        this.idProduit = idProduit;
        this.referenceProduit = referenceProduit;
        this.nomProduit = nomProduit;
        this.description = description;
        this.dlc = dlc;
    }

    public String getDlc() {
        return dlc;
    }

    public void setDlc(String dlc) {
        this.dlc = dlc;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getReferenceProduit() {
        return referenceProduit;
    }

    public void setReferenceProduit(String referenceProduit) {
        this.referenceProduit = referenceProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
