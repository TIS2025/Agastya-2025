package org.firstinspires.ftc.teamcode.auto_sequence;//package org.firstinspires.ftc.teamcode.sequences;

import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.instantcommands.ElbowCommand;
import org.firstinspires.ftc.teamcode.instantcommands.IntakeGripperCommand;
import org.firstinspires.ftc.teamcode.instantcommands.ShoulderCommand;
import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

public class PreSampleDropSeq {
    public PreSampleDropSeq(IntakeSubsystem intake, Shoulder shoulder, Elbow elbow) {
        Actions.runBlocking(
                new SequentialAction(
                new InstantAction(() -> new IntakeGripperCommand(intake, IntakeSubsystem.IntakeGripperState.INIT)),
                new InstantAction(() -> new ShoulderCommand(shoulder, Shoulder.ShoulderState.DROP)),
                new SleepAction(0.8),
                new InstantAction(() -> new ElbowCommand(elbow, Elbow.ElbowState.HIGH_BUCKET))
        ));
    }

}












/* old
      return new SequentialAction(
                new InstantAction(() -> new IntakeRollerCommand(intake, IntakeSubsystem.IntakeRollerState.ON)),
                new InstantAction(() -> new XExtensionCommand(intake, IntakeSubsystem.XExtensionState.HOME)),
                new InstantAction(() -> new ShoulderCommand(intake, IntakeSubsystem.ShoulderState.SAMPLE_PRE_DROP)),
                new InstantAction(() -> new WristCommand(intake, IntakeSubsystem.WristState.SAMPLE_DROP)),
                new InstantAction(() -> new ElbowCommand(intake, IntakeSubsystem.ElbowState.SAMPLE_DROP)),
                new InstantAction(() -> new ShoulderCommand(intake, IntakeSubsystem.ShoulderState.SAMPLE_DROP)),
                new SleepAction(0.5),
                new InstantAction(() -> new IntakeRollerCommand(intake, IntakeSubsystem.IntakeRollerState.RELEASE)),
                new SleepAction(0.6),

//        new InstantAction(() -> new IntakeRollerCommand(intake, IntakeSubsystem.IntakeRollerState.RELEASE))

//                new SleepAction(timer)
//                new InstantAction(() -> new ShoulderCommand(intake, IntakeSubsystem.ShoulderState.INIT)),
//                new InstantAction(() -> new IntakeRollerCommand(intake, IntakeSubsystem.IntakeRollerState.RELEASE)),
//
//                //REMOVE LATER
//                new SleepAction(0.7),//0.5
                new InstantAction(() -> new ElbowCommand(intake, IntakeSubsystem.ElbowState.INIT))
//                new InstantAction(() -> new IntakeRollerCommand(intake, IntakeSubsystem.IntakeRollerState.OFF))

        );
 */