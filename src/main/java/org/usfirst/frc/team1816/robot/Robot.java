
package org.usfirst.frc.team1816.robot;

import org.usfirst.frc.team1816.robot.commands.DriveXInches;
import org.usfirst.frc.team1816.robot.commands.ExampleCommand;
import org.usfirst.frc.team1816.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team1816.robot.subsystems.SpeedControlDrivetrain;

import com.edinarobotics.utils.log.Logging;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

    public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
    private SpeedControlDrivetrain drivetrain;
    private double time;
    private Joystick joystick;
    private Logging logging;

    public static OI oi;

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    // AnalogInput ai = new AnalogInput(2);
    // int averageRaw;
    // double averageVoltage;1


    @Override
    public void robotInit() {
        Components.getInstance();
        drivetrain = Components.getInstance().drivetrain;
        joystick = new Joystick(1);

        oi = new OI();
        chooser.addDefault("Default Auto", new DriveXInches(6, 0.5));
        // chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);
        // ai.setOversampleBits(4);
        // ai.setAverageBits(2);
        // AnalogInput.setGlobalSampleRate(62500);
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        if (logging != null) {
            logging.close();
        }
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = (Command) new DriveXInches(6, 0.5);
        logging = new Logging("log");
        time = System.currentTimeMillis();

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
        logging.log("Time: " + (System.currentTimeMillis() - time) + " Right Position: " + drivetrain.talonPositionRight() + " Left Position: " + drivetrain.talonPositionLeft());
    }

    @Override
    public void teleopInit() {
        // test comment
        if (autonomousCommand != null)
            autonomousCommand.cancel();
        logging = new Logging("log");
        time = System.currentTimeMillis();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        // averageRaw = ai.getAverageValue();
        // averageVoltage = ai.getAverageVoltage();
        // System.out.println("Average voltage:" + averageVoltage + "\tAverage
        // Raw: " + averageRaw);
        double joypos = -joystick.getY();
        double rotation = joystick.getTwist();
        System.out.println(joypos);
        if (rotation < -0.05) {
            drivetrain.setTalonTargetSpeed(joypos * (1 + rotation), joypos);
            System.out.println("L/R Values: " + joypos + " " + rotation);
        } else if (rotation > 0.05) {
            drivetrain.setTalonTargetSpeed(joypos, joypos * (1 - rotation));
            System.out.println("L/R Values: " + joypos + " " + rotation);
        } else {
            drivetrain.setTalonTargetSpeed(joypos, joypos);
        }
        drivetrain.getTalonSpeed();
        logging.log("Time: " + (System.currentTimeMillis() - time) + " Position: " + joypos + " Rotation: " + rotation + " Talon Position Right: " + drivetrain.talonPositionRight() + " Talon Position Left: " + drivetrain.talonPositionLeft());

    }

    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }
}
