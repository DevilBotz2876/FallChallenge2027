// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.util.PathPlannerLogging;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.config.game.rebuilt2026.*;
import frc.robot.util.Elastic;
import java.io.FileReader;
import java.util.Properties;
import org.littletonrobotics.junction.Logger;

public class RobotContainer {
  public RobotConfig robotConfig;

  public RobotContainer() {
    // Load robot name from configuration file
    // Check if the robot is running in simulation
    Properties robotProperties = new Properties();

    // get configuration from robot_config.properties
    try {
      String configPath = "";
      if (Robot.isReal()) {
        configPath = "/home/lvuser/deploy";
      } else {
        configPath = "src/main/deploy";
      }
      FileReader propertiesFile = new FileReader(configPath + "/robot_config.properties");
      robotProperties.load(propertiesFile);
      propertiesFile.close();
    } catch (Exception e) {
      System.out.println(e);
    }

    String robotName = robotProperties.getProperty("robot.name", "UNKNOWN");
    robotConfig = new RobotConfig(robotProperties);

    Preferences.initString("Robot Name", robotName);
    robotName = Preferences.getString("Robot Name", robotName);

    System.out.println("Loading Settings for Robot Name = " + robotName);
    Elastic.sendNotification(
        new Elastic.Notification()
            .withDescription("Loading Settings for Robot Name = " + robotName));

    robotConfig.configureBindings();

    PathPlannerLogging.setLogActivePathCallback(
        (activePath) ->
            Logger.recordOutput("Pathplanner/activePath", activePath.toArray(new Pose2d[0])));
    PathPlannerLogging.setLogCurrentPoseCallback(
        (currentPose) -> Logger.recordOutput("Pathplanner/currentPose", currentPose));

    PathPlannerLogging.setLogTargetPoseCallback(
        (targetPose) -> Logger.recordOutput("Pathplanner/activePath", targetPose));

    // CameraServer.startAutomaticCapture();
  }

  public Command getAutonomousCommand() {
    return robotConfig.autoChooser.getSelected();
  }
}
