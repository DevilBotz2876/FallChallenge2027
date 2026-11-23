package frc.robot.commands.common.elevator;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.interfaces.Elevator;
import frc.robot.subsystems.interfaces.Motor;
import java.util.function.DoubleSupplier;

public class ElevatorToPosition extends Command {
  Elevator elevator;
  DoubleSupplier positionDegrees;
  double targetPositionInMeters;

    public ElevatorToVelocity(Elevator elevator, DoubleSupplier positionDegrees) {
        this.elevator = elevator;
        this.positionDegrees = positionDegrees;

        addRequirements((Subsystem) elevator);
  } 

  @Override
  public void initialize() {
   targetPositionInMeters = positionDegrees.getAsDouble();

   elevator.setTargetHeight(targetPositionInMeters);

}

  @Override
  public void execute() {
    
}

  @Override
  public boolean isFinished() {
    return elevator.isAtSetpoint();
  }
}