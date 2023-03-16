package com.kh.myproduct.myproduct.svc;

import com.kh.myproduct.myproduct.dao.Product;

import java.util.List;
import java.util.Optional;

public interface ProductSVC {
  long save(Product product);

  Optional<Product> findById(Long productId);

  int update(Long productId, Product product);

  int delete(Long productId);

  List<Product> findAll();
}
