

/* V1 NE PAS UTILISER */


CREATE TABLE USERS
(
    USERNAME VARCHAR(50) NOT NULL,
    PASSWORD VARCHAR(68) NOT NULL,
    ENABLED TINYINT(1) NOT NULL,
    PRIMARY KEY(USERNAME)
);

INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES('employee','$2a$10$cRqfrdolNVFW6sAju0eNEOE0VC29aIyXwfsEsY2Fz2axy3MnH8ZGa',1);
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES('manager','$2a$10$cRqfrdolNVFW6sAju0eNEOE0VC29aIyXwfsEsY2Fz2axy3MnH8ZGa',1);

CREATE TABLE AUTHORITIES
(
    USERNAME VARCHAR(50) NOT NULL,
    AUTHORITY VARCHAR(68) NOT NULL,
    FOREIGN KEY (USERNAME) REFERENCES USERS(USERNAME)
);

INSERT INTO AUTHORITIES VALUES('employee','ROLE_EMPLOYEE');
INSERT INTO AUTHORITIES VALUES('employee','ROLE_USER');
INSERT INTO AUTHORITIES VALUES('manager','ROLE_MANAGER');
INSERT INTO AUTHORITIES VALUES('manager','ROLE_USER');



CREATE TABLE `utilisateurs` (
	`USERNAME` VARCHAR(50) NULL DEFAULT NULL COLLATE 'latin1_swedish_ci',
	`nom` VARCHAR(50) NULL DEFAULT NULL COLLATE 'latin1_swedish_ci',
	`email` VARCHAR(50) NULL DEFAULT NULL COLLATE 'latin1_swedish_ci',
	`prenom` VARCHAR(50) NULL DEFAULT NULL COLLATE 'latin1_swedish_ci',
	`numtel` VARCHAR(50) NULL DEFAULT NULL COLLATE 'latin1_swedish_ci',
	INDEX `utilisateurs_users_USERNAME_fk` (`USERNAME`) USING BTREE,
	CONSTRAINT `utilisateurs_users_USERNAME_fk` FOREIGN KEY (`USERNAME`) REFERENCES `bdddillxp`.`users` (`USERNAME`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;



CREATE TABLE `refregirateur` (
	`USERNAME` VARCHAR(50) NOT NULL COLLATE 'latin1_swedish_ci',
	`idProduit` INT(50) NOT NULL,
	`description` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'latin1_swedish_ci',
	`reference` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'latin1_swedish_ci',
	`nom` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'latin1_swedish_ci',
	`dlc` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'latin1_swedish_ci',
	`dispo` TINYINT(1) NOT NULL,
	PRIMARY KEY (`idProduit`) USING BTREE,
	INDEX `FK__users` (`USERNAME`) USING BTREE,
	CONSTRAINT `FK__users` FOREIGN KEY (`USERNAME`) REFERENCES `bdddillxp`.`users` (`USERNAME`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;






CREATE TABLE `magasin` (
	`idMagasin` INT(20) NOT NULL,
	`nomMagasin` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'latin1_swedish_ci',
	`adresseMagasin` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'latin1_swedish_ci',
	`numTelMagasin` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'latin1_swedish_ci',
	`emailMagasin` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'latin1_swedish_ci',
	PRIMARY KEY (`idMagasin`) USING BTREE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;





CREATE TABLE `achat` (
	`idAchat` INT(11) NOT NULL,
	`date` VARCHAR(50) NOT NULL COLLATE 'latin1_swedish_ci',
	`idProduit` INT(11) NOT NULL,
	`quantite` INT(11) NOT NULL,
	`USERNAME` VARCHAR(50) NOT NULL COLLATE 'latin1_swedish_ci',
	`idMagasin` INT(11) NOT NULL,
	PRIMARY KEY (`idAchat`) USING BTREE,
	INDEX `FK__refregirateur` (`idProduit`) USING BTREE,
	INDEX `FK__magasin` (`idMagasin`) USING BTREE,
	CONSTRAINT `FK__magasin` FOREIGN KEY (`idMagasin`) REFERENCES `bdddillxp`.`magasin` (`idMagasin`) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT `FK__refregirateur` FOREIGN KEY (`idProduit`) REFERENCES `bdddillxp`.`refregirateur` (`idProduit`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;





/* V2 */

CREATE TABLE `utilisateurs` (
                                `USERNAME` VARCHAR(50) NULL DEFAULT NULL ,
                                `nom` VARCHAR(50) NULL DEFAULT NULL ,
                                `email` VARCHAR(50) NULL DEFAULT NULL ,
                                `prenom` VARCHAR(50) NULL DEFAULT NULL ,
                                `numtel` VARCHAR(50) NULL DEFAULT NULL ,
                                INDEX `utilisateurs_users_USERNAME_fk` (`USERNAME`),
                                CONSTRAINT `utilisateurs_users_USERNAME_fk` FOREIGN KEY (`USERNAME`) REFERENCES `users` (`USERNAME`) ON UPDATE CASCADE ON DELETE CASCADE
)
;



CREATE TABLE `refregirateur` (
                                 `USERNAME` VARCHAR(50) NOT NULL,
                                 `idProduit` INT(50) NOT NULL,
                                 `description` VARCHAR(50) NOT NULL DEFAULT '' ,
                                 `reference` VARCHAR(50) NOT NULL DEFAULT '' ,
                                 `nom` VARCHAR(50) NOT NULL DEFAULT '' ,
                                 `dlc` VARCHAR(50) NOT NULL DEFAULT '' ,
                                 `dispo` TINYINT(1) NOT NULL,
                                 PRIMARY KEY (`idProduit`) ,
                                 INDEX `FK__users` (`USERNAME`) ,
                                 CONSTRAINT `FK__users` FOREIGN KEY (`USERNAME`) REFERENCES `users` (`USERNAME`) ON UPDATE CASCADE ON DELETE CASCADE
);




CREATE TABLE `magasin` (
                           `idMagasin` INT(20) NOT NULL,
                           `nomMagasin` VARCHAR(50) NOT NULL DEFAULT '' ,
                           `adresseMagasin` VARCHAR(50) NOT NULL DEFAULT '' ,
                           `numTelMagasin` VARCHAR(50) NOT NULL DEFAULT '' ,
                           `emailMagasin` VARCHAR(50) NOT NULL DEFAULT '' ,
                           PRIMARY KEY (`idMagasin`)
)
;









CREATE TABLE `achat` (
                         `idAchat` INT(11) NOT NULL,
                         `date` VARCHAR(50) NOT NULL ,
                         `idProduit` INT(11) NOT NULL,
                         `quantite` INT(11) NOT NULL,
                         `USERNAME` VARCHAR(50) NOT NULL ,
                         `idMagasin` INT(11) NOT NULL,
                         PRIMARY KEY (`idAchat`),
                         INDEX `FK__refregirateur` (`idProduit`) ,
                         INDEX `FK__magasin` (`idMagasin`) ,
                         CONSTRAINT `FK__magasin` FOREIGN KEY (`idMagasin`) REFERENCES `magasin` (`idMagasin`) ON UPDATE CASCADE ON DELETE CASCADE,
                         CONSTRAINT `FK__refregirateur` FOREIGN KEY (`idProduit`) REFERENCES `refregirateur` (`idProduit`) ON UPDATE CASCADE ON DELETE CASCADE
);
