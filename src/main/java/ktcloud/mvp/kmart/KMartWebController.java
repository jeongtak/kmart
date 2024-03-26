package ktcloud.mvp.kmart;

import ktcloud.mvp.kmart.kafka.pub.KafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KMartWebController {

    // 메인페이지
    @GetMapping("/")
    public ResponseEntity<String> index() {

        KMartApplication.log("K-Mart 웹 서비스에 접속하였습니다");

        return ResponseEntity.ok(
                "Welcome to K-Mart <hr><br>\n" +
                        "<a href=\"/getProductList\">상품 목록 조회</a><br>");
    }

    // 상품목록조회
    @GetMapping("/getProductList")
    public ResponseEntity<String> getProductList() {

        return ResponseEntity.ok(
                "Welcome to K-Mart <hr><br>\n" +
                        "\n" +
                        "상품 목록 <br>\n" +
                        "<table border=1 cellpadding=10>\n" +
                        "<tr>\n" +
                        "<td><a href=\"/reqProductPayment/cherry\">체리</a></td>\n" +
                        "<td><a href=\"/reqProductPayment/banana\">바나나</a></td></tr>\n" +
                        "<tr>\n" +
                        "<td><a href=\"/reqProductPayment/pineapple\">파인애플</a></td>\n" +
                        "<td><a href=\"/reqProductPayment/tomato\">토마토</a></td></tr>\n" +
                        "</table>\n" +
                        "<br>\n" +
                        "※ 상품을 클릭하면 바로 주문이 요청됩니다. <hr>\n");

    }


    // 상품결제요청
    @GetMapping("/reqProductPayment/{product}")
    public ResponseEntity<String> reqProductPayment(@PathVariable String product) {
        KMartApplication.log("(" + product + ") 상품 결제가 완료 되었습니다");
        KafkaProducer.pubPaymentEvent(product);
        return ResponseEntity.ok(createWebSocket() + "(" + product + ") 상품 결제가 완료 되었습니다 <hr>" +
                "<p id=\"message\"></p>\n" +
                "<a href=\"/getProductList\">상품 목록으로 돌아가기</a><br>");

    }

    private String createWebSocket() {
        return "<script>\n" +
                "let socket = new WebSocket(\"ws://localhost:8084/webpush\");\n" +
                "\n" +
                "socket.onopen = function(e) {\n" +
                "  //document.getElementById(\"message\").innerHTML = \"webpush connected\";\n" +
                "};\n" +
                "\n" +
                "socket.onmessage = function(event) {\n" +
                "\n" +
                "    document.getElementById(\"message\").innerHTML =  event.data;\n" +
                "};\n" +
                "\n" +
                "socket.onerror = function(error) {\n" +
                "  alert(\"[error]\");\n" +
                "};\n" +
                "</script>";
    }
//    // 상품 주문 요청
//    public void reqProductOrder() {
//
//        try {
//            KMartApplication.log("상품 주문을 요청하셨습니다.");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new RuntimeException(e);
//        }
//
//    }

//    // 주문 완료 발행
//    public void pubProductOrderEvent() {
//
//        try {
//            KMartApplication.log("상품 주문 완료 Event를 발행하였습니다.");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new RuntimeException(e);
//        }
//
//    }

//    // 주문 완료 구독
//    public void subProductOrderEvent() {
//
//        try {
//            KMartApplication.log("상품 주문 완료 Event를 구독하였습니다.");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    // 상품 배송 처리
//    public String doProductDelivery() {
//
//        try {
//            KMartApplication.log("상품 배송을 요청 하였습니다.");
//            return "상품 배송 중";
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return "상품 배송 실패";
//        }
//
//    }

}