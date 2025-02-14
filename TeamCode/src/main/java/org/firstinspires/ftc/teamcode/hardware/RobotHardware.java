package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotHardware {

    //TODO ----------------------------ACTUATORS--------------------------------
    public static Servo s1;
    public CRServo intakeRoller;
    public DcMotorEx shoulder;

    public DcMotorEx elbow;
    public Servo specimenGripper ;


    //TODO -----------------------------SENSORS SUBSYSTEM--------------------------------

    public RevColorSensorV3 colorSensor=null;

    private static RobotHardware instance;// Static instance to be used across all instances
    public boolean enabled;
    private HardwareMap hardwareMap;  // Linking to hardware map with robot hardware.


    public static RobotHardware getInstance() {
        if (instance == null) {
            instance = new RobotHardware();
        }
        instance.enabled = true;
        return instance;
    }

    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;


        //TODO============================= MAPPING INTAKE ACTUATORS ===============================

        intakeRoller=hardwareMap.get(CRServo.class,"roller");
        shoulder = hardwareMap.get(DcMotorEx.class, "shoulder");
        elbow = hardwareMap.get(DcMotorEx.class, "elbow");
        specimenGripper=hardwareMap.get(Servo.class,"grip");

        //TODO============================= MAPPING SENSORS ===============================
        colorSensor=hardwareMap.get(RevColorSensorV3.class,"color");

        //TODO============================= OTHER INITIALIZATION ===============================
        shoulder.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        elbow.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        shoulder.setDirection(DcMotorSimple.Direction.FORWARD);
        elbow.setDirection(DcMotorSimple.Direction.FORWARD);
        shoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elbow.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


//        shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        elbow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Add any other initialization code here if needed.
    }
//    public void init(HardwareMap hardwareMap) {
//        s1 = hardwareMap.get(Servo.class, "s1");
//    }

    public void resetEncoder() {
////        //Encoder
//        shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        elbow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}
