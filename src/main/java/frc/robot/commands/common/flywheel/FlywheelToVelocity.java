package frc.robot.commands.common.flywheel;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.interfaces.Flywheel;
import frc.robot.subsystems.interfaces.Motor;
import java.util.function.DoubleSupplier;

public class FlywheelToVelocity extends Command {
  Flywheel flywheel;
  DoubleSupplier velocityRPM;
  double targetVelocity;
 
  /** Sets speed of a flywheel in RPM. */
  public FlywheelToVelocity(Flywheel flywheel, DoubleSupplier velocityRPM) {
    this.flywheel = flywheel;
    this.velocityRPM = velocityRPM;

    addRequirements((Subsystem) flywheel);
  }

  @Override
  public void initialize() {
    targetVelocity =
        MathUtil.clamp(
            velocityRPM.getAsDouble(),
            -flywheel.getSettings().maxVelocityInRPMs,
            flywheel.getSettings().maxVelocityInRPMs);
  }

  @Override
  public void execute() {
    // stop the flywheel if target velo is zero
    flywheel.setTargetVelocity(targetVelocity);
    if (targetVelocity == 0.0) {
      ((Motor) flywheel).runVoltage(0.0);
    }
  }

  @Override
  public boolean isFinished() {
    return flywheel.isAtSetpoint();
  }
}
