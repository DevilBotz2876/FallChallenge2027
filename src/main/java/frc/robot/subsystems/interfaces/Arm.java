package frc.robot.subsystems.interfaces;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public interface Arm {
    public static class ArmSettings {
        public double minAngleInDegrees = 0;
        public double maxAngleInDegrees = 180.0; //guess
        public double startingAngleInDegrees = 0;
        public double targetAngleToleranceInDegrees = 1.0; //was Math.PI

    public double maxVelocityInDegreesPerSecond = 360; //unsure
    public double maxAccelerationInDegreesPerSecondSquared = 1440; //was 8 * Math.PI

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
  public double getCurrentAngle();

/**

   *
   * @return 
   */
  public double getCurrentVelocity();

  /**
   * Returns the desired arm angle
   *
   * @return the desired target angle in degrees
   */
  public double getTargetAngle();


/**
   *
   * @param degrees 
   */
  public void setTargetAngle(double degrees);


 /**
   * @return 
   */
  public boolean isAtSetpoint();


 /**
   * Returns the arm settings
   *
   * @return the current arm settings
   */
  public ArmSettings getSettings();


}