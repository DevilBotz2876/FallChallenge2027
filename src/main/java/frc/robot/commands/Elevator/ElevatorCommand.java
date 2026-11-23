package frc.robot.commands.Elevator;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.interfaces.Elevator;

class ElevatorCommand extends Command{
    public Elevator elevator;
    public DoubleSupplier speed;
    public double TargetHeight;

    public ElevatorCommand(Elevator elevator, DoubleSupplier speed){
        this.elevator = elevator;
        this.speed = speed;
        addRequirements((Subsystem) elevator);
    }

    public void initialize() {
        TargetHeight = elevator.getCurrentHeight();
    }

    public void execute() {
        double currentSpeed = speed.getAsDouble();
        if (currentSpeed != 0) {
            TargetHeight += currentSpeed * 0.02;
            TargetHeight = MathUtil.clamp(TargetHeight, elevator.getMinHeight(), elevator.getMaxHeight());
            elevator.setTargetHeight(TargetHeight);
        }
    }
}