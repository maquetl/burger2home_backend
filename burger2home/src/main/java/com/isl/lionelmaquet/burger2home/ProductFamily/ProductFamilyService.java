package com.isl.lionelmaquet.burger2home.ProductFamily;

import java.util.List;
import java.util.Optional;

public interface ProductFamilyService {
    List<ProductFamily> getAllProductFamilies();

    Optional<ProductFamily> getSingleProductFamily(Integer productFamilyIdentifier);

    List<ProductFamily> getProductFamiliesByProduct(Integer productIdentifier);

    void createProductFamily(ProductFamily productFamily);

    void modifyProductFamily(ProductFamily productFamily);

    void deleteProductFamily(Integer productFamilyIdentifier);
}
