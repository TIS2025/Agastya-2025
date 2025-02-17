package org.firstinspires.ftc.teamcode.Utils;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;

import com.qualcomm.hardware.ams.AMSColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@TeleOp(name = "Servo")
public class ServoTest extends LinearOpMode {
    public CRServo s1 = null;
    @Override
    public void runOpMode() throws InterruptedException {
        s1 = hardwareMap.get (CRServo.class ,"s");
        while (opModeInInit()) {
            s1.setPower (0.5);

        }

        waitForStart ();
        while (opModeIsActive()) {
            if (gamepad1.x) {
                s1.setPower(1);
            } else if (gamepad1.y) {
                s1.setPower(0);
            } else {
                s1.setPower(-1);
            }
        }
    }
}
