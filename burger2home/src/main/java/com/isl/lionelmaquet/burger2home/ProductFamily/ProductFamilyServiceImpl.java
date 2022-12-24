package com.isl.lionelmaquet.burger2home.ProductFamily;

import com.isl.lionelmaquet.burger2home.Product.Product;
import com.isl.lionelmaquet.burger2home.Product.ProductService;
import com.isl.lionelmaquet.burger2home.ProductFamily.Translation.ProductFamilyTranslation;
import com.isl.lionelmaquet.burger2home.ProductFamily.Translation.ProductFamilyTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductFamilyServiceImpl implements ProductFamilyService {

    @Autowired
    ProductFamilyRepository productFamilyRepository;

    @Autowired
    ProductFamilyTranslationService productFamilyTranslationService;

    @Autowired
    ProductService productService;


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
        ProductFamily productFamily = productFamilyRepository.findById(productFamilyIdentifier).get();

        for (ProductFamilyTranslation pft : productFamilyTranslationService.getProductFamilyTranslationsByProductFamily(productFamilyIdentifier)){
            productFamilyTranslationService.deleteSingleProductFamilyTranslation(pft.getId());
        }

        for (Product product : productFamily.getProducts()){
            product.getProductFamilies().remove(productFamily);
        }

        productFamilyRepository.deleteById(productFamilyIdentifier);
    }
}
