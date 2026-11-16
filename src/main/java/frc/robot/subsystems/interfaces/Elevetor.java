public interface Elevator {
    public static class ElevatorSettings {
    double minHeight;
    double maxHeight;
    double setpointPositionTolerance;

     public SimpleMotorFeedforward feedforward =
        new SimpleMotorFeedforward(
            0, 0, 0);
    
    // Returns the current height of the elevator
    public default double getCurrentHeight();

    // Sets the target height of the elevator
    public default void setTargetHeight(double height);

    // Returns the target height of the elevator
    public default double getTargetHeight();

    // Checks if the target height has been reached
    public default boolean isAtSetpoint();

    // Returns the current velocity in radians of the elevator
    public default double getCurrentVelocity();

    // Returns the elevator's settings
    public default ElevatorSettings getSettings();
    }
}
