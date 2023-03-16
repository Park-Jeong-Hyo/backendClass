package com.kh.myproduct.myproduct.web;

import com.kh.myproduct.myproduct.dao.Product;
import com.kh.myproduct.myproduct.svc.ProductSVC;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//컨트롤러의 역할을 하는 것을 알린다.
@Controller
// requestmapping을 통해 맵핑
@RequestMapping("/products")
// 맴버필드중에 fianl키워드가 붙은 걸 매개값으로 하는
// 생성자를 만드는 어노테이션
// 롬복의 어노테이션이다.
@RequiredArgsConstructor
public class ProductController {
  //svc와 컨트롤러를 연결한다.
  // 연결하는 방법은 참조이다.
  // 컨트롤러는 svc의 인터페이스를 참조하고 있기 때문에
  // 어떤식으로 인터페이스의 구현체가 구현이 되는 지는 상관이 없다.
  // 인터페이스의 구현체가 어떤식으로 구현이 되던지 간에 사용자쪽(컨트롤러) 상관이 없다.
  private final ProductSVC productSVC;
  // 이하의 구문은 requredArgsConstructor가 없을 경우 직접 써줘야 한다.
//  public ProductController(ProductSVC productSVC) {
//    this.productSVC = productSVC;
//  }

  //등록양식
  @GetMapping("/add")
  public String safeForm() {
    //템플릿 -product-> view이름: saveform
    Product product = new Product();
    Long save = productSVC.save(product);
    return "product/saveForm";
  }
  //등록처리
  @PostMapping("/add")
  public String save() {
    // 다시 클라이언트가 이하의 주소로 다시 요청을 하라고 하는 것
    //상품에 대한 정보를 클라이언트가 전달, 서버는 그걸 db에 저장하고
    // 그리고는 조회화면으로 넘어가야 하는데
    // return 이하의 경로로 가라고 클라이언트가 요청을 다시 하게끔
    // 이 과정에서 사용자는 한번을 클릭했지만 서버와 클라이언트는
    // 두번의 요청과 응답이 있다. 사용자 모르게 이루어진다.
    // 결과적으로 등록처리를 하면 조회 화면으로 넘어간다.
    // return 이하는 뷰가 아니고 경로를 클라이언트가 다시 요청을 하는 것이다.

   //데이터 검증
    // 등록
    return "redirect:/products/{id}/detail";
  }
  //어떤 상품을 조회할 지 알아야 되니까 id가 들어감
  //view가 필요하다.
  //상품조회
  @GetMapping("/{id}/detail")
  public String findById() {
    //이렇게 되면 view가 필요하다.
    // view의 경로는 resources, templates product에 있다.
    // view는 그냥 html로 만드는 것이 아니라 thymeleaf로 만든다.
    return "product/detailForm";
  }

  //상품아이디를 url경로상에 받음
  //조회 화면에서 수정버튼을 클릭했을 경우
  //수정양식
  @GetMapping("/{id}/edit")
  public String updateForm() {
    //view를 리턴
    return "product/updateForm";
  }

  //수정
  // 수정을 하고 나서는 수정 화면을 보여줘야한다.
  // 수정화면은 상품조회
  @PostMapping("{id}/edit")
  public String update() {
    return "redirect:/products/{id}/detail";
  }
  //삭제
  @GetMapping("/{id}/del")
  public String deleteById() {
    //삭제를 하고 나서도 화면을 보여줘야한다.
    return "redirect:/products";
  }

  //전체 목록은 url이 필요없으므로 따로 괄호안에 적지 않는다.
  //목록
  @GetMapping
  public String findAll() {
    return "products/all";
  }
}
