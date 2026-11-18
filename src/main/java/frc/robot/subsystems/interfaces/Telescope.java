package frc.robot.subsystems.interfaces;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public interface Telescope {
    public static class TelescopeSettings {
    
    double minLength;
    double maxLength;
    double setpointPositionTolerance;

    public SimpleMotorFeedforward feedforward =
        new SimpleMotorFeedforward(
            0, 0, 0);


    
    }
// Returns the length of the telescope
public double getCurrentLength();

// Sets the target length of the telescope
public void setTargetLength(double length);

// Returns the target length
public double getTargetLength();

// Checks if the target length has been reached
public boolean isAtSetpoint();

// Returns the current velocity of the telescope
public double getCurrentVelocity();

// Returns the telescope's settings
public TelescopeSettings getSettings();    
}