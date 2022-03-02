package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase {
  private IntakeSubsystem intakeSubsystem;

  public IntakeCommand(IntakeSubsystem intakeSubsystem) {
    this.intakeSubsystem = intakeSubsystem;
    addRequirements(intakeSubsystem);
  }

  @Override
  public void initialize() {
    intakeSubsystem.setDown(false);
  }

  @Override
  public void execute() {
    if (RobotContainer.gamepad.getRawButtonPressed(Constants.Controls.ToggleIntakeDown)) {
      intakeSubsystem.toggleDown();
      if (intakeSubsystem.isIntakeSupposedToBeDown()) {
        // suffer
        intakeSubsystem.setPower(0.4);
      } else {
        intakeSubsystem.stop();
      }
    }
  }

  @Override
  public void end(boolean interrupted) {
    intakeSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
