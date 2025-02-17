package org.firstinspires.ftc.teamcode.Sequence;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;

import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

public class SampleDropSeq {
    public static Action PreSampleDropHighAction( IntakeSubsystem intake, Elbow elbow, Shoulder shoulder) {

        return new SequentialAction(
                intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.INIT),
//                shoulder.ShoulderCommand(Shoulder.ShoulderState.SPECI_PRE_DROP),//800//EDIT BY DEVA TO GET SHOULDER IN MID POSE IN BETWEEN
//                elbow.ElbowCommand(Elbow.ElbowState.PRE_HANG),//800//EDIT BY DEVA TO GET SHOULDER IN MID POSE IN BETWEEN
//                new SleepAction(0.8),
                shoulder.ShoulderCommand(Shoulder.ShoulderState.DROP),//1200
//                new SleepAction(0.4),
                elbow.ElbowCommand(Elbow.ElbowState.HIGH_BUCKET)//200
//                new SleepAction(3),
//                intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.ON)
        );
    }

    public static Action DropAction(IntakeSubsystem intake, Elbow elbow, Shoulder shoulder) {

        return new SequentialAction(
                intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.INIT),//
                intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.RELEASE),
                //EDITTED BY DEVA TO GET IN INIT POSE AFTER DROPING SAMPLE
                new SleepAction(0.8),
                intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),
                intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.INIT),
                shoulder.ShoulderCommand(Shoulder.ShoulderState.INIT),
                new SleepAction(0.2),
                elbow.ElbowCommand(Elbow.ElbowState.INIT)
        );
    }
    public static Action DropActionSample(IntakeSubsystem intake, Elbow elbow, Shoulder shoulder) {

        return new SequentialAction(
                intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.INIT),//
                intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.RELEASE)
//                //EDITTED BY DEVA TO GET IN INIT POSE AFTER DROPING SAMPLE
//                new SleepAction(0.8),
//                intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),
//                intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.INIT),
//                shoulder.ShoulderCommand(Shoulder.ShoulderState.INIT),
//                new SleepAction(0.2),
//                elbow.ElbowCommand(Elbow.ElbowState.INIT)
        );
    }

    public static Action StopAction(IntakeSubsystem intake, Elbow elbow, Shoulder shoulder) {

        return new SequentialAction(
                intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF)
        );
    }

//    public static Action AfterDropAction(IntakeSubsystem intake, Elbow elbow, Shoulder shoulder) {
//
//        return new SequentialAction(
//                elbow.ElbowCommand(Elbow.ElbowState.AFTER_DROP),
//                shoulder.ShoulderCommand(Shoulder.ShoulderState.AFTER_DROP)
//        );
//    }
//
//
//    public static Action SampleDropAction(Intake intake, Elevator elevator,Shoulder shoulder) {
//
//        return new SequentialAction(
//
//
//                shoulder.ShoulderCommand(Shoulder.ShoulderState.OUTTAKE),
//                intake.GripperCommands(Intake.GripperState.FULL_OPEN),
//                new SleepAction(0.2),
//                intake.WristCommands(Intake.WristState.INIT),
//                new ParallelAction(
//                        intake.ArmCommands(Intake.ArmState.SAMPLE_PRE_PICK),
//                        intake.ElbowCommands(Intake.ElbowState.SAMPLE_PRE_PICK))
//
//        );
//    }
//
//    public static Action SampleDropDriveAction(Intake intake, Elevator elevator,Shoulder shoulder) {
//
//        return new SequentialAction(
//
//                elevator.ElevatorCommand(Elevator.ElevatorState.ELEVATOR_HOME),
//                intake.GripperCommands(Intake.GripperState.FULL_OPEN),
//                new SleepAction(0.7),
//                shoulder.ShoulderCommand(Shoulder.ShoulderState.INTAKE),
//                elevator.ElevatorCommand(Elevator.ElevatorState.INTAKE_EXTEND_1)
//
//        );
//    }
//
//
//    public static Action AutoSampleDropObservationZoneAction(Intake intake, Elevator elevator,Shoulder shoulder) {
//
//        return new SequentialAction(
//
//                intake.WristCommands(Intake.WristState.INIT),
//                intake.GripperCommands(Intake.GripperState.FULL_OPEN)
//        );
//    }

}
