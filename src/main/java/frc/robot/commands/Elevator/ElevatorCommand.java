class ElevatorCommand extends Command{
    public Elevator elevator;
    public DoubleSupplier speed;
    public double TargetHeight;

    public ElevatorCommand(Elevator elevator, DoubleSupplier speed){
        this.elevator = elevator;
        this.speed = speed;
        addRequirements((Subsystem) elevator);
    }

    public void initialize() {
        targetHeight = elevator.getHeight();
    }

    public void execute() {
        double currentSpeed = speed.getAsDouble();
        if (currentSpeed != 0) {
            targetHeight += currentSpeed * 0.02;
            targetHeight = clamp(targetHeight, elevator.getMinHeight(), elevator.getMaxHeight());
            elevator.setTargetHeight(targetHeight);
        }
    }
}