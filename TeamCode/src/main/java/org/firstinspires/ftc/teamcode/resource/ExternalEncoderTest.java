package org.firstinspires.ftc.teamcode.resource;//package org.firstinspires.ftc.teamcode.resource;
//
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.config.Config;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
//import com.arcrobotics.ftclib.controller.PIDController;
//import com.arcrobotics.ftclib.hardware.motors.Motor;
//import com.arcrobotics.ftclib.hardware.motors.MotorEx;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.util.Range;
//
//@TeleOp
//@Config
//public class ExternalEncoderTest extends LinearOpMode {
//
//    public static MotorEx motor;
//    public static MotorEx motor2;
//    public static Motor.Encoder encoder;
//    public static Motor.Encoder encoder2;
//
//
//    public static  PIDController controlr;
//    public static PIDController controlr2;
//
//
//    public static int TargetPos = 1000;
//
//    public static int TargetPos2 = 1000;
//    public static int InitPos = 0;
//    public static int shouldincr = 0;
//    public static int elbowincr = 0;
//    public static double power = 0;
//
//    public static int TargetPos1 = -1000;
//    public static double tolerance =30;
//    public static double kp = 0.0017;
//    public static double ki = 0;
//    public static double kd = 0.00000005;
//    public static double kf = 0.0000001;
//    public static double kp2 = 0.0006;
//    public static double ki2 = 0;
//    public static double kd2 = 0.000001;
//    public static double kf2 = 0;
//    public static double counts = 0;
//    public static double counts2 = 0;
//    public static boolean shoulderDecrFlag = false;
//    public static boolean shoulderIncrFlag = false;
//    public static boolean ElbowIncrFlag = false;
//    public static boolean ElbowDecrFlag = false;
//
//    public static boolean incrementFlag = false;
//    @Override
//    public void runOpMode() throws InterruptedException {
////        motor = new MotorEx(hardwareMap, "m");
////        encoder = new Motor(hardwareMap, "m3").encoder;
////        motor2 = new MotorEx(hardwareMap, "m2");
////        encoder2 = new Motor(hardwareMap, "m4").encoder;
//
//        motor = new MotorEx(hardwareMap, "shoulder");
//        encoder= new MotorEx(hardwareMap,"leftFront").encoder;
////        shoulderEncoder = hardwareMap.get(DcMotorEx.class, "leftFront");
//        motor2 = new MotorEx(hardwareMap, "elbow");
//        encoder2= new MotorEx(hardwareMap,"rightFront").encoder;
//        controlr = new PIDController(kp, ki, kd);
//        controlr2 = new PIDController(kp2, ki2, kd2);
//        motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//        motor2.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//
//        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
//
//        encoder.reset();
//        encoder2.reset();
//
//        while (opModeInInit()) {
//            telemetry.addData("Encoder", encoder.getPosition());
//            telemetry.update();
//        }
//        waitForStart();
//        while (opModeIsActive()) {
//            motor2.setPositionTolerance(tolerance);
//            if (gamepad1.dpad_up) {
//                shoulderIncrFlag = true;
//                shoulderDecrFlag = false;
//            }
//
//            if (gamepad1.dpad_down) {
//                shoulderIncrFlag = false;
//                shoulderDecrFlag = true;
//            }
//
//            if (gamepad1.a) {
//                ElbowIncrFlag = true;
//                ElbowDecrFlag = false;
//            }
//
//            if (gamepad1.b) {
//                ElbowIncrFlag = false;
//                ElbowDecrFlag = true;
//            }
//
//            if (shoulderIncrFlag) {
//                motor.set(-calculat(TargetPos1));
//            }
//            if (shoulderDecrFlag) {
//                motor.set(-calculat(InitPos));
//            }
//            if (ElbowIncrFlag)
//            {
//                if (encoder2.getPosition()<=TargetPos2 && encoder2.getPosition()>=(TargetPos2-100))
//                {
//                    motor2.set(0);
//                }
//                else {
//
//                    motor2.set(calculat2(TargetPos2));
//                }
//
//            }
//            if (ElbowDecrFlag) {
//                motor2.set(calculat2(InitPos));
//            }
//
//            telemetry.addData("Positive Count",controlr2.getPositionError()<=50);
//            telemetry.addData("Negative COUNT",-controlr2.getPositionError()<=50);
//
//        telemetry.addData("Shoulder Incr", shouldincr);
//        telemetry.addData("Elbow Incr", elbowincr);
//        telemetry.addData("POSITION", encoder.getPosition());
//        telemetry.addData("Target POSITION", TargetPos1);
//        telemetry.addData("ERROR", controlr.getPositionError());
//        telemetry.addData("POSITION", encoder2.getPosition());
//        telemetry.addData("Target POSITION", TargetPos2);
//            telemetry.addData("ERROR", controlr2.getPositionError());
//
//        telemetry.update();
//
////        }
//    }
//
//
//    }
//    public static double calculat ( int TargetPos){
//        controlr.setPID(kp, ki, kd);
//        power = Range.clip(controlr.calculate(encoder.getPosition(), TargetPos), -1, 1);
//        return power;
//    }
//    public static double calculat2 ( int TargetPos){
//        controlr2.setPID(kp2, ki2, kd2);
//        power = Range.clip(controlr2.calculate(encoder2.getPosition(), TargetPos), -1, 1);
//        return power;
//    }
//}
