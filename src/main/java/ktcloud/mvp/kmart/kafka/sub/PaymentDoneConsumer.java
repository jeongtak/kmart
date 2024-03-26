package ktcloud.mvp.kmart.kafka.sub;

import ktcloud.mvp.kmart.KMartApplication;
import ktcloud.mvp.kmart.kafka.pub.KafkaProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentDoneConsumer {

    // KafkaListener annotation을 통해 product-payment-done 토픽 의 메시지를 수신함
    @KafkaListener(id="PaymentDoneConsumer", topics = "product-payment-done",  groupId = "consumer-group-order", autoStartup = "false", containerFactory = "kafkaListenerContainerFactory")
    public void subPaymentDoneEvent(String product) {
        KMartApplication.log("Kafka PaymentDoneConsumer: <---------- "+ product);
        KMartApplication.log("<"+product+"> 상품 결제 완료 Event를 구독하였습니다.");

        KafkaProducer.pubOrderEvent(product);
    }
}
