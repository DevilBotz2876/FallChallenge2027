package frc.robot.commands.common.elevator;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.interfaces.Elevator;
import frc.robot.subsystems.interfaces.Motor;
import java.util.function.DoubleSupplier;

public class ElevatorToPosition extends Command {
    double targetPosition;
    Elevator elevator;
    DoubleSupplier positionInches;

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
        // do nothing
    }
  
@Override
public boolean isFinished() {
    return elevator.isAtSetpoint();
}
}