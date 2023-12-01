package cars;

public class Car {
	private Motor motor;
	private Dashboard dashboard;
	
	public Car(Motor motor, Dashboard dashboard) {
		this.motor = motor;
		this.dashboard = dashboard;
	}
	
	public void accelerate() {
		this.motor.accelerate();
	}
	
	public void stop() {
		this.motor.decelerate();
	}
	
	public static void main(String[] args) {
		Motor motor = new StandardMotor();
		Dashboard dashboard = new Dashboard(motor);
		Car car = new Car(motor, dashboard);
		
		dashboard.printDashboard();
		car.accelerate();
		dashboard.printDashboard();
		car.stop();
		dashboard.printDashboard();
	}
}
