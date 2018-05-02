package ryan.cramer.employee_server;

import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EmployeeRestController {

	private final EmployeeRepository employeeRepository;
    
	@Autowired
	EmployeeRestController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	/**
	 * Gets an active employee by the specified ID.
	 * @param id	The specified ID.
	 * @return  	Returns the employee if found, otherwise returns an error message.
	 */
    @GetMapping("/employees/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
    	Optional<Employee> emp = employeeRepository.findByIdAndActive(id, true);
    	if (emp.isPresent()) {
    		return new ResponseEntity<Employee>(emp.get(), HttpStatus.OK);
    	} else {
    		return new ResponseEntity<String>("Employee with id " + id + " not found.", HttpStatus.NOT_FOUND);
    	}
    }


    /**
     * Creates a new employee if no employee is found with the same ID.
     * @param newEmp	The employee to create.
     * @return			Returns the created employee if successful, otherwise returns an error message.
     */
    @PostMapping("/employees")
    public ResponseEntity<?> createEmployee(@RequestBody Employee newEmp) {
    	if (employeeRepository.findById(newEmp.getId()).isPresent()) {
    		return new ResponseEntity<String>("A user with ID " + newEmp.getId() + " already exists.", HttpStatus.CONFLICT);
    	} 
		employeeRepository.save(newEmp);
		return new ResponseEntity<Employee>(newEmp, HttpStatus.CREATED);
    	
    
    }
    
    /**
     * Updates an existing employee based on the provided information.
     * @param id			The ID of the employee to update.
     * @param employee		The updated employee information.
     * @return				The updated employee if the employee to update is found, otherwise returns an error message.
     */
    @PutMapping("/employees/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee newEmp) {
    	Optional<Employee> emp = employeeRepository.findById(id);
    	if (emp.isPresent()) {
    		employeeRepository.save(newEmp);
    		return new ResponseEntity<Employee>(newEmp, HttpStatus.OK);
    	}
    	return new ResponseEntity<String>("Unable to update.  Employee with id " + id + " not found.", HttpStatus.NOT_FOUND);
    }
    
    
    /**
     * Acts as a delete function, by setting an employee's status to inactive if found.
     * @param id
     * @return		No content if successful, otherwise returns an error message.			
     */
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
    	Optional<Employee> emp = employeeRepository.findById(id);
    	if (emp.isPresent()) {
    		Employee newEmp = emp.get();
    		newEmp.setActive(false);
    		employeeRepository.save(newEmp);
    		return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<String>("Unable to delete.  Employee with id " + id + " not found.", HttpStatus.NOT_FOUND);
    }
    
    /**
     * Get all active employees.
     * @return  All active employees.
     */
    @GetMapping("/employees")
    public ResponseEntity<Collection<Employee>> employees() {
    	return new ResponseEntity<Collection<Employee>>(employeeRepository.findByActive(true), HttpStatus.OK);
    }

    
}
