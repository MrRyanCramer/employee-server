package ryan.cramer.employee_server;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	Optional<Employee> findByIdAndActive(Long id, boolean active);
	List<Employee> findByActive(boolean active);
}
