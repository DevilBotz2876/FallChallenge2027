package frc.robot.subsystems.interfaces;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public interface Arm {
    public static class ArmSettings{
    double armLength;
    double minAngle;
    double maxAngle;
    double angleSetpointTolerance;
  
    public SimpleMotorFeedforward feedforward =
        new SimpleMotorFeedforward(
            0, 0, 0);
   
    }
// Returns the current angle the arm is positioned at
public double getCurrentAngle();

// Tells the arm the angle it should rotate towards
public void setTargetAngle(double angle);

// Returns the angle the arm should rotate towards
public double getTargetAngle();

// Checks whether the arm has reached the target angle
public boolean isAtSetpoint();

// Returns the rate, in radians, the arm is rotating at
public double getCurrentVelocity();

// Returns the arm's settings
public ArmSettings getSettings();
}
