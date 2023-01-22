package fr.dillxp.projetdill.modele.bdd;

import fr.dillxp.projetdill.modele.entity.Achat;
import fr.dillxp.projetdill.modele.entity.Magasin;
import fr.dillxp.projetdill.modele.entity.Produit;
import fr.dillxp.projetdill.modele.entity.Utilisateur;
import fr.dillxp.projetdill.modele.exception.MotDePasseDifferentsException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.mariadb.jdbc.plugin.authentication.standard.ed25519.Utils.bytesToHex;

public class Bdd {


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
                "jdbc:mariadb://localhost:3306/bdd",
                "root",
                "dillxp");


        System.out.println("Connexion réussie");
    }

    /**
     * Inscrit un utilisateur
     * @param nom
     * @param prenom
     * @param email
     * @param motDePasse
     * @param numTel
     * @param role
     * @throws NoSuchAlgorithmException
     * @throws SQLException
     */
    public void ajouterUtilisateur(String nom,String prenom, String email, String motDePasse, String numTel, String role)
            throws NoSuchAlgorithmException, SQLException {

        // Encodage mdp
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(motDePasse.getBytes(StandardCharsets.UTF_8));
        String mdpEncode = bytesToHex(encodedhash);

        PreparedStatement requetePreparee = this.connection.prepareStatement("INSERT INTO utilisateur (nom, email, prenom, numTel, role, mdp) VALUES (?, ?, ?, ?, ?, ?)");
        requetePreparee.setString(1, nom);
        requetePreparee.setString(2, email);
        requetePreparee.setString(3, prenom);
        requetePreparee.setString(4, numTel);
        requetePreparee.setString(5, role);
        requetePreparee.setString(6, mdpEncode);

        System.out.println("Execution requete");
        int i = requetePreparee.executeUpdate();
        System.out.println(i);


        //int res = requetePreparee.executeUpdate();

    }


    /**
     * Connexion à un compte
     * @param email
     * @param mdp
     * @return L'utilisateur ou null
     * @throws NoSuchAlgorithmException
     * @throws SQLException
     */
    public Utilisateur connexion(String email, String mdp) throws NoSuchAlgorithmException, SQLException {

        // Encodage mdp
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(mdp.getBytes(StandardCharsets.UTF_8));
        String mdpEncode = bytesToHex(encodedhash);

        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT nom, prenom, email, numTel, idUtilisateur FROM utilisateur WHERE email = ? AND mdp = ?");
        requetePreparee.setString(1, email);
        requetePreparee.setString(2, mdpEncode);

        ResultSet resultSet = requetePreparee.executeQuery();
        if(resultSet.next()){
            return new Utilisateur(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
        }
        return null;


    }

    /**
     * Récupère l'utilisateur par l'email
     * @param email
     * @return L'utilisateur
     */
    public Utilisateur getUtilisateurByEmail(String email) throws SQLException {
        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT nom, prenom, email, numTel, idUtilisateur FROM utilisateur WHERE email = ?");
        requetePreparee.setString(1, email);

        ResultSet resultSet = requetePreparee.executeQuery();

        if(resultSet.next()){
            return new Utilisateur(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
        }
        return null;
    }

    public Utilisateur getUtilisateurById(int id) throws SQLException {
        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT nom, prenom, email, numTel, idUtilisateur FROM utilisateur WHERE idUtilisateur = ?");
        requetePreparee.setInt(1, id);

        ResultSet resultSet = requetePreparee.executeQuery();

        if(resultSet.next()){
            return new Utilisateur(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
        }
        return null;
    }

    /**
     *Vérifie si le nom et le prénom existe ou non dans la BDD
     * @param nom
     * @param prenom
     * @return True s'il existe False sinon
     */
    public boolean verifierUtilisateurByNomPrenom(String nom, String prenom) throws SQLException {
        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT nom FROM utilisateur WHERE nom = ? AND prenom = ?");
        requetePreparee.setString(1, nom);
        requetePreparee.setString(2, prenom);


        ResultSet resultSet = requetePreparee.executeQuery();

        return resultSet.next();
    }

    /**
     * Vérifie si l'email est associé à un utilisateur
     * @param email
     * @return
     * @throws SQLException
     */
    public boolean verifierUtilisateurByEmail(String email) throws SQLException {
        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT email FROM utilisateur WHERE email = ?");
        requetePreparee.setString(1, email);


        ResultSet resultSet = requetePreparee.executeQuery();

        return resultSet.next();
    }

    /**
     * Récupère les produits du réfrégirateur d'un utilisateur
     * @param idUtilisateur
     * @return
     * @throws SQLException
     */
    public List<Produit> getFrigo(int idUtilisateur) throws SQLException {
        List<Produit> produits = new ArrayList<>();

        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT idProduit, description, reference, nom, dlc FROM refregirateur WHERE idUtilisateur = ?");
        requetePreparee.setInt(1, idUtilisateur);

        ResultSet resultSet = requetePreparee.executeQuery();

        while(resultSet.next()){
            Produit p = new Produit(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4), resultSet.getString(2), resultSet.getString(5));
            produits.add(p);
        }

        return produits;

    }

    /**
     * Modifie le mot de passe d'un utilisateur
     * @param ancienMdp
     * @param nouveauMdp
     * @param idUtilisateur
     * @return true si le mot de passe a été modifié false sinon
     * @throws SQLException
     */
    public boolean modifierMdp(String ancienMdp, String nouveauMdp, int idUtilisateur) throws SQLException, NoSuchAlgorithmException, MotDePasseDifferentsException {


        // Encodage mdp
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(ancienMdp.getBytes(StandardCharsets.UTF_8));
        String ancienMdpEncode = bytesToHex(encodedhash);

        encodedhash = digest.digest(nouveauMdp.getBytes(StandardCharsets.UTF_8));
        String nouveauMdpEncode = bytesToHex(encodedhash);

        PreparedStatement requetePreparee = this.connection.prepareStatement("UPDATE utilisateur SET mdp = ? WHERE mdp = ? AND idUtilisateur = ?");
        requetePreparee.setString(1, nouveauMdpEncode);
        requetePreparee.setString(2, ancienMdpEncode);
        requetePreparee.setInt(3, idUtilisateur);

        return requetePreparee.executeUpdate() != 0;

    }

    /**
     * Récupère la liste des achats d'un utilisateur
     * @param idUtilisateur
     * @return
     * @throws SQLException
     */
    public List<Achat> getListeAchats(int idUtilisateur) throws SQLException {

        List<Achat> achats = new ArrayList<>();

        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT a.idAchat, a.date, a.quantite, " +
                "m.idMagasin, m.nomMagasin, m.adresseMagasin, m.numTelMagasin, m.emailMagasin, " +
                "r.idProduit, r.nom, r.description, r.reference, r.dlc FROM achat AS a JOIN magasin m ON a.idMagasin = m.idMagasin JOIN refregirateur r ON a.idProduit = r.idProduit WHERE a.idUtilisateur = ?");
        requetePreparee.setInt(1, idUtilisateur);

        ResultSet resultSet = requetePreparee.executeQuery();

        while(resultSet.next()){
            Produit p = new Produit(resultSet.getInt(9), resultSet.getString(12), resultSet.getString(10), resultSet.getString(11), resultSet.getString(13));
            Magasin m = new Magasin(resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));

            Achat achat = new Achat(resultSet.getInt(1), resultSet.getString(2), p, resultSet.getInt(3), m);
            achats.add(achat);
        }

        return achats;
    }



}
