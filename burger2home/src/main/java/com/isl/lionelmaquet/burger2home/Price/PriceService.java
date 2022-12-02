package com.isl.lionelmaquet.burger2home.Price;

import java.util.Optional;

public interface PriceService {
    Optional<Price> getCurrentPriceByProductId(Integer productId);
}
