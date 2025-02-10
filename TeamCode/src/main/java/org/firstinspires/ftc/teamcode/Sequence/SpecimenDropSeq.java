package org.firstinspires.ftc.teamcode.Sequence;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;

import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

public class SpecimenDropSeq {
    public static Action AfterDropAction( IntakeSubsystem intake, Elbow elbow, Shoulder shoulder) {

        return new SequentialAction(
//                intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),
//                intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.CLOSE),
//                new ParallelAction(
//                        shoulder.ShoulderCommand(Shoulder.ShoulderState.AFTER_DROP),
//                        elbow.ElbowCommand(Elbow.ElbowState.AFTER_DROP)
//                ),
//                new SleepAction(0.5),
                elbow.ElbowCommand(Elbow.ElbowState.AFTER_DROP),
                new SleepAction(0.2),
                intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.OPEN),
                elbow.ElbowCommand(Elbow.ElbowState.SPECI_DROP)
//                new ParallelAction(
//                shoulder.ShoulderCommand(Shoulder.ShoulderState.SPECI_PICK),
//                elbow.ElbowCommand(Elbow.ElbowState.SPECI_PICK)
//        )
//                elbow.ElbowCommand(Elbow.ElbowState.SPECI_DROP)
                );
    }

    public static Action DropAction(IntakeSubsystem intake, Elbow elbow, Shoulder shoulder) {
        return new SequentialAction(
                intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),
                intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.CLOSE),
                new SleepAction(0.2),
                new ParallelAction(
                        shoulder.ShoulderCommand(Shoulder.ShoulderState.SPECI_PRE_DROP),
                        elbow.ElbowCommand(Elbow.ElbowState.SPECI_PRE_DROP)
                )
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
