public interface Elevator {
    public static class ElevatorSettings {
    double minHeight;
    double maxHeight;
    double setpointPositionTolerance;

     public SimpleMotorFeedforward feedforward =
        new SimpleMotorFeedforward(
            0, 0, 0);
    
    // Returns the elevator's current height
    public default double getCurrentHeight();

    // Sets the height the elevator should go to
    public default void setTargetHeight(double height);

    // Returns the height the elevator should go to
    public default double getTargetHeight();

    // Checks if the target height has been reached
    public default boolean isAtSetpoint();

    // Returns the current velocity in radians of the elevator
    public default double getCurrentVelocity();

    // Returns the elevator's settings
    public default ElevatorSettings getSettings();
    }
}