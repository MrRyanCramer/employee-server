package ryan.cramer.employee_server;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Entity
public class Employee {

	@Id
	private long id;
	private String firstName;
	private String middleInitial;
	private String lastName;
	private LocalDate birthDate;
	private LocalDate employmentDate;
	private boolean active = true;

	@SuppressWarnings("unused")
	private Employee() {
	}

	public Employee(long id, String firstName, String middleInitial, String lastName, LocalDate birthDate,
			LocalDate employmentDate, boolean active) {
		this.id = id;
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.employmentDate = employmentDate;
		this.active = active;
	}

	public Employee(long id, String firstName, String middleInitial, String lastName, LocalDate birthDate,
			LocalDate employmentDate) {
		this(id, firstName, middleInitial, lastName, birthDate, employmentDate, true);
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public LocalDate getEmploymentDate() {
		return employmentDate;
	}

	public long getId() {
		return id;
	}

	public String toJson() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		return mapper.writeValueAsString(this);
	}

	@Override
	public String toString() {
		try {
			return this.toJson();
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}

}
