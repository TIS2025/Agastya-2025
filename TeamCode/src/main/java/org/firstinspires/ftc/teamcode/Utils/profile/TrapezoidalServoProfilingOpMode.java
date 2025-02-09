package org.firstinspires.ftc.teamcode.Utils.profile;//package org.firstinspires.ftc.teamcode.Utils.profile;
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.Servo;
//
//import org.firstinspires.ftc.teamcode.hardware.Globals;
//@Deprecated
//@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Trapezoidal Servo Profiling", group="Linear Opmode")
//public class TrapezoidalServoProfilingOpMode extends LinearOpMode {
//
//    // Servo reference
//    private Servo servo;
//
//    // Servo constants
//    private static final double MAX_POSITION = 1.0; // Maximum servo position
//    private static final double MIN_POSITION = 0.0; // Minimum servo position
//    private static final double MAX_VELOCITY = 0.03; // Maximum velocity (servo position increment per loop)
//    private static final double ACCELERATION = 0.001; // Acceleration rate
//    private static final double DECELERATION_DISTANCE = 0.2; // Distance at which to start decelerating
//
//    // Target and current positions
//    private double targetPosition = Globals.specimenArmInit;
//    private double currentPosition = Globals.specimenArmInit; //Good Practice to have currentPos value same as targetPos to avoid conflicts in initial start of loop
//    private double currentVelocity = 0.0;
//
//    @Override
//    public void runOpMode() {
//        // Initialize the servo from the hardware map
//        servo = hardwareMap.get(Servo.class, "servo");
//
//        // Set initial position
//        servo.setPosition(currentPosition);
//
//        // Wait for the start command
//        waitForStart();
//
//        while (opModeIsActive()) {
//            // Update target position based on controller input
//            if (gamepad1.a) {
//                targetPosition = MAX_POSITION;
//            } else if (gamepad1.b) {
//                targetPosition = MIN_POSITION;
//            }
//
//            // Calculate the distance to the target
//            double distanceToTarget = Math.abs(targetPosition - currentPosition);
//
//            // Determine if we are in the acceleration, constant velocity, or deceleration phase
//            if (distanceToTarget < DECELERATION_DISTANCE) {
//                // Decelerate as we approach the target
//                currentVelocity = Math.max(ACCELERATION, currentVelocity - ACCELERATION);
//            } else if (currentVelocity < MAX_VELOCITY) {
//                // Accelerate if we are far enough from the target
//                currentVelocity = Math.min(MAX_VELOCITY, currentVelocity + ACCELERATION);
//            }
//
//            // Update the current position based on the current velocity
//            if (currentPosition < targetPosition) {
//                currentPosition = Math.min(currentPosition + currentVelocity, targetPosition); // Move towards target
//            } else if (currentPosition > targetPosition) {
//                currentPosition = Math.max(currentPosition - currentVelocity, targetPosition); // Move towards target
//            }
//
//            // Set the servo position
//            servo.setPosition(currentPosition);
//
//            // Display the telemetry data for debugging
//            telemetry.addData("Target Position", targetPosition);
//            telemetry.addData("Current Position", currentPosition);
//            telemetry.addData("Current Velocity", currentVelocity);
//            telemetry.update();
//
//            // Short delay for smooth movement
//            sleep(20);
//        }
//    }
//}
