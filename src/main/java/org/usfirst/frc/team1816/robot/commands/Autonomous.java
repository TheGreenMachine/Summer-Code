package org.usfirst.frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Autonomous extends CommandGroup {

    public enum AutonomousMode {
        MAIN;
    }

    public Autonomous(AutonomousMode mode) {
        switch (mode) {
            case MAIN:
                addSequential(new DriveXInches(60, .5));
                addSequential(new RotateXDegrees(40));
                addSequential(new DriveXInches(30, .5));
        }
    }
}
