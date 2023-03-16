package com.kh.myproduct.myproduct.svc;

import com.kh.myproduct.myproduct.dao.Product;
import com.kh.myproduct.myproduct.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//svc의 구현체라는 의미의 어노테이션
// 아래와 같은 상태이면 서비스단에서는 딱히 구현을 할 게 없다.
// 받은 정보를 dao단으로 넘겨 주고 있다.
@Service
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC{

  private final ProductDAO productDAO;
  @Override
  public long save(Product product) {
    return productDAO.save(product);
  }

  @Override
  public Optional<Product> findById(Long productId) {
    return productDAO.findById(productId);
  }

  @Override
  public int update(Long productId, Product product) {
    return productDAO.update(productId, product);
  }

  @Override
  public int delete(Long productId) {
    return productDAO.delete(productId);
  }

  @Override
  public List<Product> findAll() {
    return productDAO.findAll();
  }
}
