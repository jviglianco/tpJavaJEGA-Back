package com.tpJEG.model;

import com.tpJEG.repository.ProductRepository;

import javax.inject.Inject;
import java.util.List;

public class TpJavaTest {
    @Inject
    private ProductRepository service;
    public static void main(String[] args) {

        ProductRepository service = new ProductRepository();

        List<Product> products = service.findAll();
        for (Product p : products)
            System.out.println("Productos encotrados ante del delete: " + p);
        try {
            service.delete((long) 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Product p : products)
            System.out.println("Productos encotrados despues del delete: " + p);

    }
}
