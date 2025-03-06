package org.firstinspires.ftc.teamcode.Autos;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.AccelConstraint;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.TwoDeadWheelLocalizer;
import org.firstinspires.ftc.teamcode.auto_sequence.SpecAfterDropSeq;
import org.firstinspires.ftc.teamcode.auto_sequence.SpecDropSeq;
import org.firstinspires.ftc.teamcode.auto_sequence.SpecInitSeq;
import org.firstinspires.ftc.teamcode.auto_sequence.SpecPickSeq;
import org.firstinspires.ftc.teamcode.hardware.Globals;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

import java.util.Arrays;

@Config
@Autonomous(name="Specimen Spline 2+2 🔴 Path Test")
//@Deprecated
public class Red_Specimen_Spline extends LinearOpMode {
    private RobotHardware robot = RobotHardware.getInstance();
    //Subsystems
    IntakeSubsystem intake ;
    Elbow elbow ;
    Shoulder shoulder ;

    private  Thread pidThread;

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


    VelConstraint baseVelConstraint80 = new MinVelConstraint(Arrays.asList(
            new TranslationalVelConstraint(80.0),
            new AngularVelConstraint(Math.PI*1.5)
    ));

    AccelConstraint baseAccelConstraint60 = new ProfileAccelConstraint(-30.0,60.0);


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, telemetry);
//        drive.updatePoseEstimate();
        intake = new IntakeSubsystem(robot);
        elbow = new Elbow(robot);
        shoulder = new Shoulder(robot);
        Globals.Is_Thread=true;

        Pose2d startPose = new Pose2d(18, -64, Math.toRadians(90));
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


        Action trajectoryAction = drive.actionBuilder(new Pose2d(18, -64, Math.toRadians(90)))
                .stopAndAdd(() -> new SpecDropSeq(intake, shoulder,elbow))

                .splineToLinearHeading(new Pose2d(8,-34,Math.toRadians(90)),Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(8,-36),Math.toRadians(90),baseVelConstraint80)
//                .strafeToLinearHeading(new Vector2d(8,-36), Math.toRadians(90),baseVelConstraint80)
                .waitSeconds(0.2)
                .stopAndAdd(() -> new SpecAfterDropSeq(intake,elbow))
                .afterTime(0.5,()->new SpecInitSeq(intake, shoulder,elbow))


                //TODO Strafing Alliance Specific Sample to Obv Zone
                .setReversed(true)
//                .splineToLinearHeading(new Pose2d(24,-40,Math.toRadians(180)),Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(38,-22,Math.toRadians(270)),Math.toRadians(90))
//                .stopAndAdd(() -> new SpecInitSeq(intake, shoulder,elbow))
                .splineToLinearHeading(new Pose2d(48,-14,Math.toRadians(270)),Math.toRadians(270))

//                 .splineToConstantHeading(new Vector2d(48,-42),Math.toRadians(270))
                //TODO Slide Sample 1 into  OBV Zone
                .splineToLinearHeading((new Pose2d(48,-56,Math.toRadians(270))),Math.toRadians(270))
                .splineToLinearHeading((new Pose2d(48,-22,Math.toRadians(270))),Math.toRadians(90))
//
//                //TODO Slide Sample 2 into  OBV Zone
                .splineToLinearHeading(new Pose2d(58,-12,Math.toRadians(270)),Math.toRadians(270))

                //TODO Sample 1 Pick
                .stopAndAdd(() -> new SpecPickSeq(intake, shoulder,elbow))
                .splineToLinearHeading(new Pose2d(58,-61, Math.toRadians(270)),Math.toRadians(270))
//                .waitSeconds(0.2)
                .stopAndAdd(() -> new SpecDropSeq(intake, shoulder,elbow))

                //TODO Sample 1 Score
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(10,-33.50,Math.toRadians(90)),Math.toRadians(90))
                .stopAndAdd(() -> new SpecAfterDropSeq(intake,elbow))

                //TODO Sample 2 Pick
                .afterTime(0.5,()->new SpecPickSeq(intake, shoulder,elbow))
//                .stopAndAdd(()->new SpecPickSeq(intake, shoulder,elbow))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(38,-58,Math.toRadians(270)),Math.toRadians(270))

                .splineToLinearHeading(new Pose2d(38,-61,Math.toRadians(270)),Math.toRadians(270))
//                .waitSeconds(0.2)
                .stopAndAdd(() -> new SpecDropSeq(intake, shoulder,elbow))

                //TODO Sample 2 Score
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(4,-33.5,Math.toRadians(90)),Math.toRadians(90))
                .stopAndAdd(() -> new SpecAfterDropSeq(intake,elbow))

                //TODO Sample 3 Pick
                .afterTime(0.5,()->new SpecPickSeq(intake, shoulder,elbow))
//                .stopAndAdd(()->new SpecPickSeq(intake, shoulder,elbow))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(38,-58,Math.toRadians(270)),Math.toRadians(270))

                .splineToLinearHeading(new Pose2d(38,-61,Math.toRadians(270)),Math.toRadians(270))
//                .waitSeconds(0.2)
                .stopAndAdd(() -> new SpecDropSeq(intake, shoulder,elbow))

                //TODO Sample 3 Score
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(0,-33.5,Math.toRadians(90)),Math.toRadians(90))
                .stopAndAdd(() -> new SpecAfterDropSeq(intake,elbow))
                .afterTime(1,()->new SpecInitSeq(intake, shoulder,elbow))

//                .splineToLinearHeading(new Pose2d(38,-61,Math.toRadians(135)),Math.toRadians(270))

                .strafeToLinearHeading(new Vector2d(36, -60), Math.toRadians(90))

                .build();

        pidThread = new Thread(()-> {
            while(!pidThread.isInterrupted() && !isStopRequested()) {
                try {
                    elbow.calculatePID();
                    shoulder.calculatePID();
                    Thread.sleep(10);
                } catch (Exception e) {
                    telemetry.addData("Exception", e);
                    telemetry.update();
                    pidThread.interrupt();
                }
            }

        }
        );


        if (opModeInInit()) {
            telemetry.addLine("ROBOT INIT MODE");
            new SpecInitSeq(intake, shoulder, elbow);
        }

        waitForStart();
        pidThread.start();
        if(opModeIsActive()) {
            Actions.runBlocking(
                    new SequentialAction(
                            trajectoryAction
                    ));
            Globals.Is_Thread=false;
        }
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
