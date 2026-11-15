package frc.robot.subsystems.interfaces;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public interface Telescope{
    public static class TelescopeSettings{
        double minHeight = 0;
        double maxHeight = 1; //dont know the max height
        double setpointPositionTolerance = 0.1 //dont know the position tolerance

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
   * Returns the current winch position
   *
   * @return the current winch position in radians
   */  

public double getCurrentHeight();
/**
   * Returns the desired winch position
   *
   * @return the desired target position in radians
   */

  /**
   * Sets the desired target winch position
   *
   * @param height the desired target position in radians
   */
public void setTargetHeight(double height);
/**
   * Returns the desired winch position
   *
   * @return the desired target position in radians
   */

public double getTargetHeight();
/**
   * Returns the desired winch position
   *
   * @return the desired target position in radians
   */

public boolean isAtSetpoint();
/**
   * Returns the desired winch position
   *
   * @return the desired target position in radians
   */

public double getCurrentVelocity();
/**
   * Returns the desired winch position
   *
   * @return the desired target position in radians
   */

public SimpleMotorSettings getSettings();
}