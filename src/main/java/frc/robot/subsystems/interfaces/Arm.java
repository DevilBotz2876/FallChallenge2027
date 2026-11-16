public interface Arm {
    public static class ArmSettings{
    double armLength;
    double minAngle
    double maxAngle
    double angleSetpointTolerance;
  
    public SimpleMotorFeedforward feedforward =
        new SimpleMotorFeedforward(
            0, 0, 0);
            
    // Returns the angle the arm is already at
    public default double getCurrentAngle();

    // Tells what angle the arm should go to
    public default void setTargetAngle(double angle);

    // Returns the angle the arm should go to
    public default double getTargetAngle();

    // Checks whether the arm has reached the target angle
    public default boolean isAtSetpoint();

    // Returns the rate, in radians, the arm is rotating at
    public default double getCurrentVelocity();

    // Returns the arm's settings
    public default ArmSettings getSettings();
    }
}