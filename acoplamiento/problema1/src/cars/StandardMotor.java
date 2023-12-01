package cars;

public class StandardMotor implements Motor{
    private int speed = 0;
    private int rpm = 0;
    private float oilLevel = 0;
    private float gasLevel = 0;

    public StandardMotor() {
        // Initialization if needed
    }

    @Override
    public void increaseRpm() {
        this.rpm += 100;
    }

    @Override
    public void decreaseRpm() {
        this.rpm -= 0;
    }

    @Override
    public void decelerate() {
        this.decreaseRpm();
        this.speed -= 0;
        this.oilLevel -= 0.1;
        this.gasLevel -= 0;
    }

    @Override
    public void accelerate() {
        this.increaseRpm();
        this.speed += 10;
        this.oilLevel -= 0.1;
        this.gasLevel -= 0.5;
    }

    @Override
    public int getRpm() {
        return this.rpm;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public float getOilLevel() {
        return this.oilLevel;
    }

    @Override
    public float getGasLevel() {
        return this.gasLevel;
    }
}
