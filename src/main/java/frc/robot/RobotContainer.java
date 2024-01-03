// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.DriveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem DRIVE_SUBSYSTEM = new DriveSubsystem(DriveSubsystem.initializeHardware());

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController PRIMARY_CONTROLLER =
      new CommandXboxController(Constants.HID.PRIMARY_CONTROLLER_PORT);


  private static final SendableChooser<SequentialCommandGroup> m_autoModeChooser = new SendableChooser<>();
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    DRIVE_SUBSYSTEM.setDefaultCommand(
      new RunCommand(
        () -> DRIVE_SUBSYSTEM.set(PRIMARY_CONTROLLER.getLeftY(), PRIMARY_CONTROLLER.getRightX()), 
        DRIVE_SUBSYSTEM)
    );
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {

  }

  private void m_autoModeChooser(){
    m_autoModeChooser.setDefaultOption("Do Nothing", new SequentialCommandGroup());
    m_autoModeChooser.addOption("Drive Forward", new SequentialCommandGroup(
      DRIVE_SUBSYSTEM.run(() -> DRIVE_SUBSYSTEM.set(0.5, 0.0))
      .withTimeout(5)
      .andThen(() -> DRIVE_SUBSYSTEM.stop())
    ));
    m_autoModeChooser.addOption("Drive forward and then backward", new SequentialCommandGroup(
      DRIVE_SUBSYSTEM.run(() -> DRIVE_SUBSYSTEM.set(0.5, 0.0))
      .withTimeout(5)
      .andThen(
        DRIVE_SUBSYSTEM.run(() -> DRIVE_SUBSYSTEM.set(-0.5, 0.0))
          .withTimeout(5)
          .andThen(() -> DRIVE_SUBSYSTEM.stop())
      )
    ));



  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return m_autoModeChooser.getSelected();
  }
}

