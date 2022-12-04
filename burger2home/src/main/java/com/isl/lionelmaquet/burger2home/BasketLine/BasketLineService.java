package com.isl.lionelmaquet.burger2home.BasketLine;

import java.util.List;
import java.util.Optional;

public interface BasketLineService {
    List<BasketLine> getAllBasketLines();

    Optional<BasketLine> getSingleBasketLine(Integer basketLineIdentifier);

    List<BasketLine> getBasketLinesByBasket(Integer basketIdentifier);

    void createBasketLine(BasketLine basketLine);

    void modifyBasketLine(BasketLine basketLine);

    void deleteBasketLine(Integer basketLineIdentifier);
}
