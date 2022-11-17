DROP TABLE IF EXISTS 
`address`,
`allergen`,
`allergen_ingredient`,
`allergen_translation`,
`basket`,
`basket_line`,
`credit_card`,
`ingredient`,
`ingredient_translation`,
`language`,
`order`,
`order_line`,
`price`,
`product`,
`product_family`,
`product_family_product`,
`product_family_translation`,
`product_ingredient`,
`product_translation`,
`promotion`,
`promotion_product`,
`promotion_translation`,
`role`,
`stock_historization`,
`user`;



/* GENERAL */

CREATE TABLE `burger2home`.`language` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `creation_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `name` VARCHAR(60) NOT NULL,
  `abbreviation` VARCHAR(5) NULL,
  PRIMARY KEY (`id`));
  
INSERT IGNORE INTO burger2home.language (`id`, `creation_date` ,`name`, `abbreviation`) 
VALUES 
(1, CURRENT_TIMESTAMP() ,"ENGLISH", "EN"),
(2, CURRENT_TIMESTAMP(), "FRENCH", "FR");

/* PRODUCT */

CREATE TABLE `burger2home`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `image_url` VARCHAR(255) NULL,
  PRIMARY KEY (`id`));
  
INSERT IGNORE INTO `burger2home`.`product` (`id`, `image_url`) 
VALUES 
(1, "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/Cheeseburger.jpg/1200px-Cheeseburger.jpg"),
(2, "https://cms.burgerking.be/uploads/medium_HAMBURGER_FV_min_a6affa3bb8.png"),
(3, "data:image/jpeg;base64,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");


CREATE TABLE `burger2home`.`product_translation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` MEDIUMTEXT NULL,
  `name` VARCHAR(100) NULL,
  `product_id` INT NOT NULL,
  `language_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_product_translation_to_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `burger2home`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_translation_to_language`
    FOREIGN KEY (`language_id`)
    REFERENCES `burger2home`.`language` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT IGNORE INTO `burger2home`.`product_translation` (`id`, `description`, `name`, `product_id`, `language_id`)
VALUES
(1, "A delicious burger with a slice of cheddar.", "Cheeseburger", 1, 1),
(2, "Un délicieux burger avec une tranche de cheddar.", "Cheesburger", 1, 2),
(3, "A delicious burger with beef meet.", "Hamburger", 2, 1),
(4, "Un délicieux burger avec viande de boeuf.", "Hamburger", 2, 2),
(5, "Delicious crispy potato fries.", "Fries", 3, 1),
(6, "De délicieuses frites croustillantes.", "Frites", 3, 2);

CREATE TABLE `burger2home`.`price` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` FLOAT NOT NULL,
  `creation_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_date` DATETIME NOT NULL DEFAULT "9999-12-31 23:59:59",
  `start_date` DATETIME NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_price_to_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `burger2home`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT IGNORE INTO `burger2home`.`price` (`id`, `amount`, `creation_date`, `end_date`,`start_date`, `product_id`)
VALUES
(1, "5.99", DEFAULT, CURRENT_DATE(), "2021-01-01 20:05:01", 1),
(2, "6.99", DEFAULT, DEFAULT, CURRENT_DATE(), 1),
(3, "5.99", DEFAULT, DEFAULT, CURRENT_DATE(), 2),
(4, "2.99", DEFAULT, DEFAULT, CURRENT_DATE(), 3);
    
/* INGREDIENT */
    
CREATE TABLE `burger2home`.`ingredient` (
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));
  
INSERT IGNORE INTO `burger2home`.`ingredient` (`id`)
VALUES
(1),(2),(3);
  
CREATE TABLE `burger2home`.`ingredient_translation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) NOT NULL,
  `language_id` INT NOT NULL,
  `ingredient_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_ingredient_translation_to_language`
    FOREIGN KEY (`language_id`)
    REFERENCES `burger2home`.`language` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ingredient_translation_to_ingredient`
    FOREIGN KEY (`ingredient_id`)
    REFERENCES `burger2home`.`ingredient` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT IGNORE INTO `burger2home`.`ingredient_translation` (`id`, `name`, `language_id`, `ingredient_id`)
VALUES
(1, "beef", 1, 1),
(2, "boeuf", 2, 1),
(3, "cheddar", 1, 2),
(4, "cheddar", 2, 2),
(5, "potatoes", 1, 3),
(6, "pommes de terre", 2, 3);

    
CREATE TABLE `burger2home`.`product_ingredient` (
  `product_id` INT NOT NULL,
  `ingredient_id` INT NOT NULL,
  PRIMARY KEY (`product_id`, `ingredient_id`),
  CONSTRAINT `fk_product_ingredient_to_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `burger2home`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_ingredient_to_ingredient`
    FOREIGN KEY (`ingredient_id`)
    REFERENCES `burger2home`.`ingredient` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT IGNORE INTO `burger2home`.`product_ingredient` (`product_id`, `ingredient_id`)
VALUES
(1, 1),
(1, 2),
(2, 1),
(3, 3);
    
CREATE TABLE `burger2home`.`stock_historization` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ingredient_id` INT NOT NULL,
  `amount` INT NOT NULL,
  `creation_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_stock_historization_to_ingredient_idx` (`ingredient_id` ASC) VISIBLE,
  CONSTRAINT `fk_stock_historization_to_ingredient`
    FOREIGN KEY (`ingredient_id`)
    REFERENCES `burger2home`.`ingredient` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT IGNORE INTO `burger2home`.`stock_historization` (`id`, `ingredient_id`, `amount`, `creation_date`)
VALUES
(1, 1, 86, CURRENT_TIMESTAMP()),
(2, 1, 86, "2021-05-06 11:02:27"),
(3, 2, 94, CURRENT_TIMESTAMP()),
(4, 2, 75, "2021-05-06 11:02:27"),
(5, 3, 0, CURRENT_TIMESTAMP()),
(6, 3, 100, "2021-05-06 11:02:27");

/* ALLERGEN */
    
CREATE TABLE `burger2home`.`allergen` (
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));
  
INSERT IGNORE INTO `burger2home`.`allergen` (`id`)
VALUES
(1), (2), (3);

CREATE TABLE `burger2home`.`allergen_translation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) NOT NULL,
  `language_id` INT NOT NULL,
  `allergen_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_allergen_translation_to_language_idx` (`language_id` ASC) VISIBLE,
  INDEX `fk_allergen_translation_to_allergen_idx` (`allergen_id` ASC) VISIBLE,
  CONSTRAINT `fk_allergen_translation_to_language`
    FOREIGN KEY (`language_id`)
    REFERENCES `burger2home`.`language` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_allergen_translation_to_allergen`
    FOREIGN KEY (`allergen_id`)
    REFERENCES `burger2home`.`allergen` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT INTO `burger2home`.`allergen_translation` (`id`, `name`, `language_id`, `allergen_id`)
VALUES
(1, "Gluten", 1, 1),
(2, "Gluten", 2, 1),
(3, "Eggs", 1, 2),
(4, "Oeufs", 2, 2);
    

  
  CREATE TABLE `burger2home`.`allergen_ingredient` (
  `allergen_id` INT NOT NULL,
  `ingredient_id` INT NOT NULL,
  PRIMARY KEY (`allergen_id`, `ingredient_id`),
  INDEX `fk_allergen_ingredient_to_allergen_idx` (`allergen_id` ASC) VISIBLE,
  INDEX `fk_allergen_ingredient_to_ingredient_idx` (`ingredient_id` ASC) VISIBLE,
  CONSTRAINT `fk_allergen_ingredient_to_allergen`
    FOREIGN KEY (`allergen_id`)
    REFERENCES `burger2home`.`allergen` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_allergen_ingredient_to_ingredient`
    FOREIGN KEY (`ingredient_id`)
    REFERENCES `burger2home`.`ingredient` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT IGNORE INTO `burger2home`.`allergen_ingredient` (`allergen_id`, `ingredient_id`)
VALUES
(1, 3);


    

/* PRODUCT FAMILY */

CREATE TABLE `burger2home`.`product_family` (
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));
  
INSERT IGNORE INTO `burger2home`.`product_family` (`id`)
VALUES
(1), (2);

CREATE TABLE `burger2home`.`product_family_translation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_family_id` INT NOT NULL,
  `language_id` INT NOT NULL,
  `description` MEDIUMTEXT NULL,
  `name` VARCHAR(60) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_family_translation_to_product_family_idx` (`product_family_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_family_translation_to_language`
    FOREIGN KEY (`language_id`)
    REFERENCES `burger2home`.`language` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_family_translation_to_product_family`
    FOREIGN KEY (`product_family_id`)
    REFERENCES `burger2home`.`product_family` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT IGNORE INTO `burger2home`.`product_family_translation` (`id`, `product_family_id`, `language_id`, `description`, `name`)
VALUES
(1, 1, 1, "Delicious extras", "Sides"),
(2, 1, 2, "Un extra délicieux", "Extra"),
(3, 2, 1, "Delicious burgers", "Burgers"),
(4, 2, 2, "De delicieux burgers", "Burgers");

CREATE TABLE `burger2home`.`product_family_product` (
  `product_id` INT NOT NULL,
  `product_family_id` INT NOT NULL,
  PRIMARY KEY (`product_id`, `product_family_id`),
  INDEX `fk_product_family_product_to_product_family_idx` (`product_family_id` ASC) VISIBLE,
  INDEX `fk_product_family_product_to_product_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_family_product_to_product_family`
    FOREIGN KEY (`product_family_id`)
    REFERENCES `burger2home`.`product_family` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_family_product_to_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `burger2home`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT IGNORE INTO `burger2home`.`product_family_product` (`product_id`, `product_family_id`)
VALUES
(1, 2),
(2, 2),
(3, 1);

/* PROMOTION */

CREATE TABLE `burger2home`.`promotion` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` FLOAT NOT NULL,
  `creation_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NOT NULL DEFAULT "9999-12-31 23:59:59",
  PRIMARY KEY (`id`));
  
INSERT IGNORE INTO `burger2home`.`promotion` (`id`, `amount`, `creation_date`, `start_date`, `end_date`)
VALUES
(1, 50, DEFAULT, "2022-11-10 00:00:00", "2023-11-27 00:00:00");

CREATE TABLE `burger2home`.`promotion_translation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` MEDIUMTEXT NULL,
  `promotion_id` INT NOT NULL,
  `language_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_promotion_translation_to_promotion_idx` (`promotion_id` ASC) VISIBLE,
  INDEX `fk_promotion_translation_to_language_idx` (`language_id` ASC) VISIBLE,
  CONSTRAINT `fk_promotion_translation_to_promotion`
    FOREIGN KEY (`promotion_id`)
    REFERENCES `burger2home`.`promotion` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_promotion_translation_to_language`
    FOREIGN KEY (`language_id`)
    REFERENCES `burger2home`.`language` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT IGNORE INTO `burger2home`.`promotion_translation` (`id`, `description`, `promotion_id`, `language_id`)
VALUES
(1, "Get 30% off now !", 1, 1),
(2, "Profitez de 30% de réduction dès maintenant !", 1, 2);
    
CREATE TABLE `burger2home`.`promotion_product` (
  `promotion_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`promotion_id`, `product_id`),
  INDEX `promotion_product_to_promotion_idx` (`promotion_id` ASC) VISIBLE,
  INDEX `promotion_product_to_product_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `promotion_product_to_promotion`
    FOREIGN KEY (`promotion_id`)
    REFERENCES `burger2home`.`promotion` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `promotion_product_to_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `burger2home`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT IGNORE INTO `burger2home`.`promotion_product` (`promotion_id`, `product_id`)
VALUES
(1, 1),
(1, 2);
    
/* ROLE */

CREATE TABLE `burger2home`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NULL,
  PRIMARY KEY (`id`));
  
INSERT IGNORE INTO `burger2home`.`role` (`id`, `name`)
VALUES
(1, "admin"),
(2, "marketing"),
(3, "user");

/* USER */

CREATE TABLE `burger2home`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(70) NOT NULL,
  `firstname` VARCHAR(60) NULL,
  `lastname` VARCHAR(60) NULL,
  `image_url` VARCHAR(255) NULL,
  `password` VARCHAR(50) NOT NULL,
  `status` VARCHAR(20) NULL,
  `username` VARCHAR(60) NOT NULL,
  `role_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_to_role_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_to_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `burger2home`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT IGNORE INTO `burger2home`.`user` (`id`, `email`, `firstname`, `lastname`, `image_url`, `password`, `status`, `username`, `role_id`)
VALUES
(1, "lionel.maquet@gmail.com", "Lionel", "Maquet", NULL, "lionel", "Validated", "Lio", 1);

/* ADDRESS */

CREATE TABLE `burger2home`.`address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(45) NOT NULL,
  `zipcode` INT NOT NULL,
  `street` VARCHAR(45) NOT NULL,
  `number` INT NOT NULL,
  `extension` INT NULL,
  `note` VARCHAR(255) NULL,
  `user_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_address_to_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_address_to_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `burger2home`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT IGNORE INTO `burger2home`.`address` (`id`, `city`, `zipcode`, `street`, `number`, `extension`, `note`, `user_id`)
VALUES
(1, "Juprelle", 4450, "Rue du Tige", 120, NULL, NULL, 1);

/* BASKET */ 

CREATE TABLE `burger2home`.`basket` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `last_update` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_basket_to_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_basket_to_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `burger2home`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT IGNORE INTO `burger2home`.`basket` (`id`, `last_update`, `user_id`)
VALUES
(1, CURRENT_TIMESTAMP(), 1);

CREATE TABLE `burger2home`.`basket_line` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `basket_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `amount` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_basket_line_to_basket_idx` (`basket_id` ASC) VISIBLE,
  INDEX `fk_basket_line_to_product_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_basket_line_to_basket`
    FOREIGN KEY (`basket_id`)
    REFERENCES `burger2home`.`basket` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_basket_line_to_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `burger2home`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT IGNORE INTO `burger2home`.`basket_line` (`id`, `basket_id`, `product_id`, `amount`)
VALUES
(1, 1, 1, 1);


/* CREDIT CARD */

CREATE TABLE `burger2home`.`credit_card` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `holder_name` VARCHAR(45) NOT NULL,
  `number` VARCHAR(20) NOT NULL,
  `validity_date` DATE NOT NULL,
  `user_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_credit_card_to_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_credit_card_to_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `burger2home`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT IGNORE INTO `burger2home`.`credit_card` (`id`, `holder_name`, `number`, `validity_date`, `user_id`)
VALUES
(1, "Lionel Maquet", "1234123412341234", "2024-06-22", 1);

/* ORDER */

CREATE TABLE `burger2home`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `credit_card_id` INT NOT NULL,
  `address_id` INT NOT NULL,
  `order_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `status` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_to_user_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_order_to_credit_card_idx` (`credit_card_id` ASC) VISIBLE,
  INDEX `fk_order_to_address_idx` (`address_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_to_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `burger2home`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_to_credit_card`
    FOREIGN KEY (`credit_card_id`)
    REFERENCES `burger2home`.`credit_card` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_to_address`
    FOREIGN KEY (`address_id`)
    REFERENCES `burger2home`.`address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT IGNORE INTO `burger2home`.`order` (`id`, `user_id`, `credit_card_id`, `address_id`, `order_date`, `status`)
VALUES
(1, 1, 1, 1, CURRENT_TIMESTAMP(), "Delivered");
    
CREATE TABLE `burger2home`.`order_line` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NULL,
  `product_id` INT NOT NULL,
  `amount` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_line_to_order_idx` (`order_id` ASC) VISIBLE,
  INDEX `fk_order_line_to_product_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_line_to_order`
    FOREIGN KEY (`order_id`)
    REFERENCES `burger2home`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_line_to_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `burger2home`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT IGNORE INTO `burger2home`.`order_line` (`id`, `order_id`, `product_id`, `amount`)
VALUES
(1, 1, 1, 1),
(2, 1, 3, 1);
    
    
/* TESTING */
/*
CREATE TABLE `burger2home`.`test_burger` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `burger2home`.`testfk` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `test_burger_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_testfk_to_test_burger_idx` (`test_burger_id` ASC) VISIBLE,
  CONSTRAINT `fk_testfk_to_test_burger`
    FOREIGN KEY (`test_burger_id`)
    REFERENCES `burger2home`.`test_burger` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);*/


