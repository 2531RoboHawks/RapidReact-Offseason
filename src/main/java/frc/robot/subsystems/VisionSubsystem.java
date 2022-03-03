package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
    // https://docs.limelightvision.io/en/latest/networktables_api.html
    private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private NetworkTableEntry tv = table.getEntry("tv");
    private NetworkTableEntry tx = table.getEntry("tx");
    private NetworkTableEntry ty = table.getEntry("ty");
    private NetworkTableEntry ta = table.getEntry("ta");
    private NetworkTableEntry ledMode = table.getEntry("ledMode");

    public VisionSubsystem() {

    }

    public boolean hasValidTarget() {
        return tv.getDouble(0) == 1;
    }

    public double getX() {
        return tx.getDouble(0);
    }

    public double getY() {
        return ty.getDouble(0);
    }

    public double getArea() {
        return ta.getDouble(0);
    }

    public double getDistance() {
        double targetHeight = 102.5; // inches
        double mountHeight = 38.25; // inches
        double mountAngle = 34; // degrees
        double y = getY();
        return (targetHeight - mountHeight) / Math.tan((mountAngle + y) * (Math.PI / 180.0)) + 8.0;
    }

    public void ensureEnabled() {
        setLightsEnabled(true);
    }

    public void noLongerNeeded() {
        setLightsEnabled(false);
    }

    private void setLightsEnabled(boolean enabled) {
        System.out.println("Limelight enabled: " + enabled);
        // 0 - pipeline default
        // 1 - force off
        // 2 - force blink
        // 3 - force on
        ledMode.setNumber(enabled ? 3 : 1);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Limelight Distance", getDistance());
    }
}
