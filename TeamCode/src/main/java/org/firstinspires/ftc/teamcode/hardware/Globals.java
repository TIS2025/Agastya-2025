package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Globals {


   public static boolean Is_Thread=false;
 public static double gain = 50.0;
 public static int intakeItem = 0;

    //TODO======================================================== INTAKE SHOULDER ====================================================
    public static boolean SpecimenMode = false;
    public static int shoulderincVal = 10;
    public static int shoulderInit = 0;
    public static int shoulderDrop = 1150;
    public static int shoulderPreIntake = 260;
    public static int shoulderIntake = 320;
    public static int shoulderSafe = 15;
    public static int specimenPick =250;// 0;
    public static int specimenPreDrop =670;//660;
    public static int lowChamber = 0;
    public static int highChamber = 300;
    public static int afterDrop = 50;//400
    public static int shoulderHighHang = 0;
    public static int ShoulderHighHangDone = 0;
    public static int downMax = 0;
    public static int shoulderPreHang = 1450;
    public static int shoulderHang = -300;
    public static int upMax = 3000;

    //TODO======================================================== INTAKE ELBOW ====================================================

    public static int elbowincVal = 30;
    public static int elbowHome = 0;
    public static int elbowInit = 0;
    public static int elbowSafe = 200;
    public static int elbowExtendHighBucket = 150;//200
    public static int elbowExtendSamplePick = 1600;
    public static int elbowExtendSamplePrePick = 1200;//1200
    public static int elbowHighHang = -2500;
    public static int elbowSpeciPick = 450;//370
    public static int elbowSpeciPreDrop = -100;//450;
    public static int elbowSpeciDrop = 450;
    public static int elbowAfterDrop = 200;//455;//500;
    public static int elbowHighHangDone = -600;
    public static int elbowPreHang = 710;//700
    public static int elbowHang = 0;
    public static int elbowMax = -4300;


    //TODO======================================================== INTAKE ROLLER ====================================================

    public static double INTAKE_ON=-1;
    public static double INTAKE_OFF=0;
    public static double INTAKE_RELEASE=1;

    //TODO======================================================== INTAKE GRIPPER ====================================================

    public static double GRIP_INIT=1;
    public static double GRIP_OPEN=1;
    public static double GRIP_CLOSE=0.48;


    public static double incr = 0.01;

    //TODO======================================================== PID STATES ====================================================

    public static int shoulderInitPID = 0;
    public static int shoulderDropPID = 0;
    public static int shoulderPreIntakePID = 0;
    public static int shoulderIntakePID = 0;
    public static int shoulderSafePID = 0;
    public static int elbowInitPID = 0;
    public static int elbowHighBucketPID = 0;
    public static int elbowSamplePickPID = 0;
    public static int elbowSamplePrePickPID = 0;
    public static int elbowHighHangPID = 0;

    //    public static int elbowAfterDrop = 0;
    public static int lowChamber1PID = 0;
    public static int lowChamber2PID = 0;
    public static int highChamber1PID = 0;
    public static int highChamber2PID = 0;
    public static int highHangPID = 0;
    public static int highHangDonePID = 0;
    public static int elevatorMaxPID= 0;
    public static double factor = 0.5;

}


////////////////////////without PID
//
////TODO======================================================== INTAKE SHOULDER ====================================================
//
//    public static boolean PID = false;
//    public static int shoulderincVal = 10;
//    public static int shoulderInit = 0;
//    public static int shoulderDrop = 1200;
//    public static int shoulderPreIntake = 300;//260;
//    public static int shoulderIntake = 440;//320;
//    public static int shoulderSafe = 15;
//    public static int specimenPick = 0;
//    public static int specimenPreDrop = 750;
//    public static int lowChamber = 0;
//    public static int highChamber = 300;
//    public static int afterDrop = 400;
//    public static int shoulderHighHang = 0;
//    public static int ShoulderHighHangDone = 0;
//    public static int downMax = 0;
//    public static int shoulderPreHang = 1400;
//    public static int shoulderHang = -300;
//    public static int upMax = 3000;
//
//    //TODO======================================================== INTAKE ELBOW ====================================================
//
//    public static int elbowincVal = 30;
//    public static int elbowHome = 0;
//    public static int elbowInit = 0;
//    public static int elbowSafe = 150;
//    public static int elbowExtendHighBucket = 200;
//    public static int elbowExtendSamplePick = 1550;
//    public static int elbowExtendSamplePrePick = 1300;
//    public static int elbowHighHang = -2500;
//    public static int elbowSpeciPick = 400;
//    public static int elbowSpeciPreDrop = 0;
//    public static int elbowSpeciDrop = 350;
//    public static int elbowAfterDrop = 350;
//    public static int elbowHighHangDone = -600;
//    public static int elbowPreHang = 700;
//    public static int elbowHang = 0;
//    public static int elbowMax = -4300;
//
//
//    //TODO======================================================== INTAKE ROLLER ====================================================
//
//    public static double INTAKE_ON=-1;
//    public static double INTAKE_OFF=0;
//    public static double INTAKE_RELEASE=1;
//
//    //TODO======================================================== INTAKE GRIPPER ====================================================
//
//    public static double GRIP_INIT=1;
//    public static double GRIP_OPEN=0.9;
//    public static double GRIP_CLOSE=0.4272;
//
//    public static double incr = 0;
//
//    //TODO======================================================== PID STATES ====================================================
//
//    public static int shoulderInitPID = 0;
//    public static int shoulderDropPID = 0;
//    public static int shoulderPreIntakePID = 0;
//    public static int shoulderIntakePID = 0;
//    public static int shoulderSafePID = 0;
//    public static int elbowInitPID = 0;
//    public static int elbowHighBucketPID = 0;
//    public static int elbowSamplePickPID = 0;
//    public static int elbowSamplePrePickPID = 0;
//    public static int elbowHighHangPID = 0;
//
////    public static int elbowAfterDrop = 0;
//    public static int lowChamber1PID = 0;
//    public static int lowChamber2PID = 0;
//    public static int highChamber1PID = 0;
//    public static int highChamber2PID = 0;
//    public static int highHangPID = 0;
//    public static int highHangDonePID = 0;
//    public static int elevatorMaxPID= 0;
//    public static double factor = 0.5;
//
//}
//
//
