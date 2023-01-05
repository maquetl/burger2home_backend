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
`user`,
`type`,
`type_translation`;

/* GENERAL */

CREATE TABLE `burger2home`.`language` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `creation_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `name` VARCHAR(60) NOT NULL,
  `abbreviation` VARCHAR(5) NULL,
  PRIMARY KEY (`id`));
  
   CREATE TABLE `burger2home`.`type` (
	`id` INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`id`)
  );

/* PRODUCT */

CREATE TABLE `burger2home`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `image_name` VARCHAR(255) NULL,
  `on_menu` BOOLEAN NOT NULL DEFAULT TRUE,
  `type_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_product_to_type`
    FOREIGN KEY (`type_id`)
    REFERENCES `burger2home`.`type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
  );


  
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
    
 
  
  CREATE TABLE `burger2home`.`type_translation` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `language_id` INT NOT NULL,
	`type_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_type_translation_to_type`
		FOREIGN KEY (`type_id`) 
		REFERENCES `burger2home`.`type` (`id`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
	CONSTRAINT `fk_type_translation_to_language`
    FOREIGN KEY (`language_id`)
    REFERENCES `burger2home`.`language` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
  

CREATE TABLE `burger2home`.`price` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` FLOAT NOT NULL,
  `creation_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
  `end_date` DATE DEFAULT "9999-12-31",
  `start_date` DATE NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_price_to_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `burger2home`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
/* INGREDIENT */
    
CREATE TABLE `burger2home`.`ingredient` (
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));
  
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

/* ALLERGEN */
    
CREATE TABLE `burger2home`.`allergen` (
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));


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
    

/* PRODUCT FAMILY */

CREATE TABLE `burger2home`.`product_family` (
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));


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

/* PROMOTION */

CREATE TABLE `burger2home`.`promotion` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` FLOAT NOT NULL,
  `creation_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NOT NULL DEFAULT "9999-12-31 23:59:59",
  PRIMARY KEY (`id`));


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

    
/* ROLE */

CREATE TABLE `burger2home`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NULL,
  PRIMARY KEY (`id`));
  

/* USER */

CREATE TABLE `burger2home`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(70) NULL,
  `firstname` VARCHAR(60) NULL,
  `lastname` VARCHAR(60) NULL,
  `image_url` VARCHAR(255) NULL,
  `password` VARCHAR(50) NULL,
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
  `active` BOOL NOT NULL DEFAULT true,
  PRIMARY KEY (`id`),
  INDEX `fk_address_to_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_address_to_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `burger2home`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
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
    

/* CREDIT CARD */

CREATE TABLE `burger2home`.`credit_card` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `last4` VARCHAR(20) NULL,
  `exp_month` VARCHAR(12) NULL,
  `exp_year` CHAR(4) NULL,
  `brand` VARCHAR(20) NULL,
  `payment_method_id` VARCHAR(255) NULL,
  `user_id` INT NULL,
  `active` BOOLEAN NOT NULL DEFAULT true,
  PRIMARY KEY (`id`),
  INDEX `fk_credit_card_to_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_credit_card_to_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `burger2home`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    

/* ORDER */

CREATE TABLE `burger2home`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `credit_card_id` INT NULL,
  `address_id` INT NULL,
  `order_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `status` ENUM('waiting_for_payment', 'payment_confirmed', 'shipment_confirmed', 'shipment_in_progress', 'delivered') NOT NULL,
  `payment_intent` VARCHAR(255) NULL,
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
    

CREATE TABLE `burger2home`.`order_line` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NULL,
  `product_id` INT NOT NULL,
  `amount` INT UNSIGNED NOT NULL,
  `price_id` INT NOT NULL,
  `promotion_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_line_to_order_idx` (`order_id` ASC) VISIBLE,
  INDEX `fk_order_line_to_product_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_order_line_to_price_idx` (`price_id` ASC) VISIBLE,
  INDEX `fk_order_line_to_promotion_idx` (`promotion_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_line_to_order`
    FOREIGN KEY (`order_id`)
    REFERENCES `burger2home`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_line_to_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `burger2home`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
CONSTRAINT `fk_order_line_to_price`
    FOREIGN KEY (`price_id`)
    REFERENCES `burger2home`.`price` (`id`)
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION,
CONSTRAINT `fk_order_line_to_promotion`
    FOREIGN KEY (`promotion_id`)
    REFERENCES `burger2home`.`promotion` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    );