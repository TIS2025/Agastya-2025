package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.InstantAction;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.Globals;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
@Config
public class Shoulder {
    private static  RobotHardware robot;
    public static double power = 0;
    public static double directpower = 0.6;
    public static double kp = 0.001;//0.006//0.00095;
    public static double ki = 0.0;
    public static double kd = 0.00001;
    public static double angle;
    public int encoderCounts;
    public static double multiplier=1;
    public static double ff = 0;
    public static double ffmultiplier = 0.0015;

//    public boolean PID = false;
    public static PIDController controller;

    private double integral = 0.0;
    private double lastError = 0.0;
//    private double multiplier = 0.6;

    private static int targetPos;

    public enum ShoulderState {
        INC_VAL,
        INIT,
        DROP,
        PRE_INTAKE,
        INTAKE,
        SAFE,
        SPECI_PICK,
        SPECI_PRE_DROP,
        SPECI_DROP,
        AFTER_DROP,
        PRE_HANG,
        HANG
    }

    public static ShoulderState shoulderState = ShoulderState.INIT;

    public Shoulder(RobotHardware robot) {
        controller = new PIDController(kp,ki,kd);
        this.robot = robot;
        updateShoulderState(ShoulderState.INIT);
       // controller = new PIDController(kp, ki, kd);
    }

    public void updateShoulderState(ShoulderState state) {
        this.shoulderState = state;

        switch (state) {
            case INIT:
                extendTo(Globals.shoulderInit,1); //extend to init
                break;
             case DROP:
                extendTo(Globals.shoulderDrop,2);
                break;
            case PRE_INTAKE:
                extendTo(Globals.shoulderPreIntake,1); //extend to pick
                break;
            case INTAKE:
                extendTo(Globals.shoulderIntake,1);
                break;
            case SAFE:
                extendTo(Globals.shoulderSafe,1);
                break;
            case SPECI_PICK:
                extendTo(Globals.specimenPick,6);
                break;
            case SPECI_PRE_DROP:
                extendTo(Globals.specimenPreDrop,6);
                break;
            case SPECI_DROP:
                extendTo(Globals.highChamber,1);
                break;
            case AFTER_DROP:
                extendTo(Globals.afterDrop,1);
                break;
            case PRE_HANG:
                extendTo(Globals.shoulderPreHang,2);
                break;
            case HANG:
                extendTo(Globals.shoulderHang,1);
                break;

        }
    }





    //        robot.shoulder.setTargetPosition(targetPosition);
//        robot.shoulder.setTargetPositionTolerance(10);
//        robot.shoulder.setPower(directpower);
//        robot.shoulder.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    public static void extendToInit(int targetPosition) {
//        double powerInput = calculatePID(targetPosition);;
        if (targetPosition >= Globals.upMax) {
            targetPosition = Globals.upMax;
        }
//        if(targetPosition == Globals.shoulderInit){
//            Globals.factor = 0.35;
//        }
//        else{
//            Globals.factor = 1;
//        }
        robot.shoulder.setTargetPosition(targetPosition);
        robot.shoulder.setTargetPositionTolerance(10);
        robot.shoulder.setPower(directpower);
        robot.shoulder.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    public static void elevatorIncrement(int increment) {
        int newPosition = robot.shoulder.getCurrentPosition() + increment;
        if (newPosition <= Globals.upMax) {
            extendTo(newPosition,1);
        }
    }

    public static void elevatorDecrement(int decrement) {
        int newPosition = robot.shoulder.getCurrentPosition() - decrement;
        if (newPosition >= Globals.shoulderInit) {
            extendTo(newPosition,1);
        }
    }
    public static void extendTo(int targetPosition,double multiplier) {

        if (targetPosition >= Globals.upMax) {
            targetPosition = Globals.upMax;
        }
        targetPos=targetPosition;
        Shoulder.multiplier=multiplier;

    }

    public  void  calculatePID() {
            controller.setPID(multiplier*kp, ki, kd);
            power = Range.clip(controller.calculate(robot.shoulder.getCurrentPosition(), targetPos), -1, 1);
            robot.shoulder.setPower(power);

    }

    public void resetShoulder() {
        robot.shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public InstantAction ShoulderCommand(ShoulderState state) {
        return new InstantAction(() -> updateShoulderState(state));
    }


}

//    public static  calculatePID(int targetPosition) {
//
//        controller.setPID(kp, ki, kd);
//        power = Range.clip(controller.calculate(robot.shoulder.getCurrentPosition(), targetPosition), -1, 1);
//        robot.shoulder.setPower(power);
////        return power;
//    }

//public double mapCountsToCosine(int maxCounts) {
//        encoderCounts = robot.shoulder.getCurrentPosition();
//        // Map encoder counts to angle in degrees (-90 to 90)
//        angle = ((double) encoderCounts / maxCounts) * 180 - 90;
//
//        // Calculate the cosine of the angle (convert to radians for Math.cos)
//        return Math.cos(Math.toRadians(angle));
//    }
