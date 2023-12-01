package cars;

public class Dashboard {

	Motor motor;
	
	public Dashboard(Motor motor) {
		this.motor = motor;
	}
	
	public void printDashboard() {
		System.out.println("--------------------------------");
		System.out.println("DASHBOARD:");
		System.out.println("\t RPM: " + this.motor.getRpm());
		System.out.println("\t Speed: " + this.motor.getSpeed());
		System.out.println("\t Oil level: " + this.motor.getOilLevel());
		System.out.println("\t Gas level: " + this.motor.getGasLevel());
	}
}
