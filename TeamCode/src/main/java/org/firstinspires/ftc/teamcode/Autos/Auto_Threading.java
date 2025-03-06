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
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Sequence.InitSeq;
import org.firstinspires.ftc.teamcode.auto_sequence.ParkSeq;
import org.firstinspires.ftc.teamcode.auto_sequence.PreSampleDropSeq;
import org.firstinspires.ftc.teamcode.auto_sequence.PreSamplePickSeq;
import org.firstinspires.ftc.teamcode.auto_sequence.SampleDropSeq;
import org.firstinspires.ftc.teamcode.auto_sequence.SamplePickSeq;
import org.firstinspires.ftc.teamcode.hardware.Globals;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

import java.util.Arrays;

//@Deprecated
//@Disabled

@Config
@Autonomous(name="Red Sample 1+4 Thread Test🔴")
//@Deprecated
public class Auto_Threading extends LinearOpMode {
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
            new TranslationalVelConstraint(40.0),
            new AngularVelConstraint(Math.PI / 3)
    ));


    VelConstraint baseVelConstraint50 = new MinVelConstraint(Arrays.asList(
            new TranslationalVelConstraint(60.0),
            new AngularVelConstraint(Math.PI / 2)
    ));


    ElapsedTime timer = new ElapsedTime() ;
    private volatile boolean beamInterrupted ; // Shared variable for sensor monitoring
    public int sample_state=0;

    public static boolean sample1 = true ;
    public static boolean sample2 = true ;
    public static boolean sample3 = true ;
    public static boolean sample4 = true ;


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


        TrajectoryActionBuilder trajectoryAction =

                drive.actionBuilder(new Pose2d(-32, -64, Math.toRadians(0)))

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
                .stopAndAdd(() ->new PreSamplePickSeq(intake,shoulder,elbow));

        //TODO 4th sample picking from Submersible
        TrajectoryActionBuilder trajectory1 = trajectoryAction.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-40 ,-5), Math.toRadians(0))
                .stopAndAdd(() ->new SamplePickSeq(intake, elbow, shoulder))
                .strafeToLinearHeading(new Vector2d(-28,-5),Math.toRadians(0));

        //TODO 2nd Try for 4th Sample Pick from Submersible
        TrajectoryActionBuilder trajectory2 = trajectory1.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-22,-5), Math.toRadians(0));

        //TODO Drop Sample into Basket and Parking
        TrajectoryActionBuilder trajectory3 = trajectory2.endTrajectory().fresh()
                .stopAndAdd(() -> new PreSamplePickSeq(intake, shoulder,elbow))
                .strafeToLinearHeading(new Vector2d(-45,-5), Math.toRadians(0))
                .stopAndAdd(() -> new PreSampleDropSeq(intake, shoulder,elbow))
                .strafeToLinearHeading(new Vector2d(-56,-52), Math.toRadians(45),baseVelConstraint50)
                .stopAndAdd(() -> new SampleDropSeq(intake, shoulder, elbow))
                .waitSeconds(0.6 )
                .stopAndAdd(() ->new ParkSeq(intake,shoulder,elbow))
                //TODO Parking
                .strafeToLinearHeading(new Vector2d(-25,-5), Math.toRadians(0));

        //TODO Drop Wrong Sample and Park
        TrajectoryActionBuilder trajectory4 = trajectory2.endTrajectory().fresh()
//                .strafeToLinearHeading(new Vector2d(-55,-54), Math.toRadians(45),baseVelConstraint50)
                .stopAndAdd(() -> new PreSamplePickSeq(intake, shoulder,elbow))
                .stopAndAdd(() -> new SampleDropSeq(intake, shoulder, elbow))
                .waitSeconds(0.5 )
                .strafeToLinearHeading(new Vector2d(-40,-5), Math.toRadians(0))
                .stopAndAdd(() ->new ParkSeq(intake,shoulder,elbow))
                //TODO Parking
                .strafeToLinearHeading(new Vector2d(-25,-5), Math.toRadians(0));
       /////////////////////////////////////////////////////////////////////////////////
        //TODO Strafe and Retry Sample
        TrajectoryActionBuilder trajectory5 = trajectory2.endTrajectory().fresh()
                .stopAndAdd(() -> new PreSamplePickSeq(intake, shoulder,elbow))
                .strafeToLinearHeading(new Vector2d(-40,-5), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(-40,-10), Math.toRadians(0))
                .stopAndAdd(() ->new SamplePickSeq(intake, elbow, shoulder))
                .strafeToLinearHeading(new Vector2d(-30,-10),Math.toRadians(0));

        //TODO Drop Sample into Basket and Parking after Strafing
        TrajectoryActionBuilder trajectory6 = trajectory5.endTrajectory().fresh()
                .stopAndAdd(() -> new PreSamplePickSeq(intake, shoulder,elbow))
                .strafeToLinearHeading(new Vector2d(-45,-10), Math.toRadians(0))
                .stopAndAdd(() -> new PreSampleDropSeq(intake, shoulder,elbow))
                .strafeToLinearHeading(new Vector2d(-55,-54), Math.toRadians(45),baseVelConstraint50)
                .stopAndAdd(() -> new SampleDropSeq(intake, shoulder, elbow))
                .waitSeconds(0.6 )
                .stopAndAdd(() ->new ParkSeq(intake,shoulder,elbow))
                //TODO Parking
                .strafeToLinearHeading(new Vector2d(-25,-5), Math.toRadians(0));

        //TODO Drop Wrong Sample and Park after Strafing
        TrajectoryActionBuilder trajectory7 = trajectory5.endTrajectory().fresh()
//                .strafeToLinearHeading(new Vector2d(-55,-54), Math.toRadians(45),baseVelConstraint50)
                .stopAndAdd(() -> new PreSamplePickSeq(intake, shoulder,elbow))
                .stopAndAdd(() -> new SampleDropSeq(intake, shoulder, elbow))
                .waitSeconds(0.5 )
                .strafeToLinearHeading(new Vector2d(-40,-10), Math.toRadians(0))
                .stopAndAdd(() ->new ParkSeq(intake,shoulder,elbow))
                //TODO Parking
                .strafeToLinearHeading(new Vector2d(-25,-5), Math.toRadians(0));
      /////////////////////////////////////////////////////////////////////////////////////////
//        //TODO Drop Wrong Sample and Park after Strafing
//        TrajectoryActionBuilder trajectory10 = trajectory5.endTrajectory().fresh()
////                .strafeToLinearHeading(new Vector2d(-55,-54), Math.toRadians(45),baseVelConstraint50)
//                .stopAndAdd(() -> new PreSamplePickSeq(intake, shoulder,elbow))
//                .stopAndAdd(() -> new SampleDropSeq(intake, shoulder, elbow))
//                .waitSeconds(0.5 )
//                .strafeToLinearHeading(new Vector2d(-40,-10), Math.toRadians(0))
//                .stopAndAdd(() ->new ParkSeq(intake,shoulder,elbow))
//                //TODO Parking
//                .strafeToLinearHeading(new Vector2d(-25,-5), Math.toRadians(0));








        //TODO Color Sensor Setup

        final NormalizedRGBA[] rgba = new NormalizedRGBA[1];
        final float[][] hsv = new float[1][1];
        final double[] distance = new double[1];
        robot.colorSensor.setGain((float) Globals.Gain);

        if (opModeInInit()) {
            telemetry.addLine("ROBOT INIT MODE");
            Actions.runBlocking(new SequentialAction(
                    new InstantAction(() ->  InitSeq.InitAction(intake, elbow, shoulder))
            ));
        }


        waitForStart();
        //pidThread.start();

//        Actions.runBlocking(
//                new SequentialAction(
//                        trajectoryAction.build()
//                ));
        Thread sensorMonitor = new Thread(() -> {
            while (opModeIsActive()) {

                //TODO Color detection --> Do Not Change
                rgba[0] = robot.colorSensor.getNormalizedColors();
                distance[0] = robot.colorSensor.getDistance(DistanceUnit.MM);
                hsv[0] = rgbToHsv(rgba[0].red, rgba[0].green, rgba[0].blue);

                // TODO HSV RED
                if (((hsv[0][0] < 59) && (hsv[0][0] > 18) && distance[0] < 15)) {
                    Globals.intakeItem = 1;
                }

                //TODO HSV YELLOW
                else if (((hsv[0][0] < 85) && (hsv[0][0] > 59.8) && (hsv[0][2] > 0.95) && distance[0] < 15)) {
                    Globals.intakeItem = 2;
                }

                //TODO: Blue
                else if (((hsv[0][0] < 235) && (hsv[0][0] > 185) && distance[0] < 30)) {
                    Globals.intakeItem = 3;
                }
                else {
                    Globals.intakeItem = 0;
                }

                telemetry.addData("State",sample_state);
                telemetry.addData("Item State",Globals.intakeItem);

                telemetry.update();

                sleep(10);  // Check sensors every 50ms to avoid overloading CPU
            }
        });

        //TODO *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

        //TODO - Preload Droping and 3 Sample Pick and Drop
        if(opModeIsActive()) {
            if (sample_state == 0) {
                Actions.runBlocking(
                        new SequentialAction(
                                trajectoryAction.build()
                        )
                );
                sample_state = 1;
            }
            //TODO 4th sample picking from Submersible
            if (sample_state == 1) {
                Actions.runBlocking(
                        new SequentialAction(
                                trajectory1.build()
                        )
                );
                sample_state = 2;
            }

            sensorMonitor.start();
            //TODO - 1St Sample Picking
            timer.reset();
            timer.startTime();
            while (sample_state == 2) {
                //TODO Red and Yellow
                if (Globals.intakeItem == 1 || Globals.intakeItem == 2) {
                    sample_state = 3;  //Drop Sample into Basket Trajectory
                }
                // TODO Blue
                else if (Globals.intakeItem == 3) {
                    sample_state = 4;  //Drop Wrong Sample and Park
                }
                // TODO Retry Second Time
                else if (timer.seconds() > 1 && timer.seconds() < 2 && Globals.intakeItem == 0) {
                    sample_state = 5;
                    timer.reset();
                }
            }
            //TODO Drop Sample into Basket and Parking
            if (sample_state == 3) {
                Actions.runBlocking(
                        new SequentialAction(
                                trajectory3.build()
                        )
                );
                sample_state = 14;
            }
            //TODO Drop Wrong Sample and Park
            if (sample_state == 4) {
                Actions.runBlocking(
                        new SequentialAction(
                                trajectory4.build()
                        )
                );
                sample_state = 14;

            }
            //TODO 2nd Try for 4th Sample Pick from Submersible
            if (sample_state == 5) {
                Actions.runBlocking(
                        new SequentialAction(
                                trajectory2.build()
                        )
                );
                sample_state = 6;
            }

            while (sample_state == 6) {
                //TODO Red and Yellow
                if (Globals.intakeItem == 1 || Globals.intakeItem == 2) {
                    sample_state = 7;  //Drop Sample into Basket Trajectory
                }
                // TODO Blue
                else if (Globals.intakeItem == 3) {
                    sample_state = 8;  //Drop Wrong Sample and Park
                }
                //TODO Retry Try by strafing
                else if (timer.seconds() > 1 && timer.seconds() < 2 && Globals.intakeItem == 0) {
                    sample_state = 9;
                    timer.reset();
                }
            }
            //TODO Retry Try by strafing
            if (sample_state == 9) {
                Actions.runBlocking(
                        new SequentialAction(
                                trajectory5.build()
                        )
                );
                sample_state = 10;
            }

            while (sample_state == 10) {
                //TODO Red and Yellow
                if (Globals.intakeItem == 1 || Globals.intakeItem == 2) {
                    sample_state = 11;  //Drop Sample into Basket Trajectory
                }
                // TODO Blue
                else if (Globals.intakeItem == 3) {
                    sample_state = 12;  //Drop Wrong Sample and Park
                } else if (timer.seconds() > 1 && timer.seconds() < 2 && Globals.intakeItem == 0)
                {
                    sample_state = 12;
                    timer.reset();
                }
            }
            //TODO Drop Sample into Basket and Parking
            if (sample_state == 11) {
                Actions.runBlocking(
                        new SequentialAction(
                                trajectory6.build()
                        )
                );
                sample_state = 14;
            }
            //TODO Drop Wrong Sample and Park
            if (sample_state == 12) {
                Actions.runBlocking(
                        new SequentialAction(
                                trajectory7.build()
                        )
                );
                sample_state = 14;

            }
        }
//         Stop the sensor thread after autonomous completes
//        try {
//            sensorMonitor.join();
//        } catch (InterruptedException e) {
//            telemetry.addData("Sensor Monitor", "Thread interrupted");
//        }

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
}