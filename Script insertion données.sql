INSERT IGNORE INTO burger2home.language (`id`, `creation_date` ,`name`, `abbreviation`) 
VALUES 
(1, CURRENT_TIMESTAMP() ,"ENGLISH", "EN"),
(2, CURRENT_TIMESTAMP(), "FRENCH", "FR");



INSERT IGNORE INTO `burger2home`.`type` (`id`)
VALUES 
(1),
(2),
(3);

INSERT IGNORE INTO `burger2home`.`type_translation` (`id`,`name`, `language_id`, `type_id`)
VALUES
(1, "burgers", 1, 1),
(2, "burgers", 2, 1),
(3, "drinks", 1, 2),
(4, "boissons", 2, 2);

INSERT IGNORE INTO `burger2home`.`product` (`id`, `type_id`) 
VALUES 
(1, 1),
(2, 1),
(3, 1);

INSERT IGNORE INTO `burger2home`.`product_translation` (`id`, `description`, `name`, `product_id`, `language_id`)
VALUES
(1, "A delicious burger with a slice of cheddar.", "Cheeseburger", 1, 1),
(2, "Un délicieux burger avec une tranche de cheddar.", "Cheesburger", 1, 2),
(3, "A delicious burger with beef meet.", "Hamburger", 2, 1),
(4, "Un délicieux burger avec viande de boeuf.", "Hamburger", 2, 2),
(5, "Delicious crispy potato fries.", "Fries", 3, 1),
(6, "De délicieuses frites croustillantes.", "Frites", 3, 2);

INSERT IGNORE INTO `burger2home`.`price` (`id`, `amount`, `creation_date`, `end_date`,`start_date`, `product_id`)
VALUES
(1, "5.99", DEFAULT, "2022-12-20", "2021-01-01", 1),
(2, "6.99", DEFAULT, DEFAULT, "2022-12-20", 1),
(3, "5.99", DEFAULT, DEFAULT, CURRENT_DATE(), 2),
(4, "2.99", DEFAULT, DEFAULT, CURRENT_DATE(), 3);
    
INSERT IGNORE INTO `burger2home`.`ingredient` (`id`)
VALUES
(1),(2),(3);

INSERT IGNORE INTO `burger2home`.`ingredient_translation` (`id`, `name`, `language_id`, `ingredient_id`)
VALUES
(1, "beef", 1, 1),
(2, "boeuf", 2, 1),
(3, "cheddar", 1, 2),
(4, "cheddar", 2, 2),
(5, "potatoes", 1, 3),
(6, "pommes de terre", 2, 3);

INSERT IGNORE INTO `burger2home`.`product_ingredient` (`product_id`, `ingredient_id`)
VALUES
(1, 1),
(1, 2),
(2, 1),
(3, 3);

INSERT IGNORE INTO `burger2home`.`stock_historization` (`id`, `ingredient_id`, `amount`, `creation_date`)
VALUES
(1, 1, 86, CURRENT_TIMESTAMP()),
(2, 1, 86, "2021-05-06 11:02:27"),
(3, 2, 94, CURRENT_TIMESTAMP()),
(4, 2, 75, "2021-05-06 11:02:27"),
(5, 3, 0, CURRENT_TIMESTAMP()),
(6, 3, 100, "2021-05-06 11:02:27");

INSERT IGNORE INTO `burger2home`.`allergen` (`id`)
VALUES
(1), (2), (3);

INSERT INTO `burger2home`.`allergen_translation` (`id`, `name`, `language_id`, `allergen_id`)
VALUES
(1, "Gluten", 1, 1),
(2, "Gluten", 2, 1),
(3, "Eggs", 1, 2),
(4, "Oeufs", 2, 2);

INSERT IGNORE INTO `burger2home`.`allergen_ingredient` (`allergen_id`, `ingredient_id`)
VALUES
(1, 3);

INSERT IGNORE INTO `burger2home`.`product_family` (`id`)
VALUES
(1), (2);

INSERT IGNORE INTO `burger2home`.`product_family_translation` (`id`, `product_family_id`, `language_id`, `description`, `name`)
VALUES
(1, 1, 1, "Delicious extras", "Sides"),
(2, 1, 2, "Un extra délicieux", "Extra"),
(3, 2, 1, "Delicious burgers", "Burgers"),
(4, 2, 2, "De delicieux burgers", "Burgers");

    
INSERT IGNORE INTO `burger2home`.`product_family_product` (`product_id`, `product_family_id`)
VALUES
(1, 2),
(2, 2),
(3, 1);

INSERT IGNORE INTO `burger2home`.`promotion` (`id`, `amount`, `creation_date`, `start_date`, `end_date`)
VALUES
(1, 50, DEFAULT, "2022-11-10 00:00:00", "2023-11-27 00:00:00");

INSERT IGNORE INTO `burger2home`.`promotion_translation` (`id`, `description`, `promotion_id`, `language_id`)
VALUES
(1, "Get 30% off now !", 1, 1),
(2, "Profitez de 30% de réduction dès maintenant !", 1, 2);

INSERT IGNORE INTO `burger2home`.`promotion_product` (`promotion_id`, `product_id`)
VALUES
(1, 1),
(1, 2);

INSERT IGNORE INTO `burger2home`.`role` (`id`, `name`)
VALUES
(1, "admin"),
(2, "marketing"),
(3, "user");

INSERT IGNORE INTO `burger2home`.`user` (`id`, `email`, `firstname`, `lastname`, `image_url`, `password`, `status`, `username`, `role_id`)
VALUES
(1, "lione.maquet@gmail.com", "Lionel", "Maquet", NULL, "lionel", "Validated", "Lio", 1);

INSERT IGNORE INTO `burger2home`.`address` (`id`, `city`, `zipcode`, `street`, `number`, `extension`, `note`, `user_id`)
VALUES
(1, "Juprelle", 4450, "Rue du Tige", 120, NULL, NULL, 1);

INSERT IGNORE INTO `burger2home`.`basket` (`id`, `last_update`, `user_id`)
VALUES
(1, CURRENT_TIMESTAMP(), 1);

INSERT IGNORE INTO `burger2home`.`basket_line` (`id`, `basket_id`, `product_id`, `amount`)
VALUES
(1, 1, 1, 1);

/*
INSERT IGNORE INTO `burger2home`.`credit_card` (`id`, `last4`, `exp_month`, `exp_year`, `brand`, `payment_method_id`, `user_id`)
VALUES
(1, "Lionel Maquet", "1234123412341234", "2024-06-22", 1);*/

INSERT IGNORE INTO `burger2home`.`order` (`id`, `user_id`, `credit_card_id`, `address_id`, `order_date`, `status`)
VALUES
(1, 1, 1, 1, CURRENT_TIMESTAMP(), 'waiting_for_payment');

INSERT IGNORE INTO `burger2home`.`order_line` (`id`, `order_id`, `product_id`, `amount`)
VALUES
(1, 1, 1, 1),
(2, 1, 3, 1);
