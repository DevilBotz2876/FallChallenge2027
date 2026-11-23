package frc.robot.subsystems.interfaces;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public interface Elevator {
    public static class ElevatorSettings {
    public double minHeightInInches = 0;
    public double maxHeightInInches = 20;
    public double setpointPositionTolerance;
    }
     public SimpleMotorFeedforward feedforward =
        new SimpleMotorFeedforward(
            0, 0, 0);
    
    // Returns the elevator's current height
    public default double getCurrentHeight() {
        return 0.0;
    }

    // Sets the height the elevator should go to
    public default void setTargetHeight(double height) {

    }

    // Returns the height the elevator should go to
    public default double getTargetHeight() {
        return 0.0;
    }

    // Checks if the target height has been reached
    public default boolean isAtSetpoint() {
        return false;
    }

    // Returns the current velocity in radians of the elevator
    public default double getCurrentVelocity() {
        return 0.0;
    }

    // Returns the elevator's settings
    public default ElevatorSettings getSettings() {
        return new ElevatorSettings();
    }
    
}