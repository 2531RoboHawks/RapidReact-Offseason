// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.SynchronizedClimbCommand;
import frc.robot.commands.auto.AutoDriveCommand;
import frc.robot.commands.auto.AutoTurnAroundCommand;
import frc.robot.commands.auto.ShootBallCommand;
import frc.robot.commands.auto.TrajectoryCommand;
import frc.robot.commands.auto.Waypoint;
import frc.robot.commands.auto.MoveSetDistanceFromTarget;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.LimelightTrackCommand;
import frc.robot.commands.ManualClimbCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.CompressorSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // public static final ClimbSubsystem climbSubsystem = new ClimbSubsystem();
  public static final DriveSubsystem driveSubsystem = new DriveSubsystem();
  // public static final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  // public static final ShootSubsystem shootSubsystem = new ShootSubsystem();
  // public static final VisionSubsystem visionSubsystem = new VisionSubsystem();
  // public static final CompressorSubsystem compressorSubsystem = new CompressorSubsystem();

  // public final SynchronizedClimbCommand synchronizedClimbCommand = new SynchronizedClimbCommand(climbSubsystem, intakeSubsystem);
  // public final ManualClimbCommand manualClimbCommand = new ManualClimbCommand(climbSubsystem, intakeSubsystem);
  // public final DriveCommand driveCommand = new DriveCommand(driveSubsystem);
  // public final IntakeCommand intakeCommand = new IntakeCommand(intakeSubsystem);
  // public final ShootCommand shootCommand = new ShootCommand(shootSubsystem);

  // public final LimelightTrackCommand limelightTrackCommand = new LimelightTrackCommand(visionSubsystem, driveSubsystem);

  public static XboxController gamepad = new XboxController(0);
  public static XboxController helms = new XboxController(1);

  private SendableChooser<Command> autoChooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureButtonBindings();

    // intakeSubsystem.setDefaultCommand(intakeCommand);
    driveSubsystem.setDefaultCommand(new DriveCommand(driveSubsystem));
    // shootSubsystem.setDefaultCommand(shootCommand);

    // SmartDashboard.putData("Synchronized climb command", synchronizedClimbCommand);
    // SmartDashboard.putData("Manual climb command", manualClimbCommand);
    // SmartDashboard.putData("Drive command", driveCommand);
    // SmartDashboard.putData("Intake command", intakeCommand);
    // SmartDashboard.putData("Shoot command", shootCommand);
    // SmartDashboard.putData("Limelight Track command", limelightTrackCommand);

    autoChooser.addOption("None", null);

    autoChooser.setDefaultOption(
      "8 Ball Auto",
      TrajectoryCommand.fromWaypoints(
        driveSubsystem,
        Waypoint.LEFT,
        Waypoint.UP,
        Waypoint.DOWN,
        Waypoint.LEFT,
        Waypoint.RIGHT,
        Waypoint.LEFT
      ).resetOdometry()
    );
    autoChooser.addOption(
      "Taxi",
      new AutoDriveCommand(driveSubsystem, 0.2, 0, 0).withTimeout(5)
    );
    autoChooser.setDefaultOption(
      "The Rumbling",
      new SequentialCommandGroup(
        TrajectoryCommand.fromWaypoints(
          driveSubsystem,
          Waypoint.RUMBLING_START,
          Waypoint.RUMBLING_FIRST_BALL,
          Waypoint.RUMBLING_SECOND_BALL,
          Waypoint.RUMBLING_TERMINAL,
          Waypoint.RUMBLING_FINAL_SHOT
        ).resetOdometry()
      )
    );
    // autoChooser.setDefaultOption(
    //   "Primitive One Ball",
    //   new ShootBallCommand(driveSubsystem, shootSubsystem)
    //     .andThen(new AutoDriveCommand(driveSubsystem, 5, -0.2, 0, 0))
    // );
    // autoChooser.addOption(
    //   "One Ball",
    //   new MoveSetDistanceFromTarget(driveSubsystem, visionSubsystem, 65)
    //     .andThen(new ShootBallCommand(driveSubsystem, shootSubsystem))
    //     .andThen(new AutoDriveCommand(driveSubsystem, 5, -0.2, 0, 0))
    // );
    // autoChooser.setDefaultOption(
    //   "One Ball Delayed",
    //   new WaitCommand(5)
    //     .andThen(new MoveSetDistanceFromTarget(driveSubsystem, visionSubsystem, 65))
    //     .andThen(new ShootBallCommand(driveSubsystem, shootSubsystem))
    //     .andThen(new AutoDriveCommand(driveSubsystem, 5, -0.2, 0, 0))
    // );
    // autoChooser.addOption(
    //   "Two Ball",
    //   new MoveSetDistanceFromTarget(driveSubsystem, visionSubsystem, 65)
    //     .withTimeout(1)
    //     .andThen(new ShootBallCommand(driveSubsystem, shootSubsystem))
    //     .andThen(new AutoTurnArounCommand(driveSubsystem))
    //     .andThen(new InstantCommand(() -> {
    //       intakeSubsystem.setDown(true);
    //       shootSubsystem.setTraversePercent(0.6);
    //     }, intakeSubsystem, shootSubsystem))
    //     .andThen(new AutoDriveCommand(driveSubsystem, 3, 0.2, 0, 0))
    //     .andThen(new InstantCommand(() -> {
    //       intakeSubsystem.setDown(false);
    //     }, intakeSubsystem))
    //     .andThen(new AutoTurnArounCommand(driveSubsystem))
    //     .andThen(new LimelightTrackCommand(visionSubsystem, driveSubsystem).withTimeout(1))
    //     .andThen(new MoveSetDistanceFromTarget(driveSubsystem, visionSubsystem, 65).withTimeout(2.5))
    //     .andThen(new ShootBallCommand(driveSubsystem, shootSubsystem))
    // );
    SmartDashboard.putData(autoChooser);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // new JoystickButton(helms, 8).toggleWhenActive(synchronizedClimbCommand);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}
