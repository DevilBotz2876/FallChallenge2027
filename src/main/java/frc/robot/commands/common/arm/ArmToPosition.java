package frc.robot.commands.common.arm;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.interfaces.Arm;
import java.util.function.DoubleSupplier;

public class ArmToPosition extends Command {
    Arm arm;
    DoubleSupplier positionDeg;
    double targetPosition;

    /** Sets the rotation of the arm in degrees */
    public ArmToPosition(Arm arm, DoubleSupplier positionDeg) {
        this.arm = arm;
        this.positionDeg = positionDeg;

        addRequirements((Subsystem) arm);
    }

    @Override
    public void initialize() {
        targetPosition = positionDeg.getAsDouble();
        arm.setTargetAngle(targetPosition);
    }

    @Override 
    public void execute() {
        // N/A
    }

    @Override
    public boolean isFinished() {
        return arm.isAtSetpoint();
    }

}