package modele.entity;

public class Achat {


    private int idAchat;

    private String date;

    private Produit produit;

    private int quantite;

    private Magasin magasin;


    public Achat() {}

    public Achat(int idAchat, String date, Produit produit, int quantite, Magasin magasin) {
        this.idAchat = idAchat;
        this.date = date;
        this.produit = produit;
        this.quantite = quantite;
        this.magasin = magasin;
    }

    public int getIdAchat() {
        return idAchat;
    }

    public void setIdAchat(int idAchat) {
        this.idAchat = idAchat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }
}
