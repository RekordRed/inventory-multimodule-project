package com.callmextrm.product_service.product;

import com.callmextrm.product_service.exception.Discontinued;
import com.callmextrm.product_service.exception.Quantity;
import com.callmextrm.product_service.exception.ResourceNotFound;
import com.callmextrm.product_service.kafka.ProductEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.callmextrm.events.ProductCreatedEvent;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
@Slf4j
@Service
public class ProductService {
    private final Productrepo productDao;
    private final ProductEventProducer producer;


    public ProductService(Productrepo productDao, ProductEventProducer producer) {
        this.productDao = productDao;
        this.producer = producer;
    }

    //Add product SERVICE
    public Product addProduct(Product product, Integer initialQuantity) {
        if (initialQuantity == null || initialQuantity < 0) {
            log.error("Invalid quantity {} ", initialQuantity);
            throw new Quantity("Quantity cannot be negative");
        }


        product.setStatus(Status.Active);
        log.info("Creating product: name={}, initial quantity={}", product.getName(), initialQuantity);

        Product saved = productDao.save(product);
        log.info("Product saved with id={}", saved.getId());

        producer.publishProductCreated(new ProductCreatedEvent(saved.getId(), initialQuantity));
        log.info("ProductCreatedEvent published for productId={}", saved.getId());

        return saved;
    }

    //Get product by id SERVICE
    public Product getProductById(Long id) {
        return productDao.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Product not found: " + id));
    }

    //Get all products SERVICE
    public List<Product> getAllProducts() {
        return productDao.findAll();
    }


    //Delete product SERVICE
    public void deleteProduct(Long id) {
        if (!productDao.existsById(id)) {
            throw new ResourceNotFound("Product not found: " + id);
        }
        productDao.deleteById(id);
    }

    //Discontinue products SERVICE
    public Product discontinueProduct(Long id) {
        Product product = productDao.findById(id).orElseThrow(() -> new ResourceNotFound("Product not found"));
        if (product.getStatus() == Status.Discontinued) {
            throw new Discontinued("The product is already Discontinued");
        } else
            product.setStatus(Status.Discontinued);
        return productDao.save(product);


    }
}
