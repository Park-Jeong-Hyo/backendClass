package com.kh.myproduct.myproduct.dao;

import lombok.Data;
//게터, 세터, 등등
//데이터베이스와 연동이 되는 게 엔티티 클래스.
@Data
public class Product {
  private long productId; //PRODUCT_ID	NUMBER(10,0)
  private String pname; //PNAME	VARCHAR2(30 BYTE)
  private long quantity; //QUANTITY	NUMBER(10,0)
  private long price; // PRICE	NUMBER(10,0)
}
