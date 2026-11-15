package frc.robot.subsystems.interfaces;

public interface Elevator {
public static class ElevatorSettings {
    public double minHeight;
    public double maxHeight;
    public double setpointPositionTolerance;
    public double SimpleMotorFeedforward;

     // Sim stuff goes here 
  } 

  /**
   * Returns the current height of the elevator
   *
   * @return the current height of the elevator
   */
    public double getCurrentHeight();

    /**
     * Sets the target height for the elevator
     *
     * @param height the target height
     */
    public void setTargetHeight(double height);

    /**
     * Returns the target height for the elevator
     *
     * @return the target height
     */
    public double getTargetHeight();

    /**
     * Checks if the elevator is at its target height
     *
     * @return true if the elevator is at its target height, false otherwise
     */

    public boolean isAtSetpoint();

    /**
     * Returns the current velocity of the elevator
     *
     * @return the current velocity of the elevator
     */
    public double getCurrentVelocity();

    /**
     * Returns the elevator settings
     *
     * @return the current elevator settings
     */
    public ElevatorSettings getSettings();
}
