package frc.robot.config.game.rebuilt2026;

import static edu.wpi.first.units.Units.*;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Robot;
import frc.robot.commands.driveAssist.AssistiveInformationCommand;
import frc.robot.config.game.rebuilt2026.tunerConstants.TunerConstants;
import frc.robot.io.implementations.motor.MotorIOBase.MotorIOBaseSettings;
import frc.robot.io.implementations.motor.MotorIOFlywheelStub;
import frc.robot.io.implementations.motor.MotorIOSparkMax;
import frc.robot.io.implementations.motor.MotorIOSparkMax.SparkMaxSettings;
import frc.robot.io.implementations.motor.MotorIOStub;
import frc.robot.io.implementations.motor.MotorIOTalonFx;
import frc.robot.io.implementations.motor.MotorIOTalonFx.TalonFxSettings;
import frc.robot.subsystems.controls.combination.AutoControls;
import frc.robot.subsystems.controls.combination.AutoControls.AutoRoutineSettings;
import frc.robot.subsystems.controls.combination.DriverControls;
import frc.robot.subsystems.controls.combination.DriverControls.DefenseControlsSettings;
import frc.robot.subsystems.controls.combination.DriverControls.DriverControlsSettings;
import frc.robot.subsystems.controls.drive.DriveControls;
import frc.robot.subsystems.controls.flywheel.ConveyorControls;
import frc.robot.subsystems.controls.flywheel.IntakeControls;
import frc.robot.subsystems.controls.flywheel.ShooterControls;
import frc.robot.subsystems.implementations.drive.DriveBase;
import frc.robot.subsystems.implementations.drive.DriveSwerveCTRE;
import frc.robot.subsystems.implementations.drive.DriveSwerveCTRE.DriveSettings;
import frc.robot.subsystems.implementations.motor.FlywheelMotorSubsystem;
import frc.robot.subsystems.implementations.motor.SimpleMotorSubsystem;
import frc.robot.subsystems.implementations.vision.VisionSubsystem;
import frc.robot.subsystems.implementations.vision.camera.CameraBase;
import frc.robot.subsystems.implementations.vision.camera.CameraPhoton;
import frc.robot.subsystems.implementations.vision.camera.CameraPhotonSim;
import frc.robot.subsystems.interfaces.Flywheel.FlywheelSettings;
import frc.robot.subsystems.interfaces.SimpleMotor.SimpleMotorSettings;
import frc.robot.subsystems.interfaces.Vision.VisionSettings;
import frc.robot.util.Elastic;
import java.util.Optional;
import java.util.Properties;

/* Put all constants here with reasonable defaults */
public class RobotConfig {
  public DriveBase drive;
  public SendableChooser<Command> autoChooser;
  public VisionSubsystem vision;
  public Command assistCommand;

  public Properties properties;

  // Controls
  public CommandXboxController mainController = new CommandXboxController(0);
  public CommandXboxController assistController = new CommandXboxController(1);

  public RobotConfig(Properties robotProperties) {
    if (robotProperties.containsKey("robot.drive")) {
      if (robotProperties.getProperty("robot.drive").equals("ctre")) {
        drive =
            new DriveSwerveCTRE(
                new TunerConstants(robotProperties),
                DriveSettings.getDriveSettings(robotProperties));
      }
    } else {
      drive = new DriveBase("Stub");
      Elastic.sendNotification(
          new Elastic.Notification()
              .withDescription("USING DRIVE BASE. UNABLE TO RUN AUTO ROUTINES"));
    }

    if (Robot.isSimulation()) {
      drive.setPose(new Pose2d(new Translation2d(1, 1), new Rotation2d()));
    }

    vision = createVisionSubsystem(robotProperties);
    properties = robotProperties;
    if (robotProperties.containsKey("robot.drive")) {
      if (robotProperties.getProperty("robot.drive").equals("ctre")) {
        DriverControlsSettings driverSettings =
            DriverControlsSettings.getDriverControlsSettings(robotProperties);
        AutoRoutineSettings autoRoutineSettings =
            AutoRoutineSettings.getAutoRoutineSettings(robotProperties);
        this.autoChooser =
            AutoBuilder.buildAutoChooserWithOptionsModifier(
                (stream) -> {
                  stream =
                      stream.filter(
                          auto ->
                              auto.getName()
                                  .startsWith(
                                      properties.getProperty("robot.name").toLowerCase() + "-"));
                  return stream;
                });
      }
    }
  }

  public void configureBindings() {
    DriverControlsSettings driverSettings =
        DriverControlsSettings.getDriverControlsSettings(properties);
    DriveControls.setupController(drive, mainController);


    if (Boolean.parseBoolean(properties.getProperty("robot.isDefenseBot", "false"))) {
      DriverControls.setupDefenseController(
          drive, mainController, DefenseControlsSettings.getDefenseControlsSettings(properties));
    } else {
      DriverControls.setupMainController(
          drive,
          mainController,
          driverSettings);
    }

    DriverControls.setupPitControls();
    assistCommand = new AssistiveInformationCommand(drive, driverSettings);

    if (null != this.autoChooser) {
      SmartDashboard.putData("Autonomous", this.autoChooser);
    }
  }

  private SimpleMotorSubsystem createSimpleMotor(Properties robotProperties, String name) {
    SimpleMotorSettings simpleMotorSettings = new SimpleMotorSettings();
    String simpleMotorSettingsPrefix = name + ".simpleMotorSettings";

    simpleMotorSettings.color =
        new Color8Bit(
            Integer.parseInt(robotProperties.getProperty(simpleMotorSettingsPrefix + ".color.red")),
            Integer.parseInt(
                robotProperties.getProperty(simpleMotorSettingsPrefix + ".color.green")),
            Integer.parseInt(
                robotProperties.getProperty(simpleMotorSettingsPrefix + ".color.blue")));

    simpleMotorSettings.maxVelocityInRadiansPerSecond =
        Double.parseDouble(
            robotProperties.getProperty(
                simpleMotorSettingsPrefix + ".maxVelocityInRadiansPerSecond"));
    simpleMotorSettings.maxAccelerationInRadiansPerSecondSquared =
        Double.parseDouble(
            robotProperties.getProperty(
                simpleMotorSettingsPrefix + ".maxAccelerationInRadiansPerSecondSquared"));

    simpleMotorSettings.targetPositionToleranceInRad =
        Double.parseDouble(
            robotProperties.getProperty(
                simpleMotorSettingsPrefix + ".targetPositionToleranceInRad"));
    simpleMotorSettings.maxPositionInRads =
        Double.parseDouble(
            robotProperties.getProperty(simpleMotorSettingsPrefix + ".maxPositionInRads"));
    simpleMotorSettings.minPositionInRads =
        Double.parseDouble(
            robotProperties.getProperty(simpleMotorSettingsPrefix + ".minPositionInRads"));
    simpleMotorSettings.startingPositionInRads =
        Double.parseDouble(
            robotProperties.getProperty(simpleMotorSettingsPrefix + ".startingPositionInRads"));
    simpleMotorSettings.moiKgMetersSquared =
        Double.parseDouble(
            robotProperties.getProperty(simpleMotorSettingsPrefix + ".moiKgMetersSquared"));

    simpleMotorSettings.feedforward =
        new SimpleMotorFeedforward(
            Double.parseDouble(
                robotProperties.getProperty(simpleMotorSettingsPrefix + ".feedforward.ks")),
            Double.parseDouble(
                robotProperties.getProperty(simpleMotorSettingsPrefix + ".feedforward.kv")),
            Double.parseDouble(
                robotProperties.getProperty(simpleMotorSettingsPrefix + ".feedforward.ka", "0.0")));

    simpleMotorSettings.motor =
        getDCMotor(robotProperties.getProperty(simpleMotorSettingsPrefix + ".DCMotor"));

    MotorIOBaseSettings IOSettings = getMotorIOBaseSettings(robotProperties, name);

    switch (robotProperties.getProperty(name + ".motor.motorController")) {
      case "talonFX":
        TalonFxSettings talonSettings = TalonFxSettings.getSettings(robotProperties, name);
        return new SimpleMotorSubsystem(
            new MotorIOTalonFx(IOSettings, talonSettings), name, simpleMotorSettings);

      case "sparkMax":
        SparkMaxSettings sparkMaxSettings = SparkMaxSettings.getSettings(robotProperties, name);
        return new SimpleMotorSubsystem(
            new MotorIOSparkMax(IOSettings, sparkMaxSettings), name, simpleMotorSettings);

      case "sim":
      default:
        return new SimpleMotorSubsystem(
            new MotorIOStub(IOSettings, simpleMotorSettings), name, simpleMotorSettings);
    }
  }

  private FlywheelMotorSubsystem createFlywheel(Properties robotProperties, String name) {
    FlywheelSettings flywheelSettings = new FlywheelSettings();
    String flywheelSettingsPrefix = name + ".flywheelSettings";

    flywheelSettings.color =
        new Color8Bit(
            Integer.parseInt(robotProperties.getProperty(flywheelSettingsPrefix + ".color.red")),
            Integer.parseInt(robotProperties.getProperty(flywheelSettingsPrefix + ".color.green")),
            Integer.parseInt(robotProperties.getProperty(flywheelSettingsPrefix + ".color.blue")));

    flywheelSettings.maxVelocityInRPMs =
        Double.parseDouble(
            robotProperties.getProperty(flywheelSettingsPrefix + ".maxVelocityInRPMs"));
    flywheelSettings.targetVelocityToleranceInRPMs =
        Double.parseDouble(
            robotProperties.getProperty(flywheelSettingsPrefix + ".targetVelocityToleranceInRPMs"));
    flywheelSettings.moiKgMetersSquared =
        Double.parseDouble(
            robotProperties.getProperty(flywheelSettingsPrefix + ".moiKgMetersSquared"));

    flywheelSettings.feedforward =
        new SimpleMotorFeedforward(
            Double.parseDouble(
                robotProperties.getProperty(flywheelSettingsPrefix + ".feedforward.ks")),
            Double.parseDouble(
                robotProperties.getProperty(flywheelSettingsPrefix + ".feedforward.kv")),
            Double.parseDouble(
                robotProperties.getProperty(flywheelSettingsPrefix + ".feedforward.ka", "0.0")));

    flywheelSettings.motor =
        getDCMotor(robotProperties.getProperty(flywheelSettingsPrefix + ".DCMotor"));

    MotorIOBaseSettings IOSettings = getMotorIOBaseSettings(robotProperties, name);

    switch (robotProperties.getProperty(name + ".motor.motorController")) {
      case "talonFX":
        TalonFxSettings talonSettings = TalonFxSettings.getSettings(robotProperties, name);
        return new FlywheelMotorSubsystem(
            new MotorIOTalonFx(IOSettings, talonSettings), name, flywheelSettings);

      case "sparkMax":
        SparkMaxSettings sparkMaxSettings = SparkMaxSettings.getSettings(robotProperties, name);
        return new FlywheelMotorSubsystem(
            new MotorIOSparkMax(IOSettings, sparkMaxSettings), name, flywheelSettings);

      case "sim":
      default:
        return new FlywheelMotorSubsystem(
            new MotorIOFlywheelStub(IOSettings, flywheelSettings), name, flywheelSettings);
    }
  }

  private MotorIOBaseSettings getMotorIOBaseSettings(Properties robotProperties, String name) {
    MotorIOBaseSettings IOSettings = new MotorIOBaseSettings();
    String IOSettingsPrefix = name + ".IOSettings";

    IOSettings.motor.inverted =
        Boolean.parseBoolean(robotProperties.getProperty(IOSettingsPrefix + ".inverted"));
    IOSettings.motor.gearing =
        Double.parseDouble(robotProperties.getProperty(IOSettingsPrefix + ".gearing"));
    IOSettings.motor.drumRadiusMeters =
        Double.parseDouble(robotProperties.getProperty(IOSettingsPrefix + ".drumRadiusMeters"));

    IOSettings.pid =
        new PIDController(
            Double.parseDouble(robotProperties.getProperty(IOSettingsPrefix + ".pid.kp")),
            Double.parseDouble(robotProperties.getProperty(IOSettingsPrefix + ".pid.ki")),
            Double.parseDouble(robotProperties.getProperty(IOSettingsPrefix + ".pid.kd")));

    return IOSettings;
  }

  private DCMotor getDCMotor(String motor) {
    switch (motor) {
      case "KrakenX60":
        return DCMotor.getKrakenX60(1);
      case "Neo550":
        return DCMotor.getNeo550(1);
      // more motors if needed
      default:
        return DCMotor.getKrakenX60(1);
    }
  }

  private VisionSubsystem createVisionSubsystem(Properties robotProperties) {
    VisionSettings settings = VisionSettings.getSettings(robotProperties);

    if (Boolean.parseBoolean(robotProperties.getProperty("vision.addDrivetrain"))) {
      vision =
          new VisionSubsystem(
              AprilTagFieldLayout.loadField(
                  AprilTagFields.valueOf(robotProperties.getProperty("vision.fieldlayout"))),
              Optional.of(drive::addVisionMeasurement),
              settings);
    } else {
      vision =
          new VisionSubsystem(
              AprilTagFieldLayout.loadField(
                  AprilTagFields.valueOf(robotProperties.getProperty("vision.fieldlayout"))),
              Optional.empty(),
              settings);
    }

    String[] cameraNames = robotProperties.getProperty("vision.cameras", "").split(", ");
    System.out.println(cameraNames[0]);
    for (String cameraName : cameraNames) {
      if (cameraName.isEmpty()) break;
      switch (robotProperties.getProperty(cameraName + ".cameraType")) {
        case "photon":
          vision.addCamera(
              CameraPhoton.createCameraPhoton(
                  robotProperties, cameraName, vision.getFieldLayout()));
          break;
        case "photonSim":
          vision.addCamera(
              CameraPhotonSim.createCameraPhotonSim(
                  robotProperties, cameraName, vision.getFieldLayout(), () -> drive.getPose()));
          break;
        default:
          vision.addCamera(CameraBase.createCameraBase(robotProperties, cameraName));
          break;
      }
    }
    return vision;
  }
}
