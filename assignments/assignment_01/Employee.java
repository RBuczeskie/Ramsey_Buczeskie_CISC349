
public abstract class Employee{
	
	public int ssn;
	public String full_name;
	public String job_title;
	public String pay_type;
	public int min_hours;
	public int max_hours;
	public int hours;
	
	public Employee(int social, String name, String job){
		//System.out.println(social);
		this.ssn = social;
		this.set_name(name);
		this.set_job(job);
	}
	
	public int get_ssn(){
		return ssn;
	}
	
	public void set_name(String name){
		full_name = name;
	}
	
	public String get_name(){
		return full_name;
	}
	
	public void set_job(String title){
		job_title = title;
	}
	
	public String get_job(){
		return job_title;
	}
	
	public String get_pay_type(){
		return pay_type;
	}
	
	public int get_min_hours(){
		return min_hours;
	}
	
	public int get_max_hours(){
		return max_hours;
	}
	
	public int get_hours(){
		return hours;
	}
	
	public void set_hours(int h){
		if (min_hours <= h && h <= max_hours){hours = h;}
	}
	
}
