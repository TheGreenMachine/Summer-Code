
package org.usfirst.frc.team1816.robot;

import org.usfirst.frc.team1816.robot.commands.ExampleCommand;
import org.usfirst.frc.team1816.robot.subsystems.SpeedControlDrivetrain;
import org.usfirst.frc.team1816.robot.subsystems.ExampleSubsystem;

import com.edinarobotics.utils.gamepad.Joystick;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	private SpeedControlDrivetrain drivetrain;
	
	public Joystick joystick;
	
	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	
	//AnalogInput ai = new AnalogInput(2);
	//int averageRaw;
	//double averageVoltage;

	@Override
	public void robotInit() {
		
		drivetrain = new SpeedControlDrivetrain(1, 2, 4, 3);
		joystick = new Joystick(1);
		
		oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		//ai.setOversampleBits(4);
		//ai.setAverageBits(2);
		//AnalogInput.setGlobalSampleRate(62500);
		
	}


	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}


	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}


	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}


	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		//averageRaw = ai.getAverageValue();
		//averageVoltage = ai.getAverageVoltage();
		//System.out.println("Average voltage:" + averageVoltage + "\tAverage Raw: " + averageRaw);
		
		double joypos = joystick.getY();
		System.out.println(joypos);
		
		drivetrain.setTalonTargetSpeed(joypos);
		drivetrain.getTalonSpeed();
	}


	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
