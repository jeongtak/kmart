package ktcloud.mvp.kmart.kafka.sub;

import ktcloud.mvp.kmart.KMartApplication;
import ktcloud.mvp.kmart.db.ProductQuery;
import ktcloud.mvp.kmart.push.WebSocketHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderDoneConsumer {

    // KafkaListener annotation을 통해 product-order-done 토픽 의 메시지를 수신함
    @KafkaListener(id="OrderDoneConsumer", topics = "product-order-done",  groupId = "consumer-group-shipment", autoStartup = "false",containerFactory = "kafkaListenerContainerFactory")
    public void subOrderDoneEvent(String product) {
        KMartApplication.log("Kafka OrderDoneConsumer: <---------- "+ product);
        KMartApplication.log("<"+product+"> 상품 주문 완료 Event를 구독하였습니다.");

        ProductQuery.deliverProduct(product);
        KMartApplication.log("<"+product+"> 상품 DB 상태를 배송중으로 수정하였습니다.");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        KMartApplication.log("<"+product+"> 상품 배송을 시작했습니다.");
        WebSocketHandler.pushProductDelivery("("+product+") 상품 배송을 시작했습니다.");
    }
}
