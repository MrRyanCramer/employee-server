package ryan.cramer.employee_server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
        
    @Bean
    public CommandLineRunner commandLineRunner(EmployeeRepository repository) {
        return args -> {
        	// First command line argument is intended to contain a path to a json file of employees.
        	if (args.length > 0) {
        		try {
        			byte[] jsonData = Files.readAllBytes(Paths.get(args[0]));
        			ObjectMapper objectMapper = new ObjectMapper();
              objectMapper.registerModule(new JavaTimeModule());

        			List<Employee> list = Arrays.asList(objectMapper.readValue(jsonData, Employee[].class));
        			for (Employee emp: list) {
        				repository.save(emp);
        			}
        		} catch (IOException e) {
        			System.out.println("Unable to parse provided json file:\n" + e);
        		}
        	} else {
        		System.out.println("Defaulting to empty employee set.  "
        				+ "Provide a filepath containing employees as the first command line argument to import.");
        	}
		};
	}
}
