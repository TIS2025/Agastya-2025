package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.roadrunner.InstantAction;

import org.firstinspires.ftc.teamcode.hardware.Globals;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class IntakeSubsystem {

    public static  RobotHardware robot;  // RobotHardware Object
    public IntakeRollerState rollerState = IntakeRollerState.OFF;
    public IntakeGripperState gripperState = IntakeGripperState.INIT;

    public IntakeSubsystem(RobotHardware robot){
        this.robot = robot;
    }
    public enum IntakeRollerState {
        ON,
        OFF,
        RELEASE
    }

    public enum IntakeGripperState {
        INIT,
        OPEN,
        CLOSE,
    }

    public void updateGripperState(IntakeGripperState state) {
        this.gripperState = state;
        double currentGripperState= Globals.GRIP_INIT;  // default to off state
        switch (state) {
            case INIT:
                currentGripperState= Globals.GRIP_INIT;  // set to on state
                break;
            case OPEN:
                currentGripperState= Globals.GRIP_OPEN; // set to off state
                break;
            case CLOSE:
                currentGripperState= Globals.GRIP_CLOSE; // set to release state
                break;
        }
        setIntakeGripper(currentGripperState);
    }

    public void updateState(IntakeRollerState state) {
        this.rollerState=state;
        double currentRollerState= Globals.INTAKE_OFF;  // default to off state
        switch (state) {
            case ON:
                currentRollerState= Globals.INTAKE_ON;  // set to on state
                break;
            case OFF:
                currentRollerState= Globals.INTAKE_OFF; // set to off state
                break;
            case RELEASE:
                currentRollerState= Globals.INTAKE_RELEASE; // set to release state
                break;
        }
        setIntakeRoller(currentRollerState);
    }
    public InstantAction IntakeRollerCommands(IntakeRollerState state) {
        return new InstantAction(() -> updateState(state));
    }
    public InstantAction IntakeGripperCommands(IntakeGripperState state) {
        return new InstantAction(() -> updateGripperState(state));
    }
    public void setIntakeRoller(double power){
        robot.intakeRoller.setPower(power);
    }
    public void setIntakeGripper(double pos){
        robot.specimenGripper.setPosition(pos);
    }
    public static void gripIncrement(double increment){
        double pos = robot.specimenGripper.getPosition() + increment;
        robot.specimenGripper.setPosition(pos);
    }
    public static void gripDecrement(double increment){
        double pos = robot.specimenGripper.getPosition() - increment;
        robot.specimenGripper.setPosition(pos);
    }




}
