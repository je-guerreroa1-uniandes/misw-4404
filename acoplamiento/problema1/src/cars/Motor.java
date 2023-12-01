package cars;

public interface Motor {
	void increaseRpm();
	void decreaseRpm();
	void decelerate();
	void accelerate();
	// Add other necessary methods
	int getRpm();
	int getSpeed();
	float getOilLevel();
	float getGasLevel();
}