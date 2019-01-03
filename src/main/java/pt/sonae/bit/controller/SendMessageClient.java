package pt.sonae.bit.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pt.sonae.bit.beans.WelcomeMessage;

@RestController
public class SendMessageClient {
	
	private static final Logger log = LoggerFactory.getLogger(SendMessageClient.class);
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value("${kafka.topic}")
    private String topic;
	
	private static final String welcomemsg = "Welcome Mr. %s!";
	
	@RequestMapping(value="/welcome/user/{name}", method = RequestMethod.POST)
	public String welcomeMessage(@PathVariable String name) throws IOException {
		
		String message = String.format(welcomemsg, name);
		
		log.info("sending message='{}' to topic='{}'", message, topic);
		
		kafkaTemplate.send(topic, message);
		
		return "message sent";
		
	}	
	
}
