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
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
@Deprecated
@Disabled
@Config
@Autonomous(name="Red Specimen Fast 2+2")
//@Deprecated
public class Red_Specimen_Fast extends LinearOpMode {
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
                //TODO Preload Specimen Drop

                .stopAndAdd(() -> new SpecDropSeq(intake, shoulder,elbow))
//                .splineToConstantHeading(new Vector2d(8,-34),Math.toRadians(90),baseVelConstraint80)
                .splineToLinearHeading(new Pose2d(8,-34,Math.toRadians(90)),Math.toRadians(90))

//                .strafeToLinearHeading(new Vector2d(8,-36), Math.toRadians(90),baseVelConstraint80)

                .waitSeconds(0.2)
                .stopAndAdd(() -> new SpecAfterDropSeq(intake, shoulder,elbow))

                //TODO Strafing Alliance Specific Sample to Obv Zone
                .splineToLinearHeading(new Pose2d(16,-46,Math.toRadians(180)),Math.toRadians(180),baseVelConstraint80)


                .strafeToLinearHeading(new Vector2d(8,-46), Math.toRadians(90),baseVelConstraint80)
                .stopAndAdd(() -> new SpecInitSeq(intake, shoulder,elbow))

                .strafeToLinearHeading(new Vector2d(26,-46), Math.toRadians(90),baseVelConstraint80)

                //todo slide 1nd sample
                .splineToLinearHeading((new Pose2d(48, -12, Math.toRadians(270))),Math.toRadians(0),baseVelConstraint80)
                .strafeToLinearHeading(new Vector2d(48,-42), Math.toRadians(270),baseVelConstraint80)

                //todo slide 2nd sample
                .splineToLinearHeading((new Pose2d(48, -20, Math.toRadians(270))),Math.toRadians(0))
                .splineToLinearHeading((new Pose2d(58, -12, Math.toRadians(270))),Math.toRadians(0))
                .stopAndAdd(() -> new SpecPickSeq(intake, shoulder,elbow))

////             //TODO Specimen 1 Pick and Drop
//                .splineToLinearHeading((new Pose2d(58, -59, Math.toRadians(270))),Math.toRadians(0))

                .strafeToLinearHeading(new Vector2d(58, -59), Math.toRadians(270))

                .stopAndAdd(() -> new SpecDropSeq(intake, shoulder,elbow))
//                .strafeToLinearHeading(new Vector2d(4,-35), Math.toRadians(90),baseVelConstraint80)
                .strafeToLinearHeading(new Vector2d(4,-30), Math.toRadians(90))
                //TODO Drop
                .waitSeconds(0.2)
                .stopAndAdd(() -> new SpecAfterDropSeq(intake, shoulder,elbow))
                .strafeToLinearHeading(new Vector2d(4,-35), Math.toRadians(90),baseVelConstraint80)

                //TODO Specimen 2 Pick and Drop
                .strafeToLinearHeading(new Vector2d(36, -40), Math.toRadians(270),baseVelConstraint80)
                .stopAndAdd(() -> new SpecPickSeq(intake, shoulder,elbow))
                .strafeToLinearHeading(new Vector2d(36, -59), Math.toRadians(270),baseVelConstraint)

                .stopAndAdd(() -> new SpecDropSeq(intake, shoulder,elbow))
//                .strafeToLinearHeading(new Vector2d(0,-35), Math.toRadians(90),baseVelConstraint80)
                .strafeToLinearHeading(new Vector2d(0,-28), Math.toRadians(90))
                //TODO Drop
                .waitSeconds(0.2)
                .stopAndAdd(() -> new SpecAfterDropSeq(intake, shoulder,elbow))

                .strafeToLinearHeading(new Vector2d(0,-40), Math.toRadians(90))
                .stopAndAdd(() -> new SpecInitSeq(intake, shoulder,elbow))

                //TODO Specimen 3 Pick and Drop
                .strafeToLinearHeading(new Vector2d(36, -40), Math.toRadians(270),baseVelConstraint80)
                .stopAndAdd(() -> new SpecPickSeq(intake, shoulder,elbow))
                .strafeToLinearHeading(new Vector2d(36, -59), Math.toRadians(270),baseVelConstraint)

                .stopAndAdd(() -> new SpecDropSeq(intake, shoulder,elbow))
//                .strafeToLinearHeading(new Vector2d(-4,-35), Math.toRadians(90),baseVelConstraint80)
                .strafeToLinearHeading(new Vector2d(-4,-28), Math.toRadians(90))
                //TODO Drop
                .waitSeconds(0.2)
                .stopAndAdd(() -> new SpecAfterDropSeq(intake, shoulder,elbow))

                .strafeToLinearHeading(new Vector2d(-4,-40), Math.toRadians(90))
                .stopAndAdd(() -> new SpecInitSeq(intake, shoulder,elbow))

                //TODO Parking
                .strafeToLinearHeading(new Vector2d(38, -58), Math.toRadians(270))
//
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
