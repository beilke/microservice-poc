package com.poc.microservicepoc.customerservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.DefaultKafkaHeaderMapper;
import org.springframework.kafka.support.KafkaHeaderMapper;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.messaging.Message;

import java.util.List;
import java.util.Map;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
public class CustomerServiceApplication {

	@Value("#{'${interestingEvents}'.split(';')}")
	private List<String> interestingEvents;

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

    /**
     * Helps in deserializing custom headers
     */
	@Bean
    public KafkaHeaderMapper defaultKafkaHeaderMapper(){
        DefaultKafkaHeaderMapper defaultKafkaHeaderMapper = new DefaultKafkaHeaderMapper();
		defaultKafkaHeaderMapper.setRawMappedHeaders(Map.of("eventType", true, "eventId", true, KafkaHeaders.RECEIVED_MESSAGE_KEY, true));
        return defaultKafkaHeaderMapper;
    }

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	/**
	 * Route by eventType header.The name of the consumer bean should be the event type.
	 */
	@Bean
	public MessageRoutingCallback customRouter() {
		return new MessageRoutingCallback() {
			@Override
			public FunctionRoutingResult routingResult(Message<?> message) {
				String eventType = (String) message.getHeaders().get("eventType");
				if(!interestingEvents.contains(eventType)){
					return new FunctionRoutingResult("NotInterestedEvent");
				}
				return new FunctionRoutingResult(eventType);
			}
		};
	}

}
