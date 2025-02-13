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
////                new SleepAction(0.4),
//                new InstantAction(() -> new ElbowCommand(elbow, Elbow.ElbowState.SAFE)),
//        new SleepAction(1),
        new InstantAction(() -> new ElbowCommand(elbow, Elbow.ElbowState.HIGH_BUCKET))
        ));
    }

}