package frc.robot.commands.common.arm;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.interfaces.Arm;
import frc.robot.subsystems.interfaces.Motor;
import java.util.function.DoubleSupplier;

public class ArmToPosition extends Command{
    double targetPosition;
    Arm arm;
    DoubleSupplier positionDeg;

    public ArmToPosition(Arm arm, DoubleSupplier positionDeg) {
        this.arm = arm;
        this.positionDeg = positionDeg

        addRequirements((Subsystem) arm);
    }

    @Override
    public void initialize() {
        targetPosition = positionDeg.getAsDouble();
        arm.setTargetAngle(targetPosition);
    }

    @Override
    public void execute() {
        // do nothing
    }

    @Override
    public boolean isFinished(){
        return arm.isAtSetpoint();
    }
}