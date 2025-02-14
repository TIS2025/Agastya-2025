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
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Sequence.InitSeq;
import org.firstinspires.ftc.teamcode.TwoDeadWheelLocalizer;
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

@Config
@Autonomous(name="Red Sample 1+4 Thread Test")
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
            new TranslationalVelConstraint(30.0),
            new AngularVelConstraint(Math.PI / 4)
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


        TrajectoryActionBuilder trajectoryAction = drive.actionBuilder(startPose)
                //TODO Preload Sample Drop

                .stopAndAdd(() -> new PreSampleDropSeq(intake, shoulder,elbow))
                .strafeToLinearHeading(new Vector2d(-55,-54), Math.toRadians(45))
                .stopAndAdd(() -> new SampleDropSeq(intake, shoulder, elbow))
                .waitSeconds(0.4)

                //TODO Sample 1 Pick
                //.stopAndAdd(() ->new PreSamplePickSeq(intake, shoulder, elbow))
                .stopAndAdd(() ->new SamplePickSeq(intake, elbow, shoulder))//RECALL
                .strafeToLinearHeading(new Vector2d(-18,-34), Math.toRadians(165))
                //.stopAndAdd(() ->new PreSamplePickSeq(intake, shoulder, elbow))//RECALL
//                .waitSeconds(0.4)
                //.stopAndAdd(() ->new SamplePickSeq(intake, elbow, shoulder))
//                .stopAndAdd(() ->new SamplePickSeq(intake, elbow, shoulder))//RECALL

                .strafeToLinearHeading(new Vector2d(-24,-34), Math.toRadians(163),baseVelConstraint)
                .waitSeconds(0.2)  //0.3 seconds
                //TODO Sample 1 Drop
                .stopAndAdd(() -> new PreSampleDropSeq(intake, shoulder,elbow))
                .strafeToLinearHeading(new Vector2d(-48,-45), Math.toRadians(45))

                .strafeToLinearHeading(new Vector2d(-55,-53), Math.toRadians(45))
                .stopAndAdd(() -> new SampleDropSeq(intake, shoulder, elbow))
                .waitSeconds(0.4)

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
                .strafeToLinearHeading(new Vector2d(-53,-51), Math.toRadians(45))
                .stopAndAdd(() -> new SampleDropSeq(intake, shoulder, elbow))
                .waitSeconds(0.4)

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
                .strafeToLinearHeading(new Vector2d(-52,-51), Math.toRadians(45))
                .stopAndAdd(() -> new SampleDropSeq(intake, shoulder, elbow))
                .waitSeconds(0.4)
                .strafeToLinearHeading(new Vector2d(-50,-45), Math.toRadians(45))

                .stopAndAdd(() ->new PreSamplePickSeq(intake,shoulder,elbow))

                //TODO Parking//Pick from Submersible
                .strafeToLinearHeading(new Vector2d(-40,-5), Math.toRadians(0))
                .stopAndAdd(() ->new SamplePickSeq(intake, elbow, shoulder))
                .strafeToLinearHeading(new Vector2d(-35,-5), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(-24,-5.001), Math.toRadians(0))
                ;

        TrajectoryActionBuilder trajectoryAction1 = trajectoryAction.endTrajectory().fresh()

                .strafeToLinearHeading(new Vector2d(-24,-5.001), Math.toRadians(0))
                .stopAndAdd(() ->new PreSamplePickSeq(intake,shoulder,elbow))
                .strafeToLinearHeading(new Vector2d(-48,-5), Math.toRadians(0))
                .stopAndAdd(() -> new PreSampleDropSeq(intake, shoulder,elbow))
                .strafeToLinearHeading(new Vector2d(-52,-51), Math.toRadians(45))
                .stopAndAdd(() -> new SampleDropSeq(intake, shoulder, elbow))
                .waitSeconds(0.4 )
                .strafeToLinearHeading(new Vector2d(-25,-5), Math.toRadians(0));

        TrajectoryActionBuilder trajectoryAction2 = trajectoryAction.endTrajectory().fresh()
                .stopAndAdd(()-> new SampleDropSeq(intake, shoulder, elbow))
                .waitSeconds(0.4 )
                .strafeToLinearHeading(new Vector2d(-50,-5), Math.toRadians(0))
                .stopAndAdd(()-> new ParkSeq(intake, shoulder, elbow))
                .strafeToLinearHeading(new Vector2d(-40,-5), Math.toRadians(0))

                ;


        final NormalizedRGBA[] rgba = new NormalizedRGBA[1];
        final float[][] hsv = new float[1][1];
        final double[] distance = new double[1];
        robot.colorSensor.setGain((float) Globals.gain);







        if (opModeInInit()) {
            telemetry.addLine("ROBOT INIT MODE");
            Actions.runBlocking(new SequentialAction(
                    new InstantAction(() ->  InitSeq.InitAction(intake, elbow, shoulder))
            ));
            sample1 = true;
            sample2 = true;
            sample3 = true;
            sample4 = true;

        }


        waitForStart();
        //pidThread.start();

        Actions.runBlocking(
                new SequentialAction(
                        trajectoryAction.build()
                ));
        Thread sensorMonitor = new Thread(() -> {
            while (opModeIsActive()) {

                //TODO Color detection --> Do Not Change
                rgba[0] = robot.colorSensor.getNormalizedColors();
                distance[0] = robot.colorSensor.getDistance(DistanceUnit.MM);
                hsv[0] = rgbToHsv(rgba[0].red, rgba[0].green, rgba[0].blue);

                // TODO HSV RED
                if (((hsv[0][0] < 26) && (hsv[0][0] > 18) && distance[0] < 30)) {
                    Globals.intakeItem = 2;
                }

                //TODO HSV YELLOW
                else if (((hsv[0][0] < 80) && (hsv[0][0] > 55) && (hsv[0][2] > 0.95) && distance[0] < 30)) {
                    Globals.intakeItem = 1;
                }

                //TODO: Blue
                else if (((hsv[0][0] < 235) && (hsv[0][0] > 210) && distance[0] < 30)) {
                    Globals.intakeItem = 3;
                }
                else {
                    Globals.intakeItem = 0;
                }

                telemetry.update();

                sleep(10);  // Check sensors every 50ms to avoid overloading CPU
            }
        });

        //TODO *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

        sensorMonitor.start();
        //TODO - Preload Droping and 3 Sample Pick and Drop
        if(sample_state==0){
            Actions.runBlocking(
                    new SequentialAction(
                            trajectoryAction.build()
                    )
            );
            sample_state=1;
        }

        //TODO - 1St Sample Picking
        timer.reset();
        timer.startTime();
        while( sample_state==1){

            if (Globals.intakeItem == 1 && Globals.intakeItem == 2) {
                Actions.runBlocking(
                        new SequentialAction(
                                trajectoryAction1.build()
                        )
                );
            } else if (timer.seconds() > 0.5 && timer.seconds() < 2 && Globals.intakeItem ==0) {
                Actions.runBlocking(
                        new SequentialAction(
                                trajectoryAction2.build()
                        )
                );

                timer.reset();
            }
        }

        

//         Stop the sensor thread after autonomous completes
        try {
            sensorMonitor.join();
        } catch (InterruptedException e) {
            telemetry.addData("Sensor Monitor", "Thread interrupted");
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
}