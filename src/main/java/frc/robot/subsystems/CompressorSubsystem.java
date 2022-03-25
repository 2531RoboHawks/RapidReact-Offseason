// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CompressorSubsystem extends SubsystemBase {
  private PneumaticsControlModule pcm = new PneumaticsControlModule();
  public CompressorSubsystem() {
    // pcm.disableCompressor();
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Pressure Switch", pcm.getPressureSwitch());
  }
}
