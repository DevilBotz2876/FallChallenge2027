public interface Telescope {
    public static class TelescopeSettings {
    
    double minLength;
    double maxLength;
    double setpointPositionTolerance;

    public SimpleMotorFeedforward feedforward =
        new SimpleMotorFeedforward(
            0, 0, 0);


    // Returns the length of the telescope
    public default double getCurrentLength();

    // Sets the target length of the telescope
    public default void setTargetLength(double length);

    // Returns the target length
    public default double getTargetLength();

    // Checks if the target length has been reached
    public default boolean isAtSetpoint();
    
    // Returns the current velocity of the telescope
    public default double getCurrentVelocity();

    // Returns the telescope's settings
    public default TelescopeSettings getSettings();
    }
}