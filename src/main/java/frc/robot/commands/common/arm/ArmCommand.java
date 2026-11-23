package frc.robot.commands.common.arm;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.interfaces.Arm;
import java.util.function.DoubleSupplier;

public class ArmCommand extends Command {
  Arm arm;
  DoubleSupplier speed;
  double targetAngle;

  /** Sets the rotation of the arm in degrees */
  public ArmCommand(Arm arm, DoubleSupplier speed) {
    this.arm = arm;
    this.speed = speed;
    addRequirements((Subsystem) arm);
  }

  @Override
  public void initialize() {
    targetAngle = arm.getCurrentAngle();
  }  

  @Override
  public void execute() {
    double currentSpeed = speed.getAsDouble();
    if (currentSpeed != 0) {
        targetAngle += currentSpeed * arm.getSettings().maxAngleInDegrees;
        targetAngle = MathUtil.clamp(targetAngle, arm.getSettings().minAngleInDegrees, arm.getSettings().maxAngleInDegrees);
        arm.setTargetAngle(targetAngle);
    }
  }
}