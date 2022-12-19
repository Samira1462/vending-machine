package com.app.vendingmachine.controller;

import com.app.vendingmachine.dto.ProductDto;
import com.app.vendingmachine.entity.Product;
import com.app.vendingmachine.exception.BadRequestException;
import com.app.vendingmachine.exception.ObjectNotFoundException;
import com.app.vendingmachine.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ModelMapper mapper;
    @Autowired
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) throws ObjectNotFoundException {
        return ResponseEntity.ok(productService.getProduct(id).stream()
                .map(this::convertToProductDto).findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("user not found for this id :: " + id))
        );
    }

    @GetMapping(path = "/getAll", produces = "application/json")
    public ResponseEntity<List<ProductDto>> getAllProduct() {
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getAllProduct().stream()
                        .map(this::convertToProductDto)
                        .collect(Collectors.toList()));
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    @PreAuthorize("authentication.principal.username == @productService.findById(#id).getSeller().getUsername() && hasRole('ROLE_SELLER')")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        productService.delete(id);
        return ResponseEntity.ok().body("deleted user :: " + id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<String> add(@RequestBody ProductDto productDto) {

        productService.add(convertToProduct(productDto));
        return ResponseEntity.ok().body(" saved product");
    }

    @PutMapping("/{id}")
    @PreAuthorize("authentication.principal.username == @productService.findById(#id).getSeller().getUsername() && hasRole('ROLE_SELLER')")
    public ResponseEntity update(@RequestBody ProductDto productDto, @PathVariable("id") Long id) throws BadRequestException {
        Product update = productService.update(productDto, id);
        if (update == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("could not find product");
        }
        return ResponseEntity.ok(update);
    }

    private ProductDto convertToProductDto(Product product) {
        return mapper.map(product, ProductDto.class);
    }

    private Product convertToProduct(ProductDto productDto) {
        return mapper.map(productDto, Product.class);
    }
}
