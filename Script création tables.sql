CREATE TABLE `burger2home`.`product` (
  `id` INT NOT NULL,
  `image_url` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `burger2home`.`language` (
  `id` INT NOT NULL,
  `creation_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `name` VARCHAR(45) NOT NULL,
  `abbreviation` VARCHAR(5) NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `burger2home`.`product_translation` (
  `id` INT NOT NULL,
  `description` MEDIUMTEXT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_product_translation_product`
    FOREIGN KEY (`id`)
    REFERENCES `burger2home`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_translation_language`
    FOREIGN KEY (`id`)
    REFERENCES `burger2home`.`language` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `burger2home`.`price` (
  `id` INT NOT NULL,
  `amount` FLOAT NOT NULL,
  `creation_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_date` DATETIME NULL,
  `start_date` DATETIME NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_price_product`
    FOREIGN KEY (`id`)
    REFERENCES `burger2home`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    


