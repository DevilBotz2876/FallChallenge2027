package frc.robot.commands.common.elevator;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.interfaces.Elevator;
import frc.robot.subsystems.interfaces.Motor;
import java.util.function.DoubleSupplier;

public class ElevatorCommand extends Command {
    Elevator elevator;
    double targetHeight;
    DoubleSupplier height;

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
        targetHeight += currentHeight * elevator.getSettings().maxHeight;
        targetHeight = MathUtil.clamp(targetHeight, minHeight, maxHeight);
        elevator.setTargetHeight(targetHeight);
    }
  }
}
}