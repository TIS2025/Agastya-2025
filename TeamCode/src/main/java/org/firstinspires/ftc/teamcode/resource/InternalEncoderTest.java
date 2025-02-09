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
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.util.Range;
//
//@TeleOp
//@Config
//public class InternalEncoderTest extends LinearOpMode {
//
//    public static DcMotorEx shoulder;
//    public static DcMotorEx elbow;
//
//    public static int shoulder_target = 0;
//    public static int elbow_target = 0;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//
//        shoulder = hardwareMap.get(DcMotorEx.class, "shoulder");
//        elbow = hardwareMap.get(DcMotorEx.class, "elbow");
//
//        shoulder.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
//        elbow.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
//
//        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
//
//        shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        elbow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        shoulder.setDirection(DcMotorSimple.Direction.FORWARD);
//        elbow.setDirection(DcMotorSimple.Direction.FORWARD);
//        shoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        elbow.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        while (opModeInInit()) {
//            telemetry.addData("Shoulder Pos", shoulder.getCurrentPosition());
//            telemetry.addData("Elbow Pos", elbow.getCurrentPosition());
//            telemetry.update();
//        }
//        waitForStart();
//        while (opModeIsActive()) {
//
//            if (gamepad1.a)
//            {
//                shoulder.setTargetPosition(shoulder_target);
//                shoulder.setPower(0.6);
//                shoulder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            }
//            if (gamepad1.b)
//            {
//                elbow.setTargetPosition(elbow_target);
//                elbow.setPower(0.6);
//                elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            }
//            telemetry.addData("Shoulder Pos", shoulder.getCurrentPosition());
//            telemetry.addData("Elbow Pos", elbow.getCurrentPosition());
//            telemetry.update();
//
////        }
//        }
//    }
//}
