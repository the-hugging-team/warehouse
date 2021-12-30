package com.the.hugging.team.warehouse;

import com.the.hugging.team.entities.Product;
import com.the.hugging.team.services.ProductCategoryService;
import com.the.hugging.team.services.ProductQuantityTypeService;
import com.the.hugging.team.services.ProductService;
import com.the.hugging.team.services.StorageService;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

    private static Product newProduct;
    private final ProductService productService = ProductService.getInstance();

    @Test
    @Order(1)
    @DisplayName("Should add product")
    void shouldAddProduct() {
        Product product = new Product();
        product.setName("test");
        product.setNomenclature("test");
        product.setProductCategory(ProductCategoryService.getInstance().getAllProductCategories().get(1));
        product.setQuantity(100.0);
        product.setProductQuantityType(ProductQuantityTypeService.getInstance().getAllProductQuantityTypes().get(1));
        product.setRetailPrice(100.0);
        product.setWholesalePrice(100.0);
        product.setDeliveryPrice(100.0);
        product.setShelf(StorageService.getInstance().getAllShelves().get(1));
        newProduct = productService.addProduct(product);

        Assertions.assertTrue(productService.getAllProducts().contains(newProduct));
    }

    @Test
    @Order(2)
    @DisplayName("Should update product")
    void shouldUpdateProduct() {
        newProduct.setName("testUPDATE");
        productService.updateProduct(newProduct);

        Assertions.assertEquals("testUPDATE", newProduct.getName());
    }

    @Test
    @Order(3)
    @DisplayName("Should delete product")
    void shouldDeleteProduct() {
        productService.deleteProduct(newProduct);

        Assertions.assertFalse(productService.getAllProducts().contains(newProduct));
    }
}
