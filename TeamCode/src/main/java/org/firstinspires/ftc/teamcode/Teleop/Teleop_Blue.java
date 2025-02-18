package org.firstinspires.ftc.teamcode.Teleop;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
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

@TeleOp(name="AG Teleop Blue 🔵")
public class Teleop_Blue extends LinearOpMode {
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
    public double ThresholdDistance = 6.5;
    public static boolean upFlag = false;
    public static boolean initFlag = false;
    public static boolean pickingFlag = false;
    public static boolean slowFlag = false;

    public static boolean pickedFlag = false;
    public static boolean isPicking = false;
    public static boolean isPicked = false;

    public static List<Action> runningActions = new ArrayList<>();

    private boolean detectionFlag = false;
    private boolean colorFlag = false;
    private boolean gripFlag = false;

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

        NormalizedRGBA rgba;
        float[] hsv;
        double distance;
        robot.colorSensor.setGain((float) Globals.Gain);

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
//        });.


        // TODO =============================================== INIT===========================================================
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

            // TODO =============================================== SAMPLE PRE PICK ===========================================================
            if (gamepad1.a) {
                runningActions.add(SamplePickSeq.PreSamplePickAction(intake, elbow, shoulder));
            }
            //TODO Color detection --> Do Not Change
            rgba = robot.colorSensor.getNormalizedColors();
            distance = robot.colorSensor.getDistance(DistanceUnit.MM);
            hsv = rgbToHsv(rgba.red, rgba.green, rgba.blue);

            // TODO HSV RED
//            if (((hsv[0] < 26) && (hsv[0] > 18) && distance < 15)) {

                if (((hsv[0] < 59) && (hsv[0] > 18) && distance < 15)) {
                Globals.intakeItem = 1;
            }

            //TODO HSV YELLOW
//          else if (((hsv[0] < 80) && (hsv[0] > 55) && (hsv[2] > 0.95)  && distance < 15)) {

            else if (((hsv[0] < 85) && (hsv[0] > 59.8) && (hsv[2] > 0.95)  && distance < 15)) {
                Globals.intakeItem = 2;
            }

            // TODO HSV BLUE
//          else if (((hsv[0] < 235) && (hsv[0] > 210) && distance < 15)) {

            else if (((hsv[0] < 225) && (hsv[0] > 185) && distance < 15)) {
                Globals.intakeItem = 3;
            }
            else {
                Globals.intakeItem = 0;
            }

            //TODO: Color Detection Logic
            if(detectionFlag ) {
                if (Globals.intakeItem == 3 || Globals.intakeItem == 2) {
                    Actions.runBlocking(
                            new SequentialAction(
                                    intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),
                                    shoulder.ShoulderCommand(Shoulder.ShoulderState.PRE_INTAKE),//300
                                    elbow.ElbowCommand(Elbow.ElbowState.SAMPLE_PRE_PICK)
                            )
                    );
                    detectionFlag = false;
                    colorFlag = true;
                }
                else if (Globals.intakeItem ==1){
                    Actions.runBlocking(
                            new SequentialAction(
//                                    new ParallelAction(
//                                            shoulder.ShoulderCommand(Shoulder.ShoulderState.PRE_INTAKE),
//                                            elbow.ElbowCommand(Elbow.ElbowState.SAMPLE_PRE_PICK)
//                                    ),
                                    intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.RELEASE),
                                    new SleepAction(0.5),
//                                    new ParallelAction(
//                                            shoulder.ShoulderCommand(Shoulder.ShoulderState.INTAKE),//300
//                                            elbow.ElbowCommand(Elbow.ElbowState.SAMPLE_PICK)//1550
//                                    )
                                    intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.ON)
                                    )
                    );

                }
//                detectionFlag = false;
            }

            if (isPicking) {
                if (robot.colorSensor.getDistance(DistanceUnit.MM) < ThresholdDistance) {
                    runningActions.add(SamplePickSeq.PreSamplePickAction(intake, elbow, shoulder));
                    isPicking = false;
//                    isPicked= true;
//                    slowFlag = false;
                }
            }

            // TODO =============================================== SAMPLE PICK ===========================================================
            if (gamepad1.x) {
                detectionFlag = true;
//                isPicking = true;
//                slowFlag = true;
                runningActions.add(SamplePickSeq.PickAction(intake, elbow, shoulder));
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
                Globals.SpecimenMode = !Globals.SpecimenMode; // Toggle the value of Specimen
            }

            // TODO =============================================== OPERATOR===========================================================

            if (gamepad2.right_trigger > 0.5) {
                Shoulder.elevatorIncrement(30);
            }
            if (gamepad2.left_trigger > 0.5) {
                Shoulder.elevatorDecrement(30);
            }

            // TODO ===============================================Field Oriented Drive  ===========================================================

            if (gamepad1.right_stick_button) {
                drive.lazyImu.get().resetYaw();
                drive.navxMicro.initialize();
                botHeading = drive.pose.heading.toDouble();
            }
            if (gamepad1.left_trigger > 0 && Globals.SpecimenMode) {
                multiplier = 0.78;
                drive.driveFieldCentric(Math.pow(Range.clip(-gamepad1.left_stick_x*strafe*multiplier,-1,1),3),
                        Math.pow(Range.clip(-gamepad1.left_stick_y*speed*multiplier,-1,1),3),
                        Math.pow(Range.clip(gamepad1.right_stick_x*turn*multiplier,-1,1),3),
                        botHeading);
            }
            if (Globals.SpecimenMode) {
                multiplier=1;
                drive.driveFieldCentric(Math.pow(Range.clip(-gamepad1.left_stick_x*strafe*multiplier,-1,1),3),
                        Math.pow(Range.clip(-gamepad1.left_stick_y*speed*multiplier,-1,1),3),
                        Math.pow(Range.clip(gamepad1.right_stick_x*turn*multiplier,-1,1),3),
                        botHeading);
            }
            drive.updatePoseEstimate();

            // Todo ==================================== Robot Oriented ======================================================================

            if(!Globals.SpecimenMode) {
                drive.setDrivePowers(
                        new PoseVelocity2d(
                                new Vector2d(Math.pow(Range.clip(-gamepad1.left_stick_y * speed, -1, 1), 3),
                                        Math.pow(Range.clip(-gamepad1.left_stick_x * strafe, -1, 1), 3)),
                                Math.pow(Range.clip(-gamepad1.right_stick_x * turn, -1, 1), 3))
                );
            }

            if (gamepad1.left_trigger > 0.3 || slowFlag && !Globals.SpecimenMode ) {
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
            telemetry.addData("ItemState", Globals.intakeItem);
            telemetry.addData("DetectionFlag", detectionFlag);
            telemetry.addData("ColorFlag", colorFlag);

            telemetry.addData("RGB",rgba);
            telemetry.addData("Distance",distance);

            telemetry.addLine()
                    .addData("HUE",hsv[0])
                    .addData("Saturation", hsv[1])
                    .addData("Value", hsv[2]);


            telemetry.addData("Kp", Shoulder.multiplier*Shoulder.kp);
            telemetry.addData("PID", Globals.SpecimenMode);
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
            telemetry.addData("isPicking",isPicking);
            telemetry.addLine("---------------------------");
            telemetry.addData("x", drive.pose.position.x);
            telemetry.addData("y", drive.pose.position.y);
            telemetry.addData("heading (deg)", drive.lazyImu.get().getRobotYawPitchRollAngles().getYaw());
            telemetry.addData("Navx Heading", drive.navxMicro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);


            telemetry.update();

        }
    }


    public float[] rgbToHsv(float rNorm, float gNorm, float bNorm) {
        float[] hsv = new float[3];

        float max = Math.max(rNorm, Math.max(gNorm, bNorm));
        float min = Math.min(rNorm, Math.min(gNorm, bNorm));
        float delta = max - min;
        // Value
        hsv[2] = max;

        // Saturation
        hsv[1] = max == 0 ? 0 : delta / max;

        // Hue
        if (delta == 0) {
            hsv[0] = 0;
        } else {
            if (max == rNorm) {
                hsv[0] = (60 * ((gNorm - bNorm) / delta) + 360) % 360;
            } else if (max == gNorm) {
                hsv[0] = (60 * ((bNorm - rNorm) / delta) + 120) % 360;
            } else if (max == bNorm) {
                hsv[0] = (60 * ((rNorm - gNorm) / delta) + 240) % 360;
            }
        }

        return hsv;
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
