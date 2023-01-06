package modele.bdd;

import modele.entity.Produit;
import modele.entity.Utilisateur;

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
                "root");

        System.out.println("Connexion réussie");
    }

    /**
     * Inscrit un utilisateur
     * @param nom
     * @param prenom
     * @param email
     * @param motDepasse
     * @param numTel
     * @param role
     * @throws NoSuchAlgorithmException
     * @throws SQLException
     */
    public void ajouterUtilisateur(String nom,String prenom, String email, String motDepasse, String numTel, String role)
            throws NoSuchAlgorithmException, SQLException {

        // Encodage mdp
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(motDepasse.getBytes(StandardCharsets.UTF_8));
        String mdpEncode = bytesToHex(encodedhash);

        PreparedStatement requetePreparee = this.connection.prepareStatement("INSERT INTO utilisateur (nom, email, prenom, numTel, role, mdp) VALUES (?, ?, ?, ?, ?, ?)");
        requetePreparee.setString(1, nom);
        requetePreparee.setString(2, email);
        requetePreparee.setString(3, prenom);
        requetePreparee.setString(4, numTel);
        requetePreparee.setString(5, role);
        requetePreparee.setString(6, mdpEncode);


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
    public boolean modifierMdp(String ancienMdp, String nouveauMdp, int idUtilisateur) throws SQLException {

        PreparedStatement requetePreparee = this.connection.prepareStatement("UPDATE utilisateur SET mdp = ? WHERE mdp = ? AND idUtilisateur = ?");
        requetePreparee.setString(1, ancienMdp);
        requetePreparee.setString(2, nouveauMdp);
        requetePreparee.setInt(3, idUtilisateur);

        return requetePreparee.executeUpdate() != 0;

    }



}
