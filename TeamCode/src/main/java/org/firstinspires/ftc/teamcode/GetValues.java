package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.hardware.Globals;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name="GetValues", group="TeleOp")
public class GetValues extends LinearOpMode {
    private RobotHardware robot;
    private IntakeSubsystem intake;
    private Elbow elbow;
    private Shoulder shoulder;
    private MecanumDrive drive;
    public double botHeading;

    public double multiplier=1;
    public double strafe = 0.7, speed = 0.7, turn = 0.7;
    public static boolean upFlag = false;
    public static boolean initFlag = false;
    public static boolean pickingFlag = false;

    public static boolean pickedFlag = false;

    public static List<Action> runningActions = new ArrayList<>();

    public static int lifterPos=0;
    @Override
    public void runOpMode() {

        robot = new RobotHardware();
        robot.init(hardwareMap, telemetry);
        elbow = new Elbow(robot);
        shoulder= new Shoulder(robot);
        intake = new IntakeSubsystem(robot);

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();

        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        while (opModeInInit()) {

            robot.resetEncoder();
            upFlag = false;
            initFlag = false;
            PIDFalse();

            pickingFlag = false;
            pickedFlag = false;

        }

        waitForStart();

        while (opModeIsActive()) {
            runningActions = updateAction();
            botHeading = drive.pose.heading.toDouble();


            previousGamepad1.copy(currentGamepad1);
            previousGamepad2.copy(currentGamepad2);

            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);

            // TODO =============================================== INIT===========================================================

            if(gamepad1.dpad_up){
                Shoulder.elevatorIncrement(30);
            }
            if(gamepad1.dpad_down){
                Shoulder.elevatorDecrement(30);
            }
            if(gamepad1.a){
                Elbow.elevatorIncrement(30);
            }
            if(gamepad1.b){
                Elbow.elevatorDecrement(30);
            }

            if(gamepad1.dpad_left){
                IntakeSubsystem.gripIncrement(Globals.incr);
            }
            if(gamepad1.dpad_right){
                IntakeSubsystem.gripDecrement(Globals.incr);
            }



            // TODO =============================================== SAMPLE PRE DROP ===========================================================



            // TODO =============================================== SAMPLE PICK ===========================================================


            // TODO ===============================================Field Oriented Drive  ===========================================================

//            if (gamepad1.right_stick_button) {
//                drive.lazyImu.get().resetYaw();
//                drive.navxMicro.initialize();
//                botHeading = drive.pose.heading.toDouble();
//            }
//
//            if (gamepad1.left_trigger > 0) {
//                multiplier = 0.78;
//                drive.driveFieldCentric(Math.pow(Range.clip(-gamepad1.left_stick_x*strafe*multiplier,-1,1),3),
//                        Math.pow(Range.clip(-gamepad1.left_stick_y*speed*multiplier,-1,1),3),
//                        Math.pow(Range.clip(gamepad1.right_stick_x*turn*multiplier,-1,1),3),
//                        botHeading);
//            } else {
//                multiplier=1;
//                drive.driveFieldCentric(Math.pow(Range.clip(-gamepad1.left_stick_x*strafe*multiplier,-1,1),3),
//                        Math.pow(Range.clip(-gamepad1.left_stick_y*speed*multiplier,-1,1),3),
//                        Math.pow(Range.clip(gamepad1.right_stick_x*turn*multiplier,-1,1),3),
//                        botHeading);
//            }
//            drive.updatePoseEstimate();

            // Todo ==================================== Robot Oriented ======================================================================
            drive.setDrivePowers(
                    new PoseVelocity2d(
                            new Vector2d(Math.pow(Range.clip(-gamepad1.left_stick_y*speed,-1,1),3),
                                    Math.pow(Range.clip(-gamepad1.left_stick_x*strafe,-1,1),3)),
                            Math.pow(Range.clip(-gamepad1.right_stick_x*turn,-1,1),3))
            );

            if (gamepad1.left_trigger>0.3){
                drive.setDrivePowers(
                        new PoseVelocity2d(
                                new Vector2d(Math.pow(Range.clip(gamepad1.left_stick_y*speed*multiplier,-1,1),3),
                                        Math.pow(Range.clip(gamepad1.left_stick_x*strafe*multiplier,-1,1),3)),
                                Math.pow(Range.clip(-gamepad1.right_stick_x*turn*multiplier,-1,1),3))

                );
            }


            // Todo ==================================== Elevator PID States ======================================================================


//            telemetry.addData("Left Front Current : ", drive.leftFront.getCurrent(CurrentUnit.AMPS));
//            telemetry.addData("Right Front Current : ", drive.rightFront.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("Right Back Current : ", drive.rightBack.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("Left Back Current : ", drive.leftBack.getCurrent(CurrentUnit.AMPS));
            telemetry.addLine("---------------------------");

            telemetry.addData(" Elbow POS : ", robot.elbow.getCurrentPosition());
            telemetry.addData(" Shoulder POS : ", robot.shoulder.getCurrentPosition());

            telemetry.addLine("---------------------------");

            telemetry.addData(" Elbow Error : ", Elbow.controller.getPositionError());
            telemetry.addData(" Shoulder Error : ",  Shoulder.controller.getPositionError());

            telemetry.addLine("---------------------------");

            telemetry.addData(" Specimen Gripper : ",  robot.specimenGripper.getPosition());


            telemetry.addData("x", drive.pose.position.x);
            telemetry.addData("y", drive.pose.position.y);
            telemetry.addData("heading (deg)", drive.lazyImu.get().getRobotYawPitchRollAngles().getYaw());
            telemetry.addData("Navx Heading", drive.navxMicro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);


            telemetry.update();








        }

    }

    public static List<Action> updateAction(){
        TelemetryPacket packet = new TelemetryPacket();
        List<Action> newActions = new ArrayList<>();
        List<Action> RemovableActions = new ArrayList<>();

        for (Action action : runningActions) {

            if (action.run(packet)) {
                newActions.add(action);
            }
        }
        return newActions;
    }

//    private static List<Action> updateAction() {
//        TelemetryPacket packet = new TelemetryPacket();
//        List<Action> newActions = new ArrayList<>();
//
//        for (Action action : ftc) {
//            if (action.run(packet)) {
//                newActions.add(action);
//            }
//        }
//        return newActions;
//    }



    public static void PIDFalse() {
        Globals.shoulderInitPID = 0;
        Globals.shoulderDropPID = 0;
        Globals.shoulderPreIntakePID = 0;
        Globals.shoulderIntakePID = 0;
        Globals.shoulderSafePID = 0;
        Globals.elbowInitPID = 0;
        Globals.elbowHighBucketPID = 0;
        Globals.elbowSamplePickPID = 0;
        Globals.elbowSamplePrePickPID = 0;
        Globals.elbowHighHangPID = 0;
    }



}
