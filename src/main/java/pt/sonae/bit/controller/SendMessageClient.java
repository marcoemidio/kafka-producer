package pt.sonae.bit.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pt.sonae.bit.beans.WelcomeMessage;
import pt.sonae.bit.kafka.producer.KafkaProducer;

/**
 * @author Marco Emidio
 */
@RestController
public class SendMessageClient {
	
	private static final Logger log = LoggerFactory.getLogger(SendMessageClient.class);
	
	@Autowired
    private KafkaProducer producer;
	
	private static final String welcomemsg = "Welcome Mr. %s!";
	
	@RequestMapping(value="/welcome/user/{name}", method = RequestMethod.GET)
	public WelcomeMessage welcomeMessage(@PathVariable String name) throws IOException {
		
		String message = String.format(welcomemsg, name);
		
		log.info("GET request with name='{}'", name);
		
		producer.sendRequest(message);
		
		return new WelcomeMessage(String.format(welcomemsg, name));
		
	}	
	
}
