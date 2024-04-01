CREATE TABLE IF NOT EXISTS `transactionalService`.`deposit` (
                                                                `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                                `value` FLOAT NOT NULL,
                                                                `tax` FLOAT NOT NULL,
                                                                `client_id` BIGINT NOT NULL,
                                                                `company_id` BIGINT NOT NULL,
                                                                PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC),
    INDEX `fk_deposit_client_idx` (`client_id` ASC),
    INDEX `fk_deposit_company1_idx` (`company_id` ASC),
    CONSTRAINT `fk_deposit_client`
    FOREIGN KEY (`client_id`)
    REFERENCES `transactionalService`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_deposit_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `transactionalService`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `transactionalService`.`withdraw` (
                                                                 `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                                 `value` FLOAT NOT NULL,
                                                                 `tax` FLOAT NOT NULL,
                                                                 `client_id` BIGINT NOT NULL,
                                                                 `company_id` BIGINT NOT NULL,
                                                                 PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC),
    INDEX `fk_withdraw_client1_idx` (`client_id` ASC),
    INDEX `fk_withdraw_company1_idx` (`company_id` ASC),
    CONSTRAINT `fk_withdraw_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `transactionalService`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_withdraw_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `transactionalService`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;