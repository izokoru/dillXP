package fr.dillxp.projetdill.modele.entity;

public class Magasin {

    private int idMagasin;

    private String nomMagasin;

    private String adresseMagasin;

    private String numTelMagasin;

    private String emailMagasin;

    public Magasin(String nomMagasin){
        this.nomMagasin = nomMagasin;
    }

    public Magasin(int idMagasin, String nomMagasin, String adresseMagasin, String numTelMagasin, String emailMagasin) {
        this.idMagasin = idMagasin;
        this.nomMagasin = nomMagasin;
        this.adresseMagasin = adresseMagasin;
        this.numTelMagasin = numTelMagasin;
        this.emailMagasin = emailMagasin;
    }

    public Magasin(String nomMagasin, String adresseMagasin, String numTelMagasin, String emailMagasin) {
        this.nomMagasin = nomMagasin;
        this.adresseMagasin = adresseMagasin;
        this.numTelMagasin = numTelMagasin;
        this.emailMagasin = emailMagasin;
    }

    public int getIdMagasin() {
        return idMagasin;
    }

    public void setIdMagasin(int idMagasin) {
        this.idMagasin = idMagasin;
    }

    public String getNomMagasin() {
        return nomMagasin;
    }

    public void setNomMagasin(String nomMagasin) {
        this.nomMagasin = nomMagasin;
    }

    public String getAdresseMagasin() {
        return adresseMagasin;
    }

    public void setAdresseMagasin(String adresseMagasin) {
        this.adresseMagasin = adresseMagasin;
    }

    public String getNumTelMagasin() {
        return numTelMagasin;
    }

    public void setNumTelMagasin(String numTelMagasin) {
        this.numTelMagasin = numTelMagasin;
    }

    public String getEmailMagasin() {
        return emailMagasin;
    }

    public void setEmailMagasin(String emailMagasin) {
        this.emailMagasin = emailMagasin;
    }
}
