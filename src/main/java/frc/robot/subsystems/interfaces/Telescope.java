public interface Telescope {
    public static class TelescopeSettings {
    
    double minLength;
    double maxLength;
    double setpointPositionTolerance;
    public SimpleMotorFeedforward feedforward =
        new SimpleMotorFeedforward(
            0, 0, 0);

    // Returns the telescope's current length
    public default double getCurrentLength();

    // Sets the the length the telescope should go to
    public default void setTargetLength(double length);

    // Returns the length the target should go to
    public default double getTargetLength();

    // Checks if the target length has been reached
    public default boolean isAtSetpoint();
    
    // Returns the current velocity of the telescope
    public default double getCurrentVelocity();

    // Returns the telescope's settings
    public default TelescopeSettings getSettings();
    }
}