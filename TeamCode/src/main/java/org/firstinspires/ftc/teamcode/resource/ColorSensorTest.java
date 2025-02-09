package org.firstinspires.ftc.teamcode.resource;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

@TeleOp
@Config
public class ColorSensorTest extends LinearOpMode {

    private final RobotHardware robot = RobotHardware.getInstance();
    public int ThresholdColor=1000;
    public int ThresholdDistance=215;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, telemetry);

        while(opModeInInit()){
            robot.intakeRoller.setPower(0);
        }
        waitForStart();

        while (opModeIsActive()){
            if(((robot.colorSensor.red()>=ThresholdColor || robot.colorSensor.blue()>=ThresholdColor || robot.colorSensor.green()>=ThresholdColor ) && robot.colorSensor.getDistance(DistanceUnit.MM)<=ThresholdDistance)
                    && ((robot.colorSensor.red()>=ThresholdColor || robot.colorSensor.blue()>=ThresholdColor || robot.colorSensor.green()>=ThresholdColor) && robot.colorSensor.getDistance(DistanceUnit.MM)<=ThresholdDistance)){
                telemetry.addLine("Got You");
            }


            telemetry.addData("Red Value", robot.colorSensor.red());
            telemetry.addData("Blue Value", robot.colorSensor.blue());
            telemetry.addData("Green Value", robot.colorSensor.green());

            telemetry.addData("Distance Value", robot.colorSensor.getDistance(DistanceUnit.MM));
            telemetry.update();
        }
    }
}
