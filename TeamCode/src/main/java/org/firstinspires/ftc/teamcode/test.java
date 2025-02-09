package org.firstinspires.ftc.teamcode;//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.Servo;
//@TeleOp
//
//public class test extends LinearOpMode {
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        Servo   servo;
//        servo = hardwareMap.get(Servo .class, "servo");
//
//        // Wait for the start button
//        telemetry.addData(">", "Press Start to scan Servo." );
//        telemetry.update();
//        waitForStart();
//        while(opModeIsActive()){
//
//            // slew the servo, according to the rampUp (direction) variable.
//           if(gamepad1.a)
//           {
//               servo.setPosition(0);
//           }
//           else if (gamepad1.b) {
//               servo.setPosition(0.5);
//           }
//           else if (gamepad1.y)
//           {
//               servo.setPosition(1);
//           }
//
//
//            telemetry.addData(">", "Press Stop to end test." );
//           telemetry.addData("Servo Pose" ,servo.getPosition());
//            telemetry.update();
//
//            // Set the servo to the new position and pause;
//
//        }
//
//        // Signal done;
//        telemetry.addData(">", "Done");
//        telemetry.update();
//    }
//}
