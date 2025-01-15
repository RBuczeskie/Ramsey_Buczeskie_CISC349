public class PartTime extends Employee{
	
	public PartTime(int social, String name, String job){
		super(social, name, job);
		this.pay_type = "Salaried, Part Time";
		this.min_hours = 20;
		this.max_hours = 40;
	}
	
}