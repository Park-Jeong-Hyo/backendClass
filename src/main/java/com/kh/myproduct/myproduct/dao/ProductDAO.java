package com.kh.myproduct.myproduct.dao;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
  /**
   * 등록
   * @param product (상품아이디, 상품명, 수량, 가격)
   * @return 상품아이디
   */
  Long save(Product product);

  /**
   * 조회
   * @param productId 상품아이디
   * @return 상품
   */
  Optional<Product> findById(Long productId);
  // int로 숫자를 지정해서 0일 때 실패 1일때 성공 등등으로 표시하기 위해

  /**
   * 수정
   * @param productId 상품아이디
   * @param product 수정할 상품
   * @return 수정된 레코드수
   */
  int update(Long productId, Product product);
  //반환은 삭제 건수

  /**
   * 삭제
   * @param productId 상품아이디
   * @return 삭제된 레코드 수
   */
  int delete(Long productId);
  //결과가 여러개이기 때문에 담을 수 있는 인터페이스 리스트가 필요하다.

  /**
   * 목록
   * @return 상품목록
   */
  List<Product> findAll();
}
