package fr.dillxp.projetdill.modele.entity;

public class Produit {

    private int idProduit;

    private Magasin magasin;

    private String referenceProduit;

    private String nomProduit;

    private String description;

    private String dlc;

    private int quantite;

    public Produit(){}

    public Produit(int idProduit, String referenceProduit, String nomProduit, String description, String dlc, int quantite) {
        this.idProduit = idProduit;
        this.referenceProduit = referenceProduit;
        this.nomProduit = nomProduit;
        this.description = description;
        this.dlc = dlc;
        this.quantite = quantite;
    }

    public Produit(String referenceProduit, String nomProduit, String description, String dlc, int quantite) {
        this.referenceProduit = referenceProduit;
        this.nomProduit = nomProduit;
        this.description = description;
        this.dlc = dlc;
        this.quantite = quantite;
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

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produit)) return false;
        Produit produit = (Produit) o;
        return referenceProduit.equals(produit.referenceProduit) && nomProduit.equals(produit.nomProduit) && dlc.equals(produit.dlc);
    }

}
