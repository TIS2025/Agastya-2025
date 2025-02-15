package org.firstinspires.ftc.teamcode.Autos;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Sequence.InitSeq;
import org.firstinspires.ftc.teamcode.TwoDeadWheelLocalizer;
import org.firstinspires.ftc.teamcode.auto_sequence.ParkSeq;
import org.firstinspires.ftc.teamcode.auto_sequence.PreSampleDropSeq;
import org.firstinspires.ftc.teamcode.auto_sequence.SampleDropSeq;
import org.firstinspires.ftc.teamcode.auto_sequence.SamplePickSeq;
import org.firstinspires.ftc.teamcode.auto_sequence.SpecInitSeq;
import org.firstinspires.ftc.teamcode.auto_sequence.SpecPickSeq;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

import java.util.Arrays;

@Config
@Autonomous(name="Red Sample 1+3 🔴")
//@Deprecated
public class Red_Sample_Safe extends LinearOpMode {
    private RobotHardware robot = RobotHardware.getInstance();
    //Subsystems
    IntakeSubsystem intake ;
    Elbow elbow ;
    Shoulder shoulder ;

    //Drive
    private MecanumDrive drive = null;

    //TODO SPECIMEN PICK
    public static double pickOffset = 4;

    //TODO TESTING PROFILE ACCELERATION
    VelConstraint baseVelConstraint = new MinVelConstraint(Arrays.asList(
            new TranslationalVelConstraint(30.0),
            new AngularVelConstraint(Math.PI / 2)
    ));

    VelConstraint baseVelConstraint50 = new MinVelConstraint(Arrays.asList(
            new TranslationalVelConstraint(50.0),
            new AngularVelConstraint(Math.PI / 2)
    ));


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, telemetry);

        intake = new IntakeSubsystem(robot);
        elbow = new Elbow(robot);
        shoulder = new Shoulder(robot);
        robot.shoulder.setDirection(DcMotorSimple.Direction.FORWARD);
        robot.elbow.setDirection(DcMotorSimple.Direction.FORWARD);

        Pose2d startPose = new Pose2d(-32, -64, Math.toRadians(0));
        drive = new MecanumDrive(hardwareMap, startPose);

        //TODO ===============================================TRAJECTORIES =============================================================


        TrajectoryActionBuilder trajectoryAction = drive.actionBuilder(startPose)
                //TODO Preload Sample Drop

                .stopAndAdd(() -> new PreSampleDropSeq(intake, shoulder,elbow))
                .strafeToLinearHeading(new Vector2d(-56,-56), Math.toRadians(45))
                .stopAndAdd(() -> new SampleDropSeq(intake, shoulder, elbow))
                .waitSeconds(0.8)

                //TODO Sample 1 Pick
                                    //.stopAndAdd(() ->new PreSamplePickSeq(intake, shoulder, elbow))
                .stopAndAdd(() ->new SamplePickSeq(intake , elbow, shoulder))//RECALL
                .strafeToLinearHeading(new Vector2d(-18,-34), Math.toRadians(165))
                                    //.stopAndAdd(() ->new PreSamplePickSeq(intake, shoulder, elbow))//RECALL
//                .waitSeconds(0.4)
                                    //.stopAndAdd(() ->new SamplePickSeq(intake, elbow, shoulder))
                .stopAndAdd(() ->new SamplePickSeq(intake, elbow, shoulder))//RECALL

                .strafeToLinearHeading(new Vector2d(-24,-34), Math.toRadians(163),baseVelConstraint)
                .waitSeconds(0.2)  //0.3 seconds
                //TODO Sample 1 Drop
                .stopAndAdd(() -> new PreSampleDropSeq(intake, shoulder,elbow))
                .strafeToLinearHeading(new Vector2d(-48,-45), Math.toRadians(45),baseVelConstraint50)

                .strafeToLinearHeading(new Vector2d(-55,-54), Math.toRadians(45),baseVelConstraint50)
                .stopAndAdd(() -> new SampleDropSeq(intake, shoulder, elbow))
                .waitSeconds(0.6)

                //TODO Sample 2 Pick
                                    //.stopAndAdd(() ->new PreSamplePickSeq(intake, shoulder, elbow))
                .stopAndAdd(() ->new SamplePickSeq(intake, elbow, shoulder))//RECALL
                .strafeToLinearHeading(new Vector2d(-29,-35), Math.toRadians(162))
                                    //.stopAndAdd(() ->new PreSamplePickSeq(intake, shoulder, elbow))//RECALL
//                .waitSeconds(0.5)
                .stopAndAdd(() ->new SamplePickSeq(intake, elbow, shoulder))//RECALL
                .strafeToLinearHeading(new Vector2d(-34,-34), Math.toRadians(165),baseVelConstraint)
                .waitSeconds(0.2)  //0.3 seconds
                //TODO Sample 2 Drop
                .stopAndAdd(() -> new PreSampleDropSeq(intake, shoulder,elbow))

                //TODO ALIGN BASKET BEFORE DROPING AND THEN TAKING BOT TOWARDS BASKET   //
                .strafeToLinearHeading(new Vector2d(-48,-45), Math.toRadians(45),baseVelConstraint50) //

                .strafeToLinearHeading(new Vector2d(-55,-54), Math.toRadians(45),baseVelConstraint50)
                .stopAndAdd(() -> new SampleDropSeq(intake, shoulder, elbow))
                .waitSeconds(0.6)

                //TODO Sample 3 Pick
                                     //.stopAndAdd(() ->new PreSamplePickSeq(intake, shoulder, elbow))
                .stopAndAdd(() ->new SamplePickSeq(intake, elbow, shoulder))//RECALL
                .strafeToLinearHeading(new Vector2d(-38,-35), Math.toRadians(162))
                                      //.stopAndAdd(() ->new PreSamplePickSeq(intake, shoulder, elbow))//RECALL
//                .waitSeconds(0.5)
                .stopAndAdd(() ->new SamplePickSeq(intake, elbow, shoulder))//RECALL
                .strafeToLinearHeading(new Vector2d(-42,-34), Math.toRadians(165),baseVelConstraint)
                .waitSeconds(0.2)  //0.3 seconds
                //TODO Sample 3 Drop
                .stopAndAdd(() -> new PreSampleDropSeq(intake, shoulder,elbow))

                //TODO ALIGN BASKET BEFORE DROPING AND THEN TAKING BOT TOWARDS BASKET      //
                .strafeToLinearHeading(new Vector2d(-48,-45), Math.toRadians(45),baseVelConstraint50)   //

                .strafeToLinearHeading(new Vector2d(-55,-54), Math.toRadians(45),baseVelConstraint50)
                .stopAndAdd(() -> new SampleDropSeq(intake, shoulder, elbow))
                .waitSeconds(0.6 )
                .stopAndAdd(() ->new ParkSeq(intake,shoulder,elbow))

                //TODO Parking
                .strafeToLinearHeading(new Vector2d(-25,-5), Math.toRadians(0));

        if (opModeInInit()) {
            telemetry.addLine("ROBOT INIT MODE");
            Actions.runBlocking(new SequentialAction(
                    new InstantAction(() ->  InitSeq.InitAction(intake, elbow, shoulder))
            ));

        }


        waitForStart();
        //pidThread.start();

        Actions.runBlocking(
                new SequentialAction(
                        trajectoryAction.build()
                ));
        while (opModeIsActive()) {
            telemetry.addData("x", drive.pose.position.x);
            telemetry.addData("y", drive.pose.position.y);
            telemetry.addData("heading (deg)", Math.toDegrees(drive.pose.heading.toDouble()));
            telemetry.addData("Navx heading (deg)", TwoDeadWheelLocalizer.robotHeading);
            telemetry.update();
        }


    }
}
