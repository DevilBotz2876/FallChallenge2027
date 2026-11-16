package frc.robot.subsystems.interfaces;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public interface Elevator {
  public static class ElevatorSettings {

    public double minHeightInMeters = 0.0;
    public double maxHeightInMeters = 1.0;
    
    public double setpointPositionToleranceInMeters = 0.1; //unsure

    // feedforward is in *radian* units
    public SimpleMotorFeedforward feedforward =
        new SimpleMotorFeedforward(
            0, 0, 0); // These feedforward values assume the native Motor units of *radians*.

    // 2D Graphic Params
    public Color8Bit color = new Color8Bit(Color.kWhite);

    // Flywheel Simulation Settings
    public DCMotor motor = DCMotor.getNEO(1);
    public double moiKgMetersSquared = 0.025; // The moment of inertia of the winch
  }




/**
   
   * @return 
   */
  public double getCurrentHeight();

  /**
   *
   * @param height 
   */
  public void setTargetHeight(double height);

  /**
   
   * @return 
   */
  public double getTargetHeight();

   /**
   * @return 
   */
  public boolean isAtSetpoint();

  /**

   *
   * @return 
   */
  public double getCurrentVelocity();

   /**
   * Returns the elevator settings
   *
   * @return the current elevator settings
   */
  public ElevatorSettings getSettings();

}
