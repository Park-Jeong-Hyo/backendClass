package com.kh.myproduct.myproduct.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//slf4j 는 롬복에 있는 어노테이션이다.
// 이 클래스는 데이터베이스와 연동하는 클래스다. 라는 어노테이션
@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO{
  //이름 기반 파라미터 바인딩
  private final NamedParameterJdbcTemplate template;

  /**
   * 등록
   *
   * @param product (상품아이디, 상품명, 수량, 가격)
   * @return 상품아이디
   */
  @Override
  public Long save(Product product) {
    // sql에서 가져온 구문 맨 끝에 있는 ;은 삭제해야함
    // 문자열은 불변객체이다. 불변객체라는 건 한번 값이 초기화되서 객체가 되고 나면
    // 값을 바꿀 수 없는 객체라는 뜻이다.
    // 한번 값을 설정하고 나서는 바뀌지 않기 때문에 오류가 날 가능성이 없는
    // 안전한 객체라는 뜻이다. 불변객체 = 안전한 객체
    // 변수는 여러번 바뀌기 때문에 오류가 날 수 있고 어디서 오류가 났는지 찾는 과정이 필요할 수 있다.
    // string buffer를 만들면 새로운 객체를 만드는게 아니라 기존의 객체에 덮어 씌우기를 한다.
    // 이렇게 하지 않으면 문자열은 객체를 여러개 생성하게 된다.(아래와 같은 경우 3가지를 생성
    StringBuffer sb = new StringBuffer();
    //맨 마지막에 무조건 띄어쓰기를 해야한다. 그렇지 않으면 붙여져서 나오고, sql 프로그램에서는 오류가 난다.
    sb.append("insert into product(product_id,pname,quantity,price) ");
    sb.append("values(product_product_id_seq.nextval, :pname, :quantity, :price) ");
    // 아래의 예문은 객체를 3가지 생성한다.
    //    String sql = "insert into product(product_id,pname,quantity,price) " +
    //  ("values(product_product_id_seq.nextval, :pname, :quantity, :price) ";

    //프로덕트 객체를 읽어서 맴버 필드, 게터 매소드를 통해서 값을 읽어서 치환을 한다.
    //쿼리 파라미터를 지정하는 방법중의 하나
    //SqlParameterSource: 파라미터값을 sqlParameter로 넣어주기위한 인터페이스
    //BeanPropertySqlParameterSource(): sqlParameterSouce의 구현체, 자바 빈 객체로부터
    // 정보를 받아와 파라미터값을 매핑해준다.
    SqlParameterSource param = new BeanPropertySqlParameterSource(product);

    // keyholder의 구현클래스인 generatedkeyholder 객체 생성
    // insert 구문에 자동으로 증가하는 seq.nextval이 들어가 있으므로 칼럼의 값이 지정되지 않는다.
    KeyHolder keyHolder = new GeneratedKeyHolder();
    //인서트를 하는 구문
    // 첫번째 값: sql 구문 저장 string 객체
    // 두번째 값:파라미터를 저장한 sqlparametersource 객체 또는 map
    // 세번째 값: 자동으로 증가하는 숫자(키,아이디) 값
    // 서버에 값 저장
    template.update(sb.toString(), param, keyHolder, new String[]{"product_id"});
    //인서트 앞 테이블의 키를 받아온다.? 왜 받아오는지 모름
    // 프로덕트 아이디의 타입이 롱 타입이기 때문에 롱밸류
    long productId = keyHolder.getKey().longValue(); //상품아이디
    return productId;
  }

  /**
   * 조회
   *
   * @param productId 상품아이디
   * @return 상품
   */
  @Override
  //제네릭으로 프로덕트를
  // 반환값 product가 있을 수도 있고 없을 수도 있다는 뜻
  // product가 없다고 하더라도 optional이라는 객체를 반환한다.
  // 따라서 null체크를 할 필요성이 없다.
  public Optional<Product> findById(Long productId) {
    StringBuffer sb = new StringBuffer();
    sb.append("select product_id, pname, quantity, price ");
    sb.append("  from product ");
    sb.append(" where product_id = id ");

    try {
      //template.queryforObject는 레코드가 하나인 경우에 사용
      Map<String, Long> param = Map.of("id", productId);
      //첫번째 매개값: sql querry, 두번째 어떤 형태로 할건지, 세번째 결과값을 담을 자바객체(리턴됨)
      Product product = template.queryForObject(sb.toString(), param, BeanPropertyRowMapper.newInstance(Product.class));
      //Optional의 of는 뭐지? 정적메소드라는데..
      return Optional.of(product);
    } catch (EmptyResultDataAccessException e) {
      //조회 결과가 없는 경우
      return Optional.empty();
    }
  }

  /**
   * 수정
   *
   * @param productId 상품아이디
   * @param product   수정할 상품
   * @return 수정된 레코드수
   */
  @Override
  public int update(Long productId, Product product) {
    StringBuffer sb = new StringBuffer();
    sb.append("update product");
    sb.append("   set pname = :'pname, ");
    sb.append("       quantity = :quantity, ");
    sb.append("       price = :price");
    sb.append(" where product_id = :Id");

    SqlParameterSource parm = new MapSqlParameterSource()
      .addValue("pname", product.getPname())
        .addValue("quantity", product.getQuantity())
        .addValue("price", product.getPrice())
        .addValue("productId", productId);

    return template.update(sb.toString(),parm);
  }

  /**
   * 삭제
   *
   * @param productId 상품아이디
   * @return 삭제된 레코드 수
   */
  @Override
  public int delete(Long productId) {
    StringBuffer sb = new StringBuffer();
    String sql = "delete from pdoruct where product_id = :Id";
    // template는 수정이건 삭제건 update를 사용한다.
    return template.update(sql, Map.of("id", productId));
  }

  /**
   * 목록
   *
   * @return 상품목록
   */

  //레코드가 여러개일 때는 template.query를 사용한다.
  @Override
  public List<Product> findAll() {
    StringBuffer sb = new StringBuffer();
    sb.append("select product_id, pname, quantity, price");
    sb.append(" from product");

        //
    List<Product> list = template.query(
        sb.toString(),
        //레코드 칼럼과 자바 객체 맴버필드가 동일한 이름일 경우, camelcase지원
        BeanPropertyRowMapper.newInstance(Product.class)
    );
    return list;
  }
}
