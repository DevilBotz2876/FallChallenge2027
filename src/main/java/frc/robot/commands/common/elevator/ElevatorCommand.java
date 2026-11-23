package frc.robot.commands.common.elevator;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.interfaces.Elevator;
import java.util.function.DoubleSupplier;

public class ElevatorCommand extends Command {
  Elevator elevator;
  DoubleSupplier height;
  double targetHeight;

  /** Sets the height of the elevator in inches */
  public ElevatorCommand(Elevator elevator, DoubleSupplier height) {
    this.elevator = elevator;
    this.height = height;
    addRequirements((Subsystem) elevator);
  }

  @Override
  public void initialize() {
    targetHeight = elevator.getCurrentHeight();
  }  

  @Override
  public void execute() {
    double currentHeight = height.getAsDouble();
    if (currentHeight != 0) {
        targetHeight += currentHeight * elevator.getSettings().maxHeightInInches;
        targetHeight = MathUtil.clamp(targetHeight, elevator.getSettings().minHeightInInches, elevator.getSettings().maxHeightInInches);
        elevator.setTargetHeight(targetHeight);
    }
  }
}