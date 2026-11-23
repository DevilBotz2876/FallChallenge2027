package frc.robot.commands.common.elevator;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.interfaces.Elevator;
import frc.robot.subsystems.interfaces.Motor;
import java.util.function.DoubleSupplier;

public class ElevatorCommand extends Command {
    Elevator elevator;
    DoubleSupplier speed;
    double targetHeight; 

     /**
   * Sets a elevator to it's current height and incremnts by a speed * the flywheel max velocity / 50
   * each cycle. This command does not end without being interupted.
   *
   * @param elevator
   * @param speed A double between -1 and 1
   */

 public ElevatorCommand(Elevator elevator, DoubleSupplier speed) {
    this.elevator = elevator;
    this.speed = speed;

    addRequirements((Subsystem) elevator);
  }

@Override
  public void initialize() {
    targetVelocity = elevator.getCurrentHeight();
  }

 @Override
  public void execute() {
    double currentSpeed = speed.getAsDouble();

//dont know what to replace RPMs with, meters? but its not in settings

    if (currentSpeed != 0) {
       targetHeight += currentSpeed * elevator.getSettings().maxVelocityInRPMs / 50;
    targetVelocity =
        MathUtil.clamp(
            targetVelocity,
            elevator.getSettings().minHeightInMeters,
            elevator.getSettings().maxHeightInMeters);

    elevator.setTargetHeight(targetHeight);
    }
  }
}