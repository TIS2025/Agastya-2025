package org.firstinspires.ftc.teamcode.resource;//package org.firstinspires.ftc.teamcode.resource;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.util.Range;
//
//import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
//
//@TeleOp
//@Config
//public class LifterTest extends LinearOpMode {
//    DcMotorEx m1;
//    DcMotorEx m2;
//
//    DcMotorEx lowHanger=null;
//
//    public static int elevatorPos=0;
//    public static int elevatorTargetPos=1500;
//
//    public static int lowHangerPos=0;
//    public static int lowHangTargetPos=0;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        m1 = hardwareMap.get(DcMotorEx.class, "LLifter");
//        m2 = hardwareMap.get(DcMotorEx.class, "RLifter");
//        lowHanger = hardwareMap.get(DcMotorEx.class, "lowHang");
//
//
//        m1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        m2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//
//        m1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        m2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        lowHanger.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        lowHangerPos=0;
//        elevatorPos=0;
//        waitForStart();
//
//        while (opModeIsActive()) {
//            if (gamepad1.a) {
//                extendTo(elevatorTargetPos);
//            }
//            if (gamepad1.b) {
//                extendTo(0);
//            }
//
//            if(gamepad1.right_bumper){
//                elevatorPos+=50;
//                extendTo(elevatorPos);
//            } else if (gamepad1.left_bumper) {
//                elevatorPos-=50;
//                extendTo(elevatorPos);
//            }
//
//
//
//            if(gamepad1.dpad_up){
//                lowHangerPos+=50;
//                extendLowHang(lowHangerPos);
//            } else if (gamepad1.dpad_down) {
//                lowHangerPos-=50;
//                extendLowHang(lowHangerPos);
//            }
//
//            if(gamepad1.left_trigger>0.3){
//                extendLowHang(lowHangTargetPos);
//            } else if (gamepad1.right_trigger>0.3) {
//                extendLowHang(0);
//
//            }
//
//            elevatorPos= (int) clamp(elevatorPos);
//
//
////            telemetry.addData("Motor Power", m1.getPower());
//            telemetry.addData("Motor Speed", m1.getVelocity());
//            telemetry.addData("MotorCurrent", m1.getCurrent(CurrentUnit.AMPS));
//            telemetry.addData("CurrentPos", m1.getCurrentPosition());
//
//            telemetry.addData("Motor Speed R", m2.getVelocity());
//            telemetry.addData("MotorCurrent 2", m2.getCurrent(CurrentUnit.AMPS));
//            telemetry.addData("CurrentPos R", m2.getCurrentPosition());
//
//            telemetry.addData("low hang",lowHanger.getCurrentPosition());
//            telemetry.addData("hanger current",lowHanger.getCurrent(CurrentUnit.AMPS));
//            telemetry.update();
//
//        }
//    }
//
//    public void extendTo(int targetPos) {
//        m1.setTargetPosition(targetPos);
//        m1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        m1.setPower(1);
//
//        m2.setTargetPosition(targetPos);
//        m2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        m2.setPower(1);
//    }
//
//    public void extendLowHang(int targetPos){
//        lowHanger.setTargetPosition(targetPos);
//        lowHanger.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lowHanger.setPower(1);
//    }
//
//    private double clamp(int position) {
//        return Range.clip(position,0,2500);
//    }
//
//
//    //hanger Pos
//    /*
//    hangerTargetPos full extend-5000
//    hangerTargetPos hanged-3000
//     */
//}
