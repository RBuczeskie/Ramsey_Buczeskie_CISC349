public class Contractor extends Employee{
	
	public Contractor(int social, String name, String job){
		super(social, name, job);
		this.pay_type = "Hourly";
		this.min_hours = 0;
		this.max_hours = 168;
	}
	
}