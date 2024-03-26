package ktcloud.mvp.kmart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class KMartApplication {

	private static String SERVER_IP = null;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	public static void main(String[] args) {

		SpringApplication.run(KMartApplication.class, args);

	}

	@EventListener
	public void init(ApplicationReadyEvent event) {

		log("============= API 서버 초기화 =================");

		String apiServerName = System.getenv().get("api_server_name");

		log("API Server Name : "+apiServerName);

		if ((apiServerName != null) && apiServerName.equals("kmart-order")) {

			MessageListenerContainer container = kafkaListenerEndpointRegistry.getListenerContainer("PaymentDoneConsumer");
			if (container != null) {
				container.start();
				log(">>>>> Kafka PaymentDoneConsumer started <<<<<");
			}
		} else if((apiServerName != null) && apiServerName.equals("kmart-shipment")) {

			MessageListenerContainer container = kafkaListenerEndpointRegistry.getListenerContainer("OrderDoneConsumer");
			if (container != null) {
				container.start();
				log(">>>>> Kafka OrderDoneConsumer started <<<<<");
			}
		}

	}

	public static void log(String message) {

		if(SERVER_IP == null){
			try {
				SERVER_IP = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("[" + SERVER_IP + "] " + message);
	}

}
