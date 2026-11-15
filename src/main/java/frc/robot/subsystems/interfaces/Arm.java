package frc.robot.subsystems.interfaces;

public interface Arm {
  public static class ArmSettings {
    public double SimpleMotorFeedforward;
    public double armLength;
    public double minAngle;
    public double maxAngle;
    public double angleSetpointTolerance;

    // Sim stuff goes here 
  } 
  /**
   * Returns the current arm angle
   *
   * @return the current arm angle in degrees
   */  
  public double getCurrentAngle();

  /**
   * Sets the target angle for the arm
   *
   * @param angle the target angle in degrees
   */
    public void setTargetAngle(double angle);

    /**
     * Returns the target angle for the arm
     *
     * @return the target angle in degrees
     */
    public double getTargetAngle();

    /**
     * Checks if the arm is at its target angle
     *
     * @return true if the arm is at its target angle, false otherwise
     */
    public boolean isAtSetpoint();

    /**
     * Returns the current velocity of the arm
     *
     * @return the current velocity of the arm in degrees per second
     */
    public double getCurrentVelocity();
    /**
     * Returns the arm settings
     *
     * @return the current arm settings
     */

    public ArmSettings getSettings();
}

