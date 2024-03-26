package ktcloud.mvp.kmart.kafka.pub;

import ktcloud.mvp.kmart.KMartApplication;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducer {

    public static final String KAFKA_IP_PORT  = "INTERNAL://kafka:9092";
    private static KafkaTemplate<String, String> kafkaTemplate = kafkaTemplate();

    public static void pubPaymentEvent(String product){
        KafkaProducer.send("product-payment-done", product);
        KMartApplication.log("kafka로 상품 결제 완료 Event를 발행 했습니다");
    }

    public static void pubOrderEvent(String product){
        KafkaProducer.send("product-order-done", product);
        KMartApplication.log("kafka로 상품 주문 완료 Event를 발행 했습니다");
    }

    // 주어진 주제(topic)로 orderProduct 객체를 Kafka 메시지로 전송
    private static String send(String topic, String orderProduct) {

        kafkaTemplate.send(topic, orderProduct);

        // KafkaTemplate을 사용하여 Kafka 메시지를 보냄
        System.out.println("Kafka Produce: ----------> " + orderProduct);

        return orderProduct;
    }

    // KafkaTemplate을 생성하기 위한 Bean 메서드
    public static KafkaTemplate<String, String> kafkaTemplate() {
        // ProducerFactory를 사용하여 KafkaTemplate 생성
        return new KafkaTemplate<>(producerFactory());
    }

    public static ProducerFactory<String, String> producerFactory() {
        // Kafka producer 설정을 위한 Properties 객체 생성
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_IP_PORT); // Kafka broker의 주소와 포트 설정
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // key serializer 설정
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // value serializer 설정

        // Properties 객체를 사용하여 ProducerFactory 생성
        return new DefaultKafkaProducerFactory<>(properties);
    }

    public static void main(String arg[]){
//        KafkaProducer.send("test message ....");
    }

}
