public class FullTime extends Employee{
	
	public FullTime(int social, String name, String job){
		super(social, name, job);
		this.pay_type = "Salaried, Full Time";
		this.min_hours = 45;
		this.max_hours = 45;
	}
	
}