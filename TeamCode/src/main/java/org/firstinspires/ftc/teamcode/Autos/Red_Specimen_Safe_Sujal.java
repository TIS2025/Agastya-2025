package org.firstinspires.ftc.teamcode.Autos;


import static java.lang.Math.toRadians;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.TwoDeadWheelLocalizer;
import org.firstinspires.ftc.teamcode.auto_sequence.SpecDropSeq;
import org.firstinspires.ftc.teamcode.auto_sequence.SpecInitInitialSeq;
import org.firstinspires.ftc.teamcode.auto_sequence.SpecPickSeq;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

import java.util.Arrays;

@Config
@Autonomous(name="Red Specimen Sujal 1+3")
@Disabled
@Deprecated
public class Red_Specimen_Safe_Sujal extends LinearOpMode {
    private RobotHardware robot = RobotHardware.getInstance();
    //Subsystems
    IntakeSubsystem intake ;
    Elbow elbow ;
    Shoulder shoulder ;

    //Drive
    private MecanumDrive drive = null;
//    static Vector2d samplePick1 = new Vector2d(27, -42);//26, -43)


    //TODO SPECIMEN PICK
    public static double pickOffset = 4;
    static Vector2d PreloadDrop = new Vector2d(-57, -55);
    static Vector2d samplePick1 = new Vector2d(-62, -52);
//    public static Vector2d specimenPick1 = new Vector2d(30.5, -66);
//    public static Vector2d specimenPick2 = new Vector2d(27 + 5, -66);
//    public static Vector2d specimenPick3 = new Vector2d(27 + 5, -66);

    //TODO TESTING PROFILE ACCELERATION
    VelConstraint baseVelConstraint = new MinVelConstraint(Arrays.asList(
            new TranslationalVelConstraint(30.0),
            new AngularVelConstraint(Math.PI / 4)
    ));


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, telemetry);
//        drive.updatePoseEstimate();
        intake = new IntakeSubsystem(robot);
        elbow = new Elbow(robot);
        shoulder = new Shoulder(robot);

        Pose2d startPose = new Pose2d(8, -64, Math.toRadians(90));
        drive = new MecanumDrive(hardwareMap, startPose);

//
//        Thread pidThread = new Thread(()-> {
//           while(opModeIsActive()){
//               if(pidFlag){
//                   //runPid function
//               }
//
//               //if error <3 pidFlag = false
//           }
//        });
        //TODO ===============================================TRAJECTORIES =============================================================


        Action trajectoryAction = drive.actionBuilder(startPose)
                //TODO Preload Specimen Drop

                .stopAndAdd(() -> new SpecDropSeq(intake, shoulder,elbow))
                .waitSeconds(0.5)
                .strafeToLinearHeading(new Vector2d(8,-40), Math.toRadians(90))
                .stopAndAdd(() -> new SpecPickSeq(intake, shoulder,elbow))
                .splineToConstantHeading(new Vector2d(8,-45), toRadians(90))
                .strafeToLinearHeading(new Vector2d(35,-45), toRadians(180))
                .strafeToLinearHeading(new Vector2d(40,-14), toRadians(-90))

                .strafeToLinearHeading(new Vector2d(50,-14), toRadians(-90))
                .strafeToLinearHeading(new Vector2d(50,-45), toRadians(-90))
                .splineToLinearHeading(new Pose2d(58,-14, Math.toRadians(-90)),Math.toRadians(-40))
                .strafeToLinearHeading(new Vector2d(57,-48), toRadians(-90))
                .splineToLinearHeading(new Pose2d(63,-15, Math.toRadians(-90)),Math.toRadians(-40))
                .strafeToLinearHeading(new Vector2d(64,-60), toRadians(-90))






//                .setReversed(true)
//                .strafeToLinearHeading(new Vector2d(24,-35), Math.toRadians(90))

//                .waitSeconds( nearHeading(new Vector2d(50,-60), Math.toRadians(90))
                .build();
        if (opModeInInit()) {
            telemetry.addLine("ROBOT INIT MODE");
            new SpecInitInitialSeq(intake, shoulder, elbow);
        }

        waitForStart();
        //pidThread.start();

        Actions.runBlocking(
                new SequentialAction(
                        trajectoryAction
                ));
        while (opModeIsActive()) {

//            shoulder.calculatePID();
//            elbow.calculatePID();

            telemetry.addData("x", drive.pose.position.x);
            telemetry.addData("y", drive.pose.position.y);
            telemetry.addData("heading (deg)", Math.toDegrees(drive.pose.heading.toDouble()));
            telemetry.addData("Navx heading (deg)", TwoDeadWheelLocalizer.robotHeading);
            telemetry.update();
        }


    }
}
