package com.sumendra.ProductService.service;

import com.sumendra.ProductService.entity.ProductResponse;
import com.sumendra.ProductService.model.ProductRequest;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);
}
