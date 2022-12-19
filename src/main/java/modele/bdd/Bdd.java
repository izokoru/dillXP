package modele.bdd;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

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

    public void ajouterUtilisateur(String nom,String prenom, String email, String motDepasse, String numTel, String role)
            throws NoSuchAlgorithmException, SQLException {

        // Encodage mdp
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(motDepasse.getBytes(StandardCharsets.UTF_8));
        String mdpEncode = bytesToHex(encodedhash);

        //PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT login, mdp, nom, prenom, droits FROM utilisateur WHERE login = ? AND mdp = ?");
        /*requetePreparee.setString(1, login);
        requetePreparee.setString(2, mdpEncode);
        */

        PreparedStatement requetePreparee = this.connection.prepareStatement("INSERT INTO utilisateur (nom, email, prenom, numTel, role) VALUES (?, ?, ?, ?, ?)");
        requetePreparee.setString(1, nom);
        requetePreparee.setString(2, email);
        requetePreparee.setString(3, prenom);
        requetePreparee.setString(4, numTel);
        requetePreparee.setString(5, role);

        int res = requetePreparee.executeUpdate();


    }

    /**
     * Vérifie si l'email existe ou non dans la BDD
     * @param email
     * @return True s'il existe False sinon
     */
    public boolean getUtilisateurByEmail(String email) throws SQLException {
        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT email FROM utilisateur WHERE email = ?");
        requetePreparee.setString(1, email);

        ResultSet resultSet = requetePreparee.executeQuery();

        return resultSet.next();
    }

    /**
     *Vérifie si le nom et le prénom existe ou non dans la BDD
     * @param nom
     * @param prenom
     * @return True s'il existe False sinon
     */
    public boolean getUtilisateurByNomPrenom(String nom, String prenom) throws SQLException {
        PreparedStatement requetePreparee = this.connection.prepareStatement("SELECT nom FROM utilisateur WHERE nom = ? AND prenom = ?");
        requetePreparee.setString(1, nom);
        requetePreparee.setString(2, prenom);


        ResultSet resultSet = requetePreparee.executeQuery();

        return resultSet.next();
    }



}
