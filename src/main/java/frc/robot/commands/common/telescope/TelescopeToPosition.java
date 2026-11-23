package frc.robot.commands.common.telescope;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.interfaces.Telescope;
import java.util.function.DoubleSupplier;

public class TelescopeToPosition extends Command {
    Telescope telescope;
    DoubleSupplier positionInches;
    double targetPosition;

    /** Sets the length of the telescope in inches */
    public TelescopeToPosition(Telescope telescope, DoubleSupplier positionInches) {
        this.telescope = telescope;
        this.positionInches = positionInches;

        addRequirements((Subsystem) telescope);
    }

    @Override
    public void initialize() {
        targetPosition = positionInches.getAsDouble();
        telescope.setTargetLength(targetPosition);
    }

    @Override 
    public void execute() {
        // N/A
    }

    @Override
    public boolean isFinished() {
        return telescope.isAtSetpoint();
    }

}