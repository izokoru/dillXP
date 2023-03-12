package fr.dillxp.projetdill.modele.bdd;

import fr.dillxp.projetdill.modele.entity.Achat;
import fr.dillxp.projetdill.modele.entity.Magasin;
import fr.dillxp.projetdill.modele.entity.Produit;
import fr.dillxp.projetdill.modele.entity.Utilisateur;
import fr.dillxp.projetdill.modele.exception.MagasinInexistantException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class Bdd {

    private Connection connection;


    public void test() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/test",
                "root",
                "dillxp");


        System.out.println("Connexion réussie");
    }

    public Bdd() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/bdddillxp",
                "root",
                "dillxp");


        System.out.println("Connexion réussie");
    }

    /**
     * Inscrit un utilisateur
     * @param username Le username de l'utilisateur
     * @param nom Le nom de l'utilisateur
     * @param prenom Le prénom de l'utilisateur
     * @param email L'email de l'utilisateur
     * @param numTel Le numéro de téléphone de l'utilisateur
     * @throws SQLException
     */
    public void ajouterUtilisateur(String username, String nom, String prenom, String email, String numTel)
            throws SQLException {
        // Encodage mdp
        /*MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(motDePasse.getBytes(StandardCharsets.UTF_8));
        String mdpEncode = bytesToHex(encodedhash);*/

        PreparedStatement requetePreparee = this.connection.prepareStatement("INSERT INTO utilisateurs (USERNAME, nom, email, prenom, numTel) VALUES (?, ?, ?, ?, ?)");
        requetePreparee.setString(1, username);
        requetePreparee.setString(2, nom);
        requetePreparee.setString(3, email);
        requetePreparee.setString(4, prenom);
        requetePreparee.setString(5, numTel);

        System.out.println("Execution requete");

        int res = requetePreparee.executeUpdate();

    }


    /**
     * Récupère l'utilisateur par l'email
     * @param email L'email de l'utilisateur
     * @return L'utilisateur
     */
    public Utilisateur getUtilisateurByEmail(String email) throws SQLException {
        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT nom, prenom, email, numTel, USERNAME FROM utilisateurs WHERE email = ?");
        requetePreparee.setString(1, email);

        ResultSet resultSet = requetePreparee.executeQuery();

        if(resultSet.next()){
            return new Utilisateur(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
        }
        return null;
    }

    /**
     * Récupère un utilisateur grâce à son username
     * @param username Le username de l'utilisateur
     * @return L'utilisateur s'il existe
     */
    public Utilisateur getUtilisateurByUsername(String username) throws SQLException {
        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT nom, prenom, email, numTel, USERNAME FROM utilisateurs WHERE USERNAME = ?");
        requetePreparee.setString(1, username);

        ResultSet resultSet = requetePreparee.executeQuery();

        if(resultSet.next()){
            System.out.println("Utilisateur trouvé");
            return new Utilisateur(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
        }
        return null;
    }

    /**
     *Vérifie si le nom et le prénom existe ou non dans la BDD
     * @param nom Le nom de l'utilisateur
     * @param prenom Le prénom de l'utilisateur
     * @return True s'il existe False sinon
     */
    public boolean verifierUtilisateurByNomPrenom(String nom, String prenom) throws SQLException {
        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT nom FROM utilisateurs WHERE nom = ? AND prenom = ?");
        requetePreparee.setString(1, nom);
        requetePreparee.setString(2, prenom);


        ResultSet resultSet = requetePreparee.executeQuery();

        return resultSet.next();
    }

    /**
     * Vérifie si l'email est associé à un utilisateur
     * @param email L'email de l'utilisateur
     * @return true si l'email existe false sinon
     */
    public boolean verifierUtilisateurByEmail(String email) throws SQLException {
        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT email FROM utilisateurs WHERE email = ?");
        requetePreparee.setString(1, email);


        ResultSet resultSet = requetePreparee.executeQuery();

        return resultSet.next();
    }

    /**
     * Vérifier si le username est utilisé
     * @param username Le username à vérifier
     * @return true si le username existe false sinon
     */
    public boolean verifierUtilisateurByUsername(String username) throws SQLException {
        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT USERNAME FROM utilisateurs WHERE USERNAME = ?");
        requetePreparee.setString(1, username);

        ResultSet resultSet = requetePreparee.executeQuery();

        return resultSet.next();
    }

    /**
     * Récupère les produits du réfrégirateur d'un utilisateur
     * @param username Le username de l'utilisateur
     * @return Le frigo
     */
    public List<Produit> getFrigo(String username) throws SQLException {
        List<Produit> produits = new ArrayList<>();

        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT idProduit, description, reference, nom, dlc, quantite FROM refregirateur WHERE USERNAME = ?");
        requetePreparee.setString(1, username);

        ResultSet resultSet = requetePreparee.executeQuery();

        while(resultSet.next()){
            Produit p = new Produit(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4), resultSet.getString(2), resultSet.getString(5), resultSet.getInt(6));
            produits.add(p);
        }

        return produits;

    }

    /**
     * Récupère la liste des achats d'un utilisateur
     * @param username Le username de l'utilisateur
     * @return La liste des achats de l'utilisateur
     */
    public List<Achat> getListeAchats(String username) throws SQLException {

        List<Achat> achats = new ArrayList<>();

        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT a.idAchat, a.date, a.quantite, " +
                /*4*/"m.idMagasin, m.nomMagasin, m.adresseMagasin, m.numTelMagasin, m.emailMagasin, " +
                /*9*/"r.idProduit, r.nom, r.description, r.reference, r.dlc, r.quantite FROM achat AS a JOIN magasin m ON a.idMagasin = m.idMagasin JOIN refregirateur r ON a.idProduit = r.idProduit WHERE a.USERNAME = ?");
        requetePreparee.setString(1, username);

        ResultSet resultSet = requetePreparee.executeQuery();

        while(resultSet.next()){
            Produit p = new Produit(resultSet.getInt(9), resultSet.getString(12), resultSet.getString(10), resultSet.getString(11), resultSet.getString(13), resultSet.getInt(14));
            Magasin m = new Magasin(resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));

            Achat achat = new Achat(resultSet.getInt(1), resultSet.getString(2), p, resultSet.getInt(3), m);
            achats.add(achat);
        }

        return achats;
    }

    /**
     * Ajoute un achat à un utilisateur
     * @param username Le username de l'utilisateur
     * @param achat L'achat de l'utilisateur
     * @return true si l'achat a été ajouté false
     * @throws MagasinInexistantException Si le magasin n'existe pas
     */
    public boolean ajouterAchat(String username, Achat achat) throws SQLException, MagasinInexistantException {
        //Vérifier si le produit existe (s'il n'existe pas on l'ajoute dans refregirateur)

        // Vérifier si le produit est déjà dans le réfrégirateur
        Produit p = achat.getProduit();
        boolean ajoute = false;

        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT * FROM refregirateur WHERE USERNAME = ?");

        requetePreparee.setString(1, username);

        ResultSet resultSet = requetePreparee.executeQuery();

        int idProduit = -1;

        //String referenceProduit, String nomProduit, String description, String dlc, int quantite
        while (resultSet.next()){
            Produit produitTmp = new Produit(resultSet.getInt(2), resultSet.getString(4), resultSet.getString(5), resultSet.getString(3),
                    resultSet.getString(6), resultSet.getInt(8));

            if(produitTmp.equals(p)){
                // met à jour la quantité
                ajoute = true;

                PreparedStatement requete2 = this.connection.prepareStatement("UPDATE refregirateur SET quantite = ? WHERE USERNAME = ? AND idProduit = ?");
                requete2.setInt(1, produitTmp.getQuantite());
                requete2.setString(2, username);
                requete2.setInt(3, produitTmp.getIdProduit());

                requete2.executeUpdate();

                idProduit = produitTmp.getIdProduit();
            }
        }

        if(!ajoute){
            // On ajoute le produit
            requetePreparee = this.connection.prepareStatement("INSERT INTO refregirateur (USERNAME, description, reference, nom, dlc, dispo, quantite) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");

            requetePreparee.setString(1, username);
            requetePreparee.setString(2, p.getDescription());
            requetePreparee.setString(3, p.getReferenceProduit());
            requetePreparee.setString(4, p.getNomProduit());
            requetePreparee.setString(5, p.getDlc());
            requetePreparee.setInt(6, 1);
            requetePreparee.setInt(7, p.getQuantite());

            requetePreparee.executeUpdate();

            requetePreparee = this.connection.prepareStatement("SELECT idProduit FROM refregirateur WHERE nom = ? AND reference = ? AND dlc = ? AND USERNAME = ?");
            requetePreparee.setString(1, p.getNomProduit());
            requetePreparee.setString(2, p.getReferenceProduit());
            requetePreparee.setString(3, p.getDlc());
            requetePreparee.setString(4, username);

            ResultSet resultSet2 = requetePreparee.executeQuery();
            if(resultSet2.next()){
                idProduit = resultSet2.getInt(1);
            }



        }


        // Vérifier le magasin

        requetePreparee = this.connection.prepareStatement("SELECT * FROM magasin WHERE nomMagasin = ?");
        requetePreparee.setString(1, achat.getMagasin().getNomMagasin());

        ResultSet resultSet1 = requetePreparee.executeQuery();

        if(!resultSet1.next()){
            throw new MagasinInexistantException();
        }

        int idMagasin = resultSet1.getInt(1);

        // Ajouter l'achat

        requetePreparee = this.connection.prepareStatement("INSERT INTO achat (date, idProduit, quantite, USERNAME, idMagasin) VALUES (?, ?, ?, ?, ?)");
        requetePreparee.setString(1, achat.getDate());
        requetePreparee.setInt(2, idProduit);
        requetePreparee.setInt(3, achat.getQuantite());
        requetePreparee.setString(4, username);
        requetePreparee.setInt(5, idMagasin);

        int res = requetePreparee.executeUpdate();

        return res != 0;


    }

    /**
     * Ajoute un produit dans le frigo de l'utilisateur
     * @param username Le username de l'utilisateur
     * @param produit Le produit à ajouter
     * @return true si le produit a été ajouté false sinon
     */
    public boolean ajouterProduit(String username, Produit produit) throws SQLException {
        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT * FROM refregirateur WHERE USERNAME = ?");

        requetePreparee.setString(1, username);

        boolean ajoute = false;

        ResultSet resultSet = requetePreparee.executeQuery();

        int nbColAjoute = 0;


        //String referenceProduit, String nomProduit, String description, String dlc, int quantite
        while (resultSet.next()){
            Produit produitTmp = new Produit(resultSet.getInt(2), resultSet.getString(4), resultSet.getString(5), resultSet.getString(3),
                    resultSet.getString(6), resultSet.getInt(8));

            if(produitTmp.equals(produit)){
                // met à jour la quantité
                ajoute = true;

                PreparedStatement requete2 = this.connection.prepareStatement("UPDATE refregirateur SET quantite = quantite + ? WHERE USERNAME = ? AND idProduit = ?");
                requete2.setInt(1, produitTmp.getQuantite());
                requete2.setString(2, username);
                requete2.setInt(3, produitTmp.getIdProduit());



                nbColAjoute = requete2.executeUpdate();

            }
        }

        if(!ajoute){
            // On ajoute le produit
            requetePreparee = this.connection.prepareStatement("INSERT INTO refregirateur (USERNAME, description, reference, nom, dlc, dispo, quantite) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");

            requetePreparee.setString(1, username);
            requetePreparee.setString(2, produit.getDescription());
            requetePreparee.setString(3, produit.getReferenceProduit());
            requetePreparee.setString(4, produit.getNomProduit());
            requetePreparee.setString(5, produit.getDlc());
            requetePreparee.setInt(6, 1);
            requetePreparee.setInt(7, produit.getQuantite());

            nbColAjoute = requetePreparee.executeUpdate();

        }

        return nbColAjoute != 0;

    }

    /**
     * Enlève 1 à la quantité d'un produit, et le désactive si la quantité est à 0
     * @param username Le username de l'utilisateur
     * @param produit Le produit à modifier
     * @return true si la quantité a été modifiée false sinon
     */
    public boolean supprimerProduit(String username, Produit produit) throws SQLException {
        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT * FROM refregirateur WHERE USERNAME = ?");

        requetePreparee.setString(1, username);


        ResultSet resultSet = requetePreparee.executeQuery();

        int nbColAjoute = 0;


        //String referenceProduit, String nomProduit, String description, String dlc, int quantite
        while (resultSet.next()){
            Produit produitTmp = new Produit(resultSet.getInt(2), resultSet.getString(4), resultSet.getString(5), resultSet.getString(3),
                    resultSet.getString(6), resultSet.getInt(8));

            if(produitTmp.equals(produit)){

                if(produitTmp.getQuantite() == 1){
                    PreparedStatement requete2 = this.connection.prepareStatement("UPDATE refregirateur SET quantite = quantite - 1, dispo = 0 WHERE USERNAME = ? AND idProduit = ?");
                    requete2.setString(1, username);
                    requete2.setInt(2, produitTmp.getIdProduit());
                    nbColAjoute = requete2.executeUpdate();

                }

                else{
                    PreparedStatement requete2 = this.connection.prepareStatement("UPDATE refregirateur SET quantite = quantite - 1 WHERE USERNAME = ? AND idProduit = ?");
                    requete2.setString(1, username);
                    requete2.setInt(2, produitTmp.getIdProduit());
                    nbColAjoute = requete2.executeUpdate();

                }

            }
        }


        return nbColAjoute != 0;
    }



    // ---------------------------------- PARTIE ADMIN -------------------------------------

    /**
     * Ajoute un magasin
     * @param magasin Le magasin à ajouter
     * @return true si le magasin a été ajouté false sinon
     */
    public boolean ajouterMagasin(Magasin magasin) throws SQLException {

        PreparedStatement requetePreparee = this.connection.prepareStatement("INSERT INTO magasin (nomMagasin, adresseMagasin, numTelMagasin, emailMagasin) VALUE (?, ?, ?, ?)");
        requetePreparee.setString(1, magasin.getNomMagasin());
        requetePreparee.setString(2, magasin.getAdresseMagasin());
        requetePreparee.setString(3, magasin.getNumTelMagasin());
        requetePreparee.setString(4, magasin.getEmailMagasin());

        int res = requetePreparee.executeUpdate();

        return res != 0;

    }



}
