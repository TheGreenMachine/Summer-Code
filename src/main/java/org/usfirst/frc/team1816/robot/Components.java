package org.usfirst.frc.team1816.robot;

import org.usfirst.frc.team1816.robot.subsystems.SpeedControlDrivetrain;

public class Components {
    private static Components instance;

    public SpeedControlDrivetrain drivetrain;

    private static final int RIGHT_MAIN = 1;
    private static final int RIGHT_SLAVE_ONE = 2;
    private static final int RIGHT_SLAVE_TWO = 3;
    private static final int LEFT_MAIN = 4;
    private static final int LEFT_SLAVE_ONE = 5;
    private static final int LEFT_SLAVE_TWO = 6;

    public Components() {
        drivetrain = new SpeedControlDrivetrain(RIGHT_MAIN, RIGHT_SLAVE_ONE, RIGHT_SLAVE_TWO, LEFT_MAIN, LEFT_SLAVE_ONE, LEFT_SLAVE_TWO);
    }

    public static Components getInstance() {
        if (instance == null) {
            instance = new Components();
        }
        return instance;
    }
}
