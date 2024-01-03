// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  public static class Hardware {
    WPI_TalonSRX lFrontMotor, lMidMotor, lSlaveMotor;
    WPI_TalonSRX rFrontMotor, rMidMotor, rSlaveMotor;

    public Hardware(WPI_TalonSRX lFrontMotor,
        WPI_TalonSRX lMidMotor,
        WPI_TalonSRX lSlaveMotor,
        WPI_TalonSRX rFrontMotor,
        WPI_TalonSRX rMidMotor,
        WPI_TalonSRX rSlaveMotor) {
      this.lFrontMotor = lFrontMotor;
      this.lMidMotor = lMidMotor;
      this.lSlaveMotor  = lSlaveMotor;
      this.rFrontMotor = rFrontMotor;
      this.rMidMotor = rMidMotor;
      this.rSlaveMotor = rSlaveMotor;
    }
  }

  private WPI_TalonSRX m_lFrontMotor, m_lMidMotor, m_lSlaveMotor;
  private WPI_TalonSRX m_rFrontMotor, m_rMidMotor, m_rSlaveMotor;
  private DifferentialDrive m_drivetrain;
  /** Creates a new DriveSubsystem. */
  public DriveSubsystem(Hardware driveTrainHardware) {
    this.m_lFrontMotor = driveTrainHardware.lFrontMotor;
    this.m_lMidMotor = driveTrainHardware.lMidMotor;
    this.m_lSlaveMotor = driveTrainHardware.lSlaveMotor;
    this.m_rFrontMotor = driveTrainHardware.rFrontMotor;
    this.m_rMidMotor = driveTrainHardware.rMidMotor;
    this.m_rSlaveMotor = driveTrainHardware.rSlaveMotor;

    m_drivetrain = new DifferentialDrive(m_lFrontMotor, m_rFrontMotor);

    // Invert tbe motors on the right side of the drive subsystem.
    m_rSlaveMotor.setInverted(true);
    m_rFrontMotor.setInverted(true);
    m_rMidMotor.setInverted(true);

    m_lSlaveMotor.follow(m_lFrontMotor);
    m_lMidMotor.follow(m_lFrontMotor);

    m_rSlaveMotor.follow(m_rFrontMotor);
    m_rMidMotor.follow(m_rFrontMotor);
  }

  public static Hardware initializeHardware(){
    Hardware driveTrainHardware = new Hardware(
      new WPI_TalonSRX(Constants.DriveHardware.LEFT_FRONT_MASTER_MOTOR),
      new WPI_TalonSRX(Constants.DriveHardware.LEFT_MID_SLAVE_MOTOR),
      new WPI_TalonSRX(Constants.DriveHardware.LEFT_BACK_SLAVE_MOTOR),
      new WPI_TalonSRX(Constants.DriveHardware.RIGHT_FRONT_MASTER_MOTOR),
      new WPI_TalonSRX(Constants.DriveHardware.RIGHT_MID_SLAVE_MOTOR),
      new WPI_TalonSRX(Constants.DriveHardware.RIGHT_BACK_SLAVE_MOTOR)
    );
    return driveTrainHardware;
  }

  public void set(double speed, double turn){
    m_drivetrain.arcadeDrive(speed, turn);
  }

  public void stop(){
    m_drivetrain.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
