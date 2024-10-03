package com.example.ecommerce;


import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Get all products")
    void testGetAllProducts() {
        //Arrange
        Product product1 = new Product(1L, "Product 1", "Description 1", new BigDecimal(10.9), 5);
        Product product2 = new Product(2L, "Product 2", "Description 2", new BigDecimal(20.0), 10);
        List<Product> expectedProducts = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(expectedProducts);

        //Act
        List<Product> actualProducts = productService.getAllProducts();

        //Assert
        assertEquals(expectedProducts, actualProducts);
        verify(productRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("Get product by Id")
    void testGetProductById() {
            // Arrange
            Long productId = 1L;
            Product expectedProduct = new Product(productId, "Product 1", "Description 1", new BigDecimal(10.0), 5);

            when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

            // Act
            Optional<Product> actualProductOptional = productService.getProductById(productId);

            //unwrap the original
            Product actualProduct = actualProductOptional.orElseThrow(()-> new AssertionError("Product not found"));

            // Assert
            assertEquals(expectedProduct, actualProduct);
            verify(productRepository, times(1)).findById(productId);
        }

   @Test
   @DisplayName("create product")
    void testCreateProduct(){
        //Arrange
        Product productToCreate = new Product(null, "New Product", "New Description", new BigDecimal(15.0), 8);
        Product expectedProduct = new Product(1L,"New Product", "New Description", new BigDecimal(15.0), 8);

        when(productRepository.save(productToCreate)).thenReturn(expectedProduct);

        //Act
       Product actualProduct = productService.saveProduct(productToCreate);

       assertEquals(expectedProduct, actualProduct);
       verify(productRepository, times(1)).save(productToCreate);

    }


    }



