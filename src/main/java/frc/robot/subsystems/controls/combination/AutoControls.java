package frc.robot.subsystems.controls.combination;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.util.FlippingUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.common.flywheel.FlywheelToVelocity;
import frc.robot.commands.common.motor.MotorRunVoltageCommand;
import frc.robot.subsystems.controls.combination.DriverControls.DriverControlsSettings;
import frc.robot.subsystems.interfaces.Drive;
import frc.robot.subsystems.interfaces.Flywheel;
import frc.robot.subsystems.interfaces.Motor;
import frc.robot.util.Elastic;
import frc.robot.util.Elastic.Notification;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import org.littletonrobotics.junction.AutoLogOutput;

public class AutoControls {
  public static class AutoRoutineSettings {
    public double launchOneFuelSeconds;
    public double launchOneTimeoutSeconds;

    public double intakeDepotTimeoutSeconds;
    public double intakeOutpostTimeoutSeconds;

    public double dynamicPathTimeoutSeconds;

    public static AutoRoutineSettings getAutoRoutineSettings(Properties properties) {
      AutoRoutineSettings settings = new AutoRoutineSettings();
      settings.launchOneFuelSeconds =
          Double.parseDouble(properties.getProperty("auto.launchOneFuelSeconds"));
      settings.launchOneTimeoutSeconds =
          Double.parseDouble(properties.getProperty("auto.launchOneTimeoutSeconds"));
      settings.intakeDepotTimeoutSeconds =
          Double.parseDouble(properties.getProperty("auto.intakeDepotTimeoutSeconds"));
      settings.intakeOutpostTimeoutSeconds =
          Double.parseDouble(properties.getProperty("auto.intakeOutpostTimeoutSeconds"));
      settings.dynamicPathTimeoutSeconds =
          Double.parseDouble(properties.getProperty("auto.dynamicPathTimeoutSeconds"));
      return settings;
    }
  }

  @AutoLogOutput private static Pose2d pose = new Pose2d();

  public static Pose2d getGoToRadiusPose2d(Drive drive) {
    double radius = SmartDashboard.getNumber("Auto/rad", -1);
    if (radius < 1.18) {
      Elastic.sendNotification(
          new Notification()
              .withTitle("INVAILD RADIUS")
              .withDescription("The radius was less than 1.18. Will not move"));
      return drive.getPose();
    }
    Optional<Alliance> alliance = DriverStation.getAlliance();
    Pose2d drivePose = drive.getPose();
    if (alliance.isPresent()) {
      if (alliance.get() == Alliance.Red) drivePose = FlippingUtil.flipFieldPose(drivePose);
    }

    double x1, y1, x2, y2, dist1, dist2;

    if (drivePose.getX() - DynamicLocation.HUB.getX() == 0) {
      x1 = drivePose.getX();
      y1 = DynamicLocation.HUB.getY() + radius;
      x2 = drivePose.getX();
      y2 = DynamicLocation.HUB.getY() - radius;
    } else {
      double slope =
          (drivePose.getY() - DynamicLocation.HUB.getY())
              / (drivePose.getX() - DynamicLocation.HUB.getX());
      x1 = (radius / Math.sqrt(1 + Math.pow(slope, 2))) + DynamicLocation.HUB.getX();
      x2 = (radius / -Math.sqrt(1 + Math.pow(slope, 2))) + DynamicLocation.HUB.getX();
      y1 = ((radius * slope) / Math.sqrt(1 + Math.pow(slope, 2))) + DynamicLocation.HUB.getY();
      y2 = ((radius * slope) / -Math.sqrt(1 + Math.pow(slope, 2))) + DynamicLocation.HUB.getY();
    }

    dist1 = drive.getPose().getTranslation().getDistance(new Translation2d(x1, y1));
    dist2 = drive.getPose().getTranslation().getDistance(new Translation2d(x2, y2));

    if (alliance.isPresent()) {
      if (alliance.get() == Alliance.Red)
        return dist1 < dist2
            ? new Pose2d(x2, y2, Rotation2d.fromRadians(getHubScoreRotation(x2, y2)))
            : new Pose2d(x1, y1, Rotation2d.fromRadians(getHubScoreRotation(x1, y1)));
    }
    return dist1 < dist2
        ? new Pose2d(x1, y1, Rotation2d.fromRadians(getHubScoreRotation(x1, y1)))
        : new Pose2d(x2, y2, Rotation2d.fromRadians(getHubScoreRotation(x2, y2)));
  }

  public static double calcShooterSpeedFromRad() {
    return 4000;
    // calcShooterSpeedFromDistance(SmartDashboard.getNumber("Auto/rad", -1));
  }

  public static double getHubScoreRotation(double x, double y) {
    double leg1 = DynamicLocation.HUB.getY() - y;
    double leg2 = DynamicLocation.HUB.getX() - x;

    return Math.atan(leg1 / leg2) + Math.PI;
  }
}
