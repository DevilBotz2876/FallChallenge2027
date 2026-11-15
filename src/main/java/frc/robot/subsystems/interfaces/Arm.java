package frc.robot.subsystems.interfaces;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public interface Arm{
  public static class ArmSettings {
    public double minAngleInDegrees = 0;
    public double maxAngleInDegrees = 180; // dont know max position of arm, so just fill in
    public double startingAngleInDegrees = 0;
    public double angelSetpointTolerance = 1; //was Math.PI

    public double maxVelocityInDegreesPerSecond = 360; //UNSURE OF ALL NUMBERS
    public double maxAccelerationInDegreesPerSecondSquared = 1440; //was 8*Math.PI = 1440 degrees

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
   * @return the current angle position in radians
   */

public double getCurrentAngle();
/**
   * Returns the desired winch position
   *
   * @return the desired target position in radians
   */
    

  /**
   * Sets the desired target winch position
   *
   * @param degrees the desired target position in radians
   */

public void setTargetAngle(double degrees);
/**
   * Returns the desired winch position
   *
   * @return the desired target position in radians
   */

public double getTargetAngle();
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