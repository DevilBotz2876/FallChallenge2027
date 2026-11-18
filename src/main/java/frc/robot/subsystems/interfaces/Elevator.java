package frc.robot.subsystems.interfaces;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public interface Elevator {
    public static class ElevatorSettings {
    double minHeight;
    double maxHeight;
    double setpointPositionTolerance;

     public SimpleMotorFeedforward feedforward =
        new SimpleMotorFeedforward(
            0, 0, 0);
    }
// Returns the current height of the elevator
public double getCurrentHeight();

// Sets the target height of the elevator
public void setTargetHeight(double height);

// Returns the target height of the elevator
public double getTargetHeight();

// Checks if the target height has been reached
public boolean isAtSetpoint();

// Returns the current velocity in radians of the elevator
public double getCurrentVelocity();

// Returns the elevator's settings
public ElevatorSettings getSettings();
}
