package org.firstinspires.ftc.teamcode.resource;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp(name = "Differential Servo Control", group = "Linear Opmode")
public class DifferentialServoTest extends LinearOpMode {

    private Servo servoLeft;
    private Servo servoRight;
    private double servoLeftPosition = 0.5;
    private double servoRightPosition = 0.5;
    private static final double INCREMENT = 0.001;
    private static final double MAX_POS = 1.0;
    private static final double MIN_POS = 0.0;

    @Override
    public void runOpMode() {
        servoLeft = hardwareMap.get(Servo.class, "LShoulder");
        servoRight = hardwareMap.get(Servo.class, "RShoulder");

        servoLeft.setPosition(servoLeftPosition);
        servoRight.setPosition(servoRightPosition);

        waitForStart();

        while (opModeIsActive()) {
            // Viper Motion (e.g., gamepad1.left_bumper for increase, right_bumper for decrease)
            if (gamepad1.left_bumper) {
                viperMotion(true);
            } else if (gamepad1.right_bumper) {
                viperMotion(false);
            }

            // Regular Motion (e.g., gamepad1.dpad_up for increase, dpad_down for decrease)
            if (gamepad1.dpad_up) {
                regularMotion(true);
            } else if (gamepad1.dpad_down) {
                regularMotion(false);
            }

            // Send telemetry for debugging
            telemetry.addData("Servo Left Position", servoLeftPosition);
            telemetry.addData("Servo Right Position", servoRightPosition);
            telemetry.update();
        }
    }

    private void viperMotion(boolean increase) {
        if (increase) {
            servoLeftPosition = Math.min(servoLeftPosition + INCREMENT, MAX_POS);
            servoRightPosition = Math.max(servoRightPosition - INCREMENT, MIN_POS);
        } else {
            servoLeftPosition = Math.max(servoLeftPosition - INCREMENT, MIN_POS);
            servoRightPosition = Math.min(servoRightPosition + INCREMENT, MAX_POS);
        }
        servoLeft.setPosition(servoLeftPosition);
        servoRight.setPosition(servoRightPosition);
    }

    private void regularMotion(boolean increase) {
        if (increase) {
            servoLeftPosition = Math.min(servoLeftPosition + INCREMENT, MAX_POS);
            servoRightPosition = Math.min(servoRightPosition + INCREMENT, MAX_POS);
        } else {
            servoLeftPosition = Math.max(servoLeftPosition - INCREMENT, MIN_POS);
            servoRightPosition = Math.max(servoRightPosition - INCREMENT, MIN_POS);
        }
        servoLeft.setPosition(servoLeftPosition);
        servoRight.setPosition(servoRightPosition);
    }
}
