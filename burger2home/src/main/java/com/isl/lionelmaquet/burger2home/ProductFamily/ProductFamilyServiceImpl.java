package com.isl.lionelmaquet.burger2home.ProductFamily;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductFamilyServiceImpl implements ProductFamilyService {

    @Autowired
    ProductFamilyRepository productFamilyRepository;


    @Override
    public List<ProductFamily> getAllProductFamilies() {
        return productFamilyRepository.findAll();
    }

    @Override
    public Optional<ProductFamily> getSingleProductFamily(Integer productFamilyIdentifier) {
        return productFamilyRepository.findById(productFamilyIdentifier);
    }

    @Override
    public List<ProductFamily> getProductFamiliesByProduct(Integer productIdentifier) {
        return productFamilyRepository.findByProductsId(productIdentifier);
    }

    @Override
    public ProductFamily createProductFamily(ProductFamily productFamily) {
        return productFamilyRepository.save(productFamily);
    }

    @Override
    public ProductFamily modifyProductFamily(ProductFamily productFamily) {
        return productFamilyRepository.save(productFamily);
    }

    @Override
    public void deleteProductFamily(Integer productFamilyIdentifier) {
        productFamilyRepository.deleteById(productFamilyIdentifier);
    }
}
