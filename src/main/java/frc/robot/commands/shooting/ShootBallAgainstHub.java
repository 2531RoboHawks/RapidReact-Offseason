// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.DriveDistance;
import frc.robot.commands.auto.ResetOdometry;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.StorageSubsystem;

public class ShootBallAgainstHub extends SequentialCommandGroup {
  private ShootSubsystem shootSubsystem = RobotContainer.shootSubsystem;
  private IntakeSubsystem intakeSubsystem = RobotContainer.intakeSubsystem;
  private DriveSubsystem driveSubsystem = RobotContainer.driveSubsystem;
  private StorageSubsystem storageSubsystem = RobotContainer.storageSubsystem;

  public ShootBallAgainstHub(double rpm, double distance) {
    addCommands(
      new SequentialCommandGroup(
        new ResetOdometry(driveSubsystem),
        new DriveDistance(distance, driveSubsystem).withTimeout(2),
        new WaitForShooterToBeAtSpeed(rpm, shootSubsystem),
        new MoveBallToShooter(storageSubsystem),
        new PrepareToShootBall(storageSubsystem),
        new WaitForShooterToBeAtSpeed(rpm, shootSubsystem),
        new MoveBallToShooter(storageSubsystem)
      ).deadlineWith(new RevShooterToSpeed(rpm, shootSubsystem))
    );
  }
}
