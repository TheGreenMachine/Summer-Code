package org.usfirst.frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Autonomous extends CommandGroup {

    public enum AutonomousMode {
        MAIN;
    }

    public Autonomous(AutonomousMode mode) {
        switch (mode) {
            case MAIN:
                addSequential(new DriveXInches(12, .5));
                addSequential(new RotateXDegrees(-30));
                addSequential(new DriveXInches(36, .5));
                addSequential(new DriveUntilDistanceCommand(.8,.5));
        }
    }
}
