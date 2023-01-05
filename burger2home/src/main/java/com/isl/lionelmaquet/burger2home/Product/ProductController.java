package com.isl.lionelmaquet.burger2home.Product;

import com.isl.lionelmaquet.burger2home.Utils.PathUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products/summaries")
    public List<ProductBO> Index(@RequestParam(defaultValue = "EN") String language,
                                 @RequestParam(defaultValue = "false") Boolean availableProductsOnly,
                                 @RequestParam(required = false, name = "productFamily") List<Integer> productFamilyIdentifiers,
                                 @RequestParam(defaultValue = "false", name ="mustBeOnMenu") boolean onMenu,
                                 @RequestParam(required = false) Integer type
                                 ){
        return productService.getProductBOs(language, availableProductsOnly, productFamilyIdentifiers, onMenu, type);
    }

    @GetMapping("/products/summaries/{productIdentifier}")
    public Optional<ProductBO> getSingleProductBO(@PathVariable Integer productIdentifier,
                                                @RequestParam(defaultValue = "EN") String language){
        return productService.getSingleProductBO(productIdentifier, language);
    }

    @GetMapping("/products/{productIdentifier}")
    public Optional<Product> getSingleProduct(@PathVariable Integer productIdentifier){
        return productService.getSingleProduct(productIdentifier);
    }

    @GetMapping("/products")
    public List<Product> getProducts(@RequestParam(required = false, name = "productFamily") List<Integer> productFamilyIdentifiers,
                                        @RequestParam(defaultValue = "false", name ="mustBeOnMenu") boolean onMenu,
                                     @RequestParam(defaultValue = "false") Boolean availableProductsOnly,
                                     @RequestParam(required = false) Integer type){
        return productService.getProducts(productFamilyIdentifiers, onMenu, availableProductsOnly, type);
    }

    @PostMapping("/products")
    public Product createNewProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @PutMapping("/products")
    public Product modifyProduct(@RequestBody Product product){
        return productService.modifyProduct(product);
    }

    @PostMapping("/products/{productIdentifier}/image")
    public Product uploadImage(@PathVariable Integer productIdentifier, @RequestParam("image")MultipartFile image) throws IOException {
        byte[] bytes = image.getBytes();
        Files.createDirectories(Paths.get(PathUtil.getImagesPath()));
        String extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf(".") + 1);
        String generatedImageName = RandomStringUtils.randomAlphanumeric(20) + "." + extension;

        // Check that file with generated name doesn't already exist
        while(new File(PathUtil.getImagesPath() + generatedImageName).exists()){
            generatedImageName = RandomStringUtils.randomAlphanumeric(20) + "." + extension;
        }

        Path path = Paths.get(PathUtil.getImagesPath() + generatedImageName);
        Files.write(path, bytes);

        Product product = productService.getSingleProduct(productIdentifier).get();
        product.setImageName(generatedImageName);
        productService.modifyProduct(product);

        return product;
    }

    @GetMapping("/products/{productIdentifier}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer productIdentifier) throws IOException {

        Product product = productService.getSingleProduct(productIdentifier).get();
        if (product.getImageName() == null) return null;

        Path path = Paths.get(PathUtil.getImagesPath() + product.getImageName());

        File file = new File(path.toString());
        if(!file.exists()) return null;

        byte[] bytes = Files.readAllBytes(path);

        HttpHeaders headers = new HttpHeaders();

        String extension = product.getImageName().substring(product.getImageName().lastIndexOf(".") + 1);
        switch (extension){
            case "png" :
                headers.setContentType(MediaType.IMAGE_PNG);
                break;
            case "gif":
                headers.setContentType(MediaType.IMAGE_GIF);
                break;
            default:
                headers.setContentType(MediaType.IMAGE_JPEG);
                break;
        }
        return ResponseEntity.ok().headers(headers).body(bytes);
    }

}
