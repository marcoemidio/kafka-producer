package pt.sonae.bit.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Marco Emidio
 */
@Service
public class KafkaProducer {
	
	private static final Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	//@Autowired
    //private Tracer tracer;
	
	@Value("${kafka.topic}")
    private String topic;
	
	public void sendRequest(String message){
        
        //Span span = tracer.buildSpan("sendRequest").start();
       
        LOG.info("sending message='{}' to topic='{}'", message, topic);
        
        kafkaTemplate.send(topic, message);

        //span.finish();
        
    }
	
}
