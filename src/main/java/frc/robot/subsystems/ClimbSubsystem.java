package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterTalonFX;
import frc.robot.PIDSettings;

public class ClimbSubsystem extends SubsystemBase {
  public BetterTalonFX leftTalon = new BetterTalonFX(21);
  public BetterTalonFX rightTalon = new BetterTalonFX(22);
  private Solenoid solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 2);

  private static final PIDSettings talonPid = new PIDSettings(0.014, 0, 0);
  private static final double secondsFromNeutralToFull = 1;

  public ClimbSubsystem() {
    leftTalon.configurePID(talonPid);
    leftTalon.configureRamp(secondsFromNeutralToFull);

    rightTalon.configurePID(talonPid);
    rightTalon.configureRamp(secondsFromNeutralToFull);

    setPistonExtended(false);
  }

  public double getArmExtensionTarget() {
    return leftTalon.getFixedEncoderTarget();
  }
  public void setArmExtensionTarget(double sensorUnits) {
    double MIN = 0;
    double MAX = 360000;
    if (sensorUnits < MIN) {
      System.out.println("CLIMB TOO LOW: " + sensorUnits);
      sensorUnits = MIN;
    }
    if (sensorUnits > MAX) {
      System.out.println("CLIMB TOO HIGH: " + sensorUnits);
      sensorUnits = MAX;
    }
    leftTalon.setFixedEncoderTarget(sensorUnits);
    rightTalon.setFixedEncoderTarget(sensorUnits);
  }
  public void zero() {
    leftTalon.zeroFixedEncoderTarget();
    rightTalon.zeroFixedEncoderTarget();
  }
  public void setExtensionPercent(double percent) {
    leftTalon.setPower(percent);
    rightTalon.setPower(percent);
  }

  public void setPistonExtended(boolean extended) {
    System.out.println("Climb piston extended: " + extended);
    solenoid.set(extended);
  }
  public void togglePistonExtended() {
    // don't use .toggle() here so that we get fancy log messages
    setPistonExtended(!solenoid.get());
  }

  public void stop() {
    leftTalon.stop();
    rightTalon.stop();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Left Climb", leftTalon.getPositionSensorUnits());
    SmartDashboard.putNumber("Right Climb", rightTalon.getPositionSensorUnits());
  }
}
