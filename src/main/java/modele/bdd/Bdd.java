package modele.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Bdd {



    public void test() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/test",
                "root",
                "root");

        System.out.println("Connexion réussie");
    }

}
