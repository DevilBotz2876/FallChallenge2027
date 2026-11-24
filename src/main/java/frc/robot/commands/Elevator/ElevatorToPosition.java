package frc.robot.commands.Elevator;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.interfaces.Elevator;

class ElevatorToPosition extends Command{
    public Elevator elevator;
    public DoubleSupplier TargetHeight;

    public ElevatorToPosition(Elevator elevator, DoubleSupplier TargetHeight){
        this.elevator = elevator;
        this.TargetHeight = TargetHeight;
        addRequirements((Subsystem) elevator);
    }

    public void initialize() {
         elevator.setTargetHeight(TargetHeight);
         // add clamp when we know height range
    }

    
    public boolean isFinished(){
    return elevator.isAtSetpoint();
        }
    }