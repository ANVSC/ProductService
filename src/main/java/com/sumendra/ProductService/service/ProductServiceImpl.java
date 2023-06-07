package com.sumendra.ProductService.service;

import com.sumendra.ProductService.entity.Product;
import com.sumendra.ProductService.entity.ProductResponse;
import com.sumendra.ProductService.exception.ProductServiceCustomException;
import com.sumendra.ProductService.model.ProductRequest;
import com.sumendra.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding product with the request: " + productRequest.toString());
        Product product = Product.builder()
                .productName(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        productRepository.save(product);
        log.info("Product Created : "+product.toString());
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Get the product for productID");
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductServiceCustomException("Product with the given Id is not found","PRODUCT_NOT_FOUND"));
        ProductResponse productResponse = new ProductResponse();
        copyProperties(product,productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reducing Quantity of the product : {} by {} units",productId,quantity);
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ProductServiceCustomException("Product Not found with the give Id","PRODUCT_NOT_FOUND"));
        if(product.getQuantity()<quantity)
        {
            throw new ProductServiceCustomException("Product is not having enough quantity","INSUFFICIENT_QUANTITY");
        }
        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
        log.info("Product Quantity updated successfully");
    }
}
