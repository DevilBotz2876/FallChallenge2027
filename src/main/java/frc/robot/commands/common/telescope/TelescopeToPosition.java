package frc.robot.commands.common.telescope;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.interfaces.Telescope;
import frc.robot.subsystems.interfaces.Motor;
import java.util.function.DoubleSupplier;

public class TelescopeToPosition extends Command {
    Telescope telescope;
    double targetPosition;
    DoubleSupplier positionInches;

    public TelescopeToPosition(Telescope telescope, DoubleSupplier positionInches) {
        this.telescope = telescope;
        this.positionInches = positionInches;

        addRequirements((Subsystem) telescope);
    }

@Override
public void initialize(){
    targetPosition = positionInches.getAsDouble();
    telescope.setTargetLength(targetPosition);
}

@Override
public void execute() {
        // do nothing
    }
  
@Override
public boolean isFinished() {
    return telescope.isAtSetpoint();
}
}