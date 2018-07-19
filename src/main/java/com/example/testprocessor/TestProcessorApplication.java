package com.example.testprocessor;



import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;

@SpringBootApplication
@EnableBinding(Processor.class)
public class TestProcessorApplication {

	Random random = new Random();
	public static void main(String[] args) {
		SpringApplication.run(TestProcessorApplication.class, args);
	}

	@Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
	public String transform(Message<?> message) {
		try {
			Thread.sleep(new Long(random.nextInt(3000)));
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (message.getHeaders().containsKey("correlationId")) {
			return (String) message.getHeaders().get("correlationId");
		}
		return "unknown";
	}
}
