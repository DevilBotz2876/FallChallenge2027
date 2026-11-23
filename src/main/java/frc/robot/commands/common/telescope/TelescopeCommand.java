package frc.robot.commands.common.telescope;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.interfaces.Telescope;
import frc.robot.subsystems.interfaces.Motor;
import java.util.function.DoubleSupplier;

public class TelescopeCommand extends Command {
    Telescope telescope;
    double targetLenght;
    DoubleSupplier length;

    public TelescopeCommand(Telescope telescope, DoubleSupplier length) {
        this.telescope = telescope;
        this.length = length;

        addRequirements((Subsystem) telescope);
    }

@Override
public void initialize(){
    targetLength = telescope.getCurrentLength();
}

@Override
public void execute(){
    double currentLength = length.getAsDouble();
    if(currentLength != 0) {
        targetLength += currentLength * telescope.getSettings().maxLength;
        targetLength = MathUtil.clamp(targetPosition, minLength, maxLength);
        telescope.setTargetLength(targetLength);
    }
}
} 