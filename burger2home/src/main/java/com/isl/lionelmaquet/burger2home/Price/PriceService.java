package com.isl.lionelmaquet.burger2home.Price;

import java.util.List;
import java.util.Optional;

public interface PriceService {
    Optional<Price> getCurrentPriceByProductId(Integer productId);

    List<Price> getAllPricesByProduct(Integer productIdentifier);

    List<Price> getAllPrices();

    Optional<Price> getSinglePrice(Integer priceIdentifier);

    Float getCurrentPriceAfterDiscountByProductId(Integer productId);

    Price setNextPrice(Price price);

    Price setCurrentPrice(Price price);

    Optional<Price> getNextPrice(Integer productId);

    Price createDefaultCurrentPrice(Integer productId);
}
