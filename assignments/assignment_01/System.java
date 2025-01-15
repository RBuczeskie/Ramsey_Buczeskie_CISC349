
import java.util.ArrayList; // import the ArrayList class

public class System{
	
	public ArrayList<Employee> employees = new ArrayList<Employee>(); // Create an ArrayList object
	
	public void add_employee(Employee employee){
		employees.add(employee);
	}
	
	public Employee view_record(int i){
		return employees.get(i);
	}
	
	public void update_record(int i, Employee employee){
		/*
		To conveniently update employee records, call view_record,
		modify the retrieved copy of the record with relevant Class methods,
		then call update record with the same index and updated record.
		*/
		employees.set(i, employee);
	}
	
	public void delete_record(int i){
		employees.remove(i);
	}
}