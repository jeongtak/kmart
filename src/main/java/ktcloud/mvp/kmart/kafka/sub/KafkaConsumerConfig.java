package ktcloud.mvp.kmart.kafka.sub;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

// Kafka consumer 설정을 위한 Configuration 클래스
@Configuration
@EnableKafka // Kafka 사용하기 위해 Spring Kafka를 활성화함
public class KafkaConsumerConfig {

    public KafkaConsumerConfig(){
    }

    private static final String KAFKA_SERVER = "kafka:9092";

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        // Kafka consumer 설정을 위한 Properties 객체 생성
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER); // Kafka broker의 주소와 포트 설정
//        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1"); // consumer group ID 설정
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // key deserializer 설정
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // value deserializer 설정

        // Properties 객체를 사용하여 ConsumerFactory 생성
        return new DefaultKafkaConsumerFactory<>(properties);
    }

    // Kafka listener container를 생성하기 위한 Bean 메서드
    @Bean("kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        // ConcurrentKafkaListenerContainerFactory 생성
        ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();

        // ConsumerFactory를 사용하여 Kafka listener container를 생성함
        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());

        return kafkaListenerContainerFactory;
    }


}