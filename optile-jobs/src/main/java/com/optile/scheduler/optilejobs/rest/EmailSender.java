package com.optile.scheduler.optilejobs.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;


/**
 * 
 * @author Abdelrahman Fathy
 * @since 19 Jan 2020
 * Email sender job, as a sample job for testing.
 *
 */

@RestController
@RequestMapping(path = "/")
@ControllerAdvice
public class EmailSender {

	@Autowired
	private JavaMailSender javaMailSender;

	private static final Logger logger = LogManager.getLogger(EmailSender.class);

	@RequestMapping(value = "/sendEmail", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<SimpleMailMessage> sendEmail(@RequestBody SimpleMailMessage msg) {
		sendEmailAction(msg);
		return new ResponseEntity<SimpleMailMessage>(msg,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/validateEmailParam", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<JsonNode> validateEmail(@RequestBody SimpleMailMessage msg) {
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		JsonNode node = mapper.createObjectNode();
		try {
			node = mapper.readTree(validateEmailAction(msg).toString());
		} catch (Exception e) {
			logger.error(e);
		} 
		return new ResponseEntity<JsonNode>(node,HttpStatus.OK);
		
	}

	private void sendEmailAction(SimpleMailMessage msg) {
		logger.info("Email sent => " + msg);
		//uncomment to test
		logger.info("email sent:" , msg);
		//javaMailSender.send(msg);
	}
	
	private JsonObject validateEmailAction(SimpleMailMessage msg) {
		JsonObject jo = new JsonObject();
		jo.addProperty("valid", Boolean.FALSE);
		if(msg.getText() != null && msg.getText().length() > 0 && 
				msg.getTo() != null && msg.getTo().length > 0 && 
				msg.getSubject() != null && msg.getSubject().length() > 0 )
			jo.addProperty("valid", Boolean.TRUE);
		return jo;
	}

}
