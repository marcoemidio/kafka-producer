package pt.sonae.bit.kafka.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marco Emidio
 */
@Configuration
public class KafkaProducerConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerConfig.class);
	
	@Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
		
	@Bean
    public Map<String, Object> kafkaProducerConfigs() {
		
		LOG.debug("Loading kafka producer configs...");
		
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "processor");
		props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        
        return props;
    }
	
    // Decorate ProducerFactory with TracingProducerFactory
	@Bean
    public ProducerFactory<String, String> kafkaProducerFactory() {
    	
    	LOG.debug("Loading kafka producer factory...");
    	
    	return new DefaultKafkaProducerFactory<>(kafkaProducerConfigs());
    }

	// Use decorated ProducerFactory in KafkaTemplate
	@Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
    	
    	LOG.debug("Loading kafka template...");
    	
    	return new KafkaTemplate<>(kafkaProducerFactory());

    }
}
