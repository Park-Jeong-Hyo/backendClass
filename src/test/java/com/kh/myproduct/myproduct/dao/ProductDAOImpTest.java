package com.kh.myproduct.myproduct.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

@SpringBootTest
public class ProductDAOImpTest {
  @Autowired
  ProductDAO productDAO;

  //등록
  @Test
  void save(){
   Product product = new Product();
   product.setPname("복사기");
   product.setQuantity(10L);
   product.setPrice(1000000L);

   productDAO.save(product);

   Long productId = productDAO.save(product);
    System.out.println("productId=" + productId);
  }
  //조회
  @Test
  void findById() {
    Long productId = 151L;
    Optional<Product> product = productDAO.findById(productId);
    if(product.isPresent()) {
      System.out.println("product= " + product.get());
    } else {
      System.out.println("조회한 결과 없음");
    }
  }
}
