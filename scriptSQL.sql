CREATE TABLE `users` (
	`USERNAME` VARCHAR(50) NOT NULL COLLATE 'latin1_swedish_ci',
	`PASSWORD` VARCHAR(68) NOT NULL COLLATE 'latin1_swedish_ci',
	`ENABLED` TINYINT(1) NOT NULL,
	PRIMARY KEY (`USERNAME`) USING BTREE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;


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
