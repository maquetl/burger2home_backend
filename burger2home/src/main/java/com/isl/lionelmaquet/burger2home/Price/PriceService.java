package com.isl.lionelmaquet.burger2home.Price;

import java.util.List;
import java.util.Optional;

public interface PriceService {
    Optional<Price> getCurrentPriceByProductId(Integer productId);

    List<Price> getAllPricesByProduct(Integer productIdentifier);

    List<Price> getAllPrices();

    Optional<Price> getSinglePrice(Integer priceIdentifier);

    void createPrice(Price price);

    void modifyPrice(Price price);

    void deletePrice(Integer priceIdentifier);
}
