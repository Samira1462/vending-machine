package com.app.vendingmachine.service;

import com.app.vendingmachine.dto.ProductDto;
import com.app.vendingmachine.entity.Product;
import com.app.vendingmachine.exception.BadRequestException;
import com.app.vendingmachine.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public void delete(long id) {
        productRepository.deleteById(id);
    }

    public void add(Product product) {
        productRepository.saveAndFlush(product);
    }

    public Product update(ProductDto productDto, Long id) throws BadRequestException {

        if (productRepository.findById(id).isPresent()) {
            Product savedProduct = productRepository.findById(id).get();
            savedProduct.setProductName(productDto.getProductName());
            savedProduct.setCost(productDto.getCost());
            savedProduct.setAmountAvailable(productDto.getAmountAvailable());
            return productRepository.saveAndFlush(savedProduct);
        }
        return null;
    }
}
