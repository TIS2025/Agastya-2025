package org.firstinspires.ftc.teamcode.Teleop;

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
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Sequence.HangSeq;
import org.firstinspires.ftc.teamcode.Sequence.InitSeq;
import org.firstinspires.ftc.teamcode.Sequence.SampleDropSeq;
import org.firstinspires.ftc.teamcode.Sequence.SamplePickSeq;
import org.firstinspires.ftc.teamcode.Sequence.SpecimenDropSeq;
import org.firstinspires.ftc.teamcode.Sequence.SpecimenPickSeq;
import org.firstinspires.ftc.teamcode.hardware.Globals;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name="AG")
public class Teleop extends LinearOpMode {
    private final RobotHardware robot = RobotHardware.getInstance();
    private IntakeSubsystem intake;
    private Elbow elbow;
    private Shoulder shoulder;
    private MecanumDrive drive;
    public double botHeading;
    public boolean count = true;

   public double angle;
    public int encoderCounts;
    public double multiplier=0.6;
    public double strafe = 1, speed = 0.9, turn = 0.9;
    public double ThresholdDistance = 7;
    public static boolean upFlag = false;
    public static boolean initFlag = false;
    public static boolean pickingFlag = false;
    public static boolean slowFlag = false;

    public static boolean pickedFlag = false;
    public static boolean isPicking = false;
    public static boolean isPicked = false;

    public static List<Action> runningActions = new ArrayList<>();

    public static int lifterPos=0;
    @Override
    public void runOpMode() {

        robot.init(hardwareMap, telemetry);
        elbow = new Elbow(robot);
        shoulder = new Shoulder(robot);
        intake = new IntakeSubsystem(robot);

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();

        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));


        drive.setDrivePowers(
                new PoseVelocity2d(
                        new Vector2d(Math.pow(Range.clip(-gamepad1.left_stick_y * speed , -1, 1), 3),
                                     Math.pow(Range.clip(-gamepad1.left_stick_x * strafe , -1, 1), 3)),
                                     Math.pow(Range.clip(-gamepad1.right_stick_x * turn, -1, 1), 3))
        );

        telemetry.addData("Status", "Initialized");
        telemetry.update();
//        Thread pid = new Thread(()->{
//            while(opModeIsActive()){
//                if(pidFlag){
//
//                }
//
//                if(//errror <3){
//            }
//        });

        while (opModeInInit()) {
          shoulder.extendTo(0,1);
          elbow.extendTo(0);
        }
        robot.resetEncoder();
        waitForStart();

        while (opModeIsActive()) {
            shoulder.calculatePID();
            elbow.calculatePID();

            runningActions = updateAction();
            botHeading = drive.pose.heading.toDouble();


            previousGamepad1.copy(currentGamepad1);
            previousGamepad2.copy(currentGamepad2);

            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);

            // TODO =============================================== INIT===========================================================


            // TODO =============================================== SAMPLE PRE DROP ===========================================================
            if (gamepad1.a) {
                runningActions.add(SamplePickSeq.PreSamplePickAction(intake, elbow, shoulder));
            }

            /////////////////////////////////////////////////////////
////            else
////            {
////                slowFlag = false;
////            }
//
//            if(slowFlag) {
//
//
//                //if (Elbow.controller.getPositionError()<5 && Shoulder.controller.getPositionError()<5 ){
////                    counter++;
////                    if (counter==50) {
//                slowFlag = false;
////                        counter=0;
////                    }
////            }
//            }
            /////////////////////////////////////////////////////////////////

            // TODO =============================================== SAMPLE PICK ===========================================================
            if (gamepad1.x) {

                isPicking = true;
//                slowFlag = true;
                runningActions.add(SamplePickSeq.PickAction(intake, elbow, shoulder));

            }
            if (count){
                mapCountsToCosine(1400);

            }
            if (isPicking) {
                if (robot.colorSensor.getDistance(DistanceUnit.MM) < ThresholdDistance) {
                    runningActions.add(SamplePickSeq.PreSamplePickAction(intake, elbow, shoulder));
                    isPicking = false;
                    isPicked= true;
                    slowFlag = false;
                }
            }
            if (gamepad1.left_bumper) {
//                upFlag = true;
                runningActions.add(SampleDropSeq.PreSampleDropHighAction(intake,elbow,shoulder));
            }
            if (gamepad1.b) {
                runningActions.add(SampleDropSeq.DropAction(intake, elbow, shoulder));
            }
            if (gamepad1.y) {
                runningActions.add(SampleDropSeq.DropActionSample(intake, elbow, shoulder));
            }

            // TODO =============================================== INIT===========================================================

            if (gamepad1.right_bumper) {
//                upFlag = false;
                runningActions.add(InitSeq.InitAction(intake, elbow, shoulder));
            }
            // TODO =============================================== SPECIMEN===========================================================
            if (gamepad1.dpad_down) {
                runningActions.add(SpecimenPickSeq.PreSpecimenPickAction(intake, elbow, shoulder));
            }
            if (gamepad1.dpad_up) {
                runningActions.add(SpecimenDropSeq.DropAction(intake, elbow, shoulder));
            }
            if (gamepad1.dpad_right) {
                runningActions.add(SpecimenDropSeq.AfterDropAction(intake, elbow, shoulder));
            }

            // TODO =============================================== HANG===========================================================
            if (gamepad1.dpad_left) {
                runningActions.add(HangSeq.PreHangAction(intake, elbow, shoulder));
            }
            if (gamepad1.right_trigger>0) {
                runningActions.add(HangSeq.HangAction(intake, elbow, shoulder));
            }

            if (currentGamepad1.back && !previousGamepad1.back) {
                Globals.PID = !Globals.PID; // Toggle the value of PID
            }







//            if (upFlag) {
//                runningActions.add(SampleDropSeq.PreSampleDropHighAction(intake, elbow, shoulder));
//            }




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
                            new Vector2d(Math.pow(Range.clip(-gamepad1.left_stick_y * speed, -1, 1), 3),
                                    Math.pow(Range.clip(-gamepad1.left_stick_x * strafe, -1, 1), 3)),
                            Math.pow(Range.clip(-gamepad1.right_stick_x * turn, -1, 1), 3))
            );

            if (gamepad1.left_trigger > 0.3 || slowFlag) {
                drive.setDrivePowers(
                        new PoseVelocity2d(
                                new Vector2d(Math.pow(Range.clip(-gamepad1.left_stick_y * speed, -1, 1), 3),
                                        Math.pow(Range.clip(-gamepad1.left_stick_x * strafe * multiplier, -1, 1), 3)),
                                Math.pow(Range.clip(-gamepad1.right_stick_x * turn * multiplier, -1, 1), 3))

                );
            }


            // Todo ==================================== Elevator PID States ======================================================================


//            telemetry.addData("Left Front Current : ", drive.leftFront.getCurrent(CurrentUnit.AMPS));
//            telemetry.addData("Counter",counter);
//            telemetry.addData("COS",Math.cos(Math.toRadians(angle)));
//            telemetry.addData("angle",angle);
//            telemetry.addData("FeedForward",Shoulder.ff);


//            telemetry.addData("Power",Shoulder.power+Shoulder.ff);
            telemetry.addData("Kp", Shoulder.multiplier*Shoulder.kp);
            telemetry.addData("PID", Globals.PID);
            telemetry.addData("Shoulder Current : ", robot.shoulder.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("Elbow Current : ", robot.elbow.getCurrent(CurrentUnit.AMPS));

            telemetry.addData("Right Back Current : ", drive.rightBack.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("Left Back Current : ", drive.leftBack.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("Right Front Current : ", drive.rightFront.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("Left Front Current : ", drive.leftFront.getCurrent(CurrentUnit.AMPS));
            telemetry.addLine("---------------------------");

            telemetry.addData(" Elbow POS : ", robot.elbow.getCurrentPosition());
            telemetry.addData(" Shoulder POS : ", robot.shoulder.getCurrentPosition());
            telemetry.addData(" factor : ",Globals.factor);
//            telemetry.addData(" state : ",Shoulder.shoulderState);

            telemetry.addLine("---------------------------");

            telemetry.addData(" Elbow Error : ", elbow.controller.getPositionError());
            telemetry.addData(" Shoulder Error : ", shoulder.controller.getPositionError());

            telemetry.addLine("---------------------------");

            telemetry.addData("Threshold",robot.colorSensor.getDistance(DistanceUnit.MM));
            telemetry.addData("isPicking",isPicking);
            telemetry.addLine("---------------------------");
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

    // Function to map encoder counts to cosine values
    public double mapCountsToCosine(int maxCounts) {
        encoderCounts = robot.shoulder.getCurrentPosition();
        // Map encoder counts to angle in degrees (-90 to 90)
        angle = ((double) encoderCounts / maxCounts) * 180 - 90;

        // Calculate the cosine of the angle (convert to radians for Math.cos)
        return Math.cos(Math.toRadians(angle));
    }


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
