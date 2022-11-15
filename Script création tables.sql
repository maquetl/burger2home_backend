/* GENERAL */

CREATE TABLE `burger2home`.`language` (
  `id` INT NOT NULL,
  `creation_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `name` VARCHAR(45) NOT NULL,
  `abbreviation` VARCHAR(5) NULL,
  PRIMARY KEY (`id`));

/* PRODUCT */

CREATE TABLE `burger2home`.`product` (
  `id` INT NOT NULL,
  `image_url` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `burger2home`.`product_translation` (
  `id` INT NOT NULL,
  `description` MEDIUMTEXT NULL,
  `name` VARCHAR(45) NULL,
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


CREATE TABLE `burger2home`.`price` (
  `id` INT NOT NULL,
  `amount` FLOAT NOT NULL,
  `creation_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_date` DATETIME NULL,
  `start_date` DATETIME NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_price_to_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `burger2home`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
/* INGREDIENT */
    
CREATE TABLE `burger2home`.`ingredient` (
  `id` INT NOT NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `burger2home`.`ingredient_translation` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
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
  `id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `ingredient_id` INT NOT NULL,
  PRIMARY KEY (`id`),
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
  `id` INT NOT NULL,
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
  `id` INT NOT NULL,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `burger2home`.`allergen_ingredient` (
  `id` INT NOT NULL,
  `allergen_id` INT NOT NULL,
  `ingredient_id` INT NOT NULL,
  PRIMARY KEY (`id`),
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

CREATE TABLE `burger2home`.`allergen_translation` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
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
    

/* PRODUCT FAMILY */

CREATE TABLE `burger2home`.`product_family` (
  `id` INT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `burger2home`.`product_family_translation` (
  `id` INT NOT NULL,
  `product_family_id` INT NOT NULL,
  `language_id` INT NOT NULL,
  `description` MEDIUMTEXT NULL,
  `name` VARCHAR(45) NULL,
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
  `id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `product_family_id` INT NOT NULL,
  PRIMARY KEY (`id`),
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
  `id` INT NOT NULL,
  `amount` FLOAT NOT NULL,
  `creation_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `start_date` DATETIME NULL,
  `end_date` DATETIME NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `burger2home`.`promotion_translation` (
  `id` INT NOT NULL,
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
  `id` INT NOT NULL,
  `promotion_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`id`),
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
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

/* USER */

CREATE TABLE `burger2home`.`user` (
  `id` INT NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  `image_url` VARCHAR(45) NULL,
  `password` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NULL,
  `username` VARCHAR(45) NOT NULL,
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
  `id` INT NOT NULL,
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

/* BASKET */ 

CREATE TABLE `burger2home`.`basket` (
  `id` INT NOT NULL,
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
  `id` INT NOT NULL,
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
  `id` INT NOT NULL,
  `holder_name` VARCHAR(45) NOT NULL,
  `number` INT NOT NULL,
  `validity_date` DATE NOT NULL,
  `user_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_credit_card_to_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_credit_card_to_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `burger2home`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

/* ORDER */

CREATE TABLE `burger2home`.`order` (
  `id` INT NOT NULL,
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
    
CREATE TABLE `burger2home`.`order_line` (
  `id` INT NOT NULL,
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

