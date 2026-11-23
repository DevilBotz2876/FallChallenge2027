package frc.robot.subsystems.interfaces;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public interface Arm {
    public static class ArmSettings{

    public double armLength;
    public double minAngleInDegrees = -90;
    public double maxAngleInDegrees = 90;
    public double angleSetpointTolerance;
    }
  
    public SimpleMotorFeedforward feedforward =
        new SimpleMotorFeedforward(
            0, 0, 0);
            
    // Returns the angle the arm is already at
    public default double getCurrentAngle() {
        return 0.0;
    }

    // Tells what angle the arm should go to
    public default void setTargetAngle(double angle) {
        
    }

    // Returns the angle the arm should go to
    public default double getTargetAngle() {
        return 0.0;
    }

    // Checks whether the arm has reached the target angle
    public default boolean isAtSetpoint() {
        return false;
    }

    // Returns the rate, in radians, the arm is rotating at
    public default double getCurrentVelocity() {
        return 0.0;
    }

    // Returns the arm's settings
    public default ArmSettings getSettings() {
        return new ArmSettings();
    }
    
}