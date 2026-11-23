package frc.robot.subsystems.interfaces;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public interface Telescope {
    public static class TelescopeSettings {
    
    public double minLengthInInches = 0;
    public double maxLengthInInches = 10;
    public double setpointPositionTolerance;
    }
    public SimpleMotorFeedforward feedforward =
        new SimpleMotorFeedforward(
            0, 0, 0);

    // Returns the telescope's current length
    public default double getCurrentLength() {
        return 0.0;
    }

    // Sets the the length the telescope should go to
    public default void setTargetLength(double length) {

    }

    // Returns the length the target should go to
    public default double getTargetLength() {
        return 0.0;
    }

    // Checks if the target length has been reached
    public default boolean isAtSetpoint() {
        return false;
    }
    
    // Returns the current velocity of the telescope
    public default double getCurrentVelocity() {
        return 0.0;
    }

    // Returns the telescope's settings
    public default TelescopeSettings getSettings() {
        return new TelescopeSettings();
    }
}    