package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.InstantAction;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.Globals;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
@Config
public class Elbow {
    private static  RobotHardware robot;

    private MecanumDrive drive;
    public static double power = 0;
    public static double directpower = 0.6;
    public static double kp = 0.002;//0.006;
    public static double ki = 0.0;
    public static double kd = 0.0001;//0.0;

    public boolean PID = false;
    public static PIDController controller;
    private double integral = 0.0;
    private double lastError = 0.0;

    private static int targetPos;


    public enum ElbowState {
        INC_VAL,
        HOME,

        INIT,
        SAFE,
        HIGH_BUCKET,
        SAMPLE_PICK,
        SAMPLE_PRE_PICK,
        SPECI_PICK,
        SPECI_PRE_DROP,
        SPECI_DROP,
        HIGH_HANG,
        HIGH_HANG_DONE,
        AFTER_DROP,

        PRE_HANG,
        HANG
    }

    private ElbowState elbowState = ElbowState.INIT;

    public Elbow(RobotHardware robot) {
        controller = new PIDController(kp,ki,kd);
        this.robot = robot;
        updateElbowState(ElbowState.INIT);
//        controller = new PIDController(kp, ki, kd);
//        robot.elbow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void updateElbowState(ElbowState state) {
        this.elbowState = state;

        switch (state) {
            case INIT:
                extendTo(Globals.elbowInit);
                break;
            case SAFE:
                extendTo(Globals.elbowSafe);
                break;
            case HIGH_BUCKET:
                extendTo(Globals.elbowExtendHighBucket);
                break;
            case SAMPLE_PICK:
                extendTo(Globals.elbowExtendSamplePick);
                break;
            case SAMPLE_PRE_PICK:
                extendTo(Globals.elbowExtendSamplePrePick);
                break;
            case SPECI_PICK:
                extendTo(Globals.elbowSpeciPick);
                break;
            case SPECI_PRE_DROP:
                extendTo(Globals.elbowSpeciPreDrop);
                break;
            case SPECI_DROP:
                extendTo(Globals.elbowSpeciDrop);
                break;
            case AFTER_DROP:
                extendTo(Globals.elbowAfterDrop);
                break;
            case PRE_HANG:
                extendTo(Globals.elbowPreHang);
                break;
            case HANG:
                extendTo(Globals.elbowHang);
                break;
        }

    }

    public static void extendTo(int targetPosition) {
        //double powerInput = calculatePID(targetPosition);
        if (targetPosition <= Globals.elbowMax) {
            targetPosition = Globals.elbowMax;
        }

        targetPos=targetPosition;

//        if(targetPosition == Globals.elbowInit){
//            Globals.factor = 0.35;
//        }
//        else{
//            Globals.factor = 1;
//        }
//
//        robot.elbow.setTargetPosition(targetPosition);
//        robot.elbow.setTargetPositionTolerance(10);
//        robot.elbow.setPower(directpower);
//        robot.elbow.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    public static void extendToInit(int targetPosition) {
        //double powerInput = calculatePID(targetPosition);
        if (targetPosition <= Globals.elbowMax) {
            targetPosition = Globals.elbowMax;
        }
//        if(targetPosition == Globals.elbowInit){
//            Globals.factor = 0.35;
//        }
//        else{
//            Globals.factor = 1;
//        }
//        robot.elbow.setTargetPosition(targetPosition);
//        robot.elbow.setTargetPositionTolerance(10);
//        robot.elbow.setPower(directpower);
//        robot.elbow.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    public static void elevatorIncrement(int increment) {
        int newPosition = robot.elbow.getCurrentPosition() + increment;
        if (newPosition <= Globals.elbowMax) {
            extendTo(newPosition);
        }
    }

    public static void elevatorDecrement(int decrement) {
        int newPosition = robot.elbow.getCurrentPosition() - decrement;
        if (newPosition >= Globals.elbowInit) {
            extendTo(newPosition);
        }
    }

    public void calculatePID() {
//        if (PID) {
            controller.setPID(kp, ki, kd);
            power = Range.clip(controller.calculate(robot.elbow.getCurrentPosition(), targetPos), -1, 1);
            robot.elbow.setPower(power);
        }
//        else {
//            robot.elbow.setTargetPosition(targetPos);
//            robot.elbow.setTargetPositionTolerance(10);
//            robot.elbow.setPower(directpower);
//            robot.elbow.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
//        }
    //}


    public void resetElbow() {
        robot.elbow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public InstantAction ElbowCommand(ElbowState state) {
        return new InstantAction(() -> updateElbowState(state));
    }
}
