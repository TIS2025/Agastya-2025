package org.firstinspires.ftc.teamcode.auto_sequence;//package org.firstinspires.ftc.teamcode.sequences;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

public class SpecInitSeq {
    public SpecInitSeq(IntakeSubsystem intake, Shoulder shoulder, Elbow elbow) {
        Actions.runBlocking(
                new SequentialAction(
                        intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),
                        //new SleepAction(1),
                        intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.CLOSE),
                        new ParallelAction(
                                shoulder.ShoulderCommand(Shoulder.ShoulderState.INIT),
                                elbow.ElbowCommand(Elbow.ElbowState.INIT)
                        )
        ));
    }
//    public SpecInitSeq(IntakeSubsystem intake, Shoulder shoulder, ShoulderAuto shoulderAuto, Elbow elbow) {
//        Actions.runBlocking(
//                new SequentialAction(
//                        intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),
//                        //new SleepAction(1),
//                        intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.CLOSE),
//                        new ParallelAction(
//                                shoulder.ShoulderCommand(Shoulder.ShoulderState.INIT),
//                                elbow.ElbowCommand(Elbow.ElbowState.INIT)
//                        )
//                ));
//    }

}
