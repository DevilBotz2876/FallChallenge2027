package frc.robot.commands.common.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.interfaces.Elevator;
import java.util.function.DoubleSupplier;

public class ElevatorToPosition extends Command {
    Elevator elevator;
    DoubleSupplier positionInches;
    double targetPosition;

    /** Sets the height of the elevator in inches */
    public ElevatorToPosition(Elevator elevator, DoubleSupplier positionInches) {
        this.elevator = elevator;
        this.positionInches = positionInches;

        addRequirements((Subsystem) elevator);
    }

    @Override
    public void initialize() {
        targetPosition = positionInches.getAsDouble();
        elevator.setTargetHeight(targetPosition);
    }

    @Override 
    public void execute() {
        // N/A
    }

    @Override
    public boolean isFinished() {
        return elevator.isAtSetpoint();
    }

}