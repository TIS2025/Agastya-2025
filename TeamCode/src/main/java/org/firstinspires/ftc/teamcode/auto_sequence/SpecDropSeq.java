package org.firstinspires.ftc.teamcode.auto_sequence;//package org.firstinspires.ftc.teamcode.sequences;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

public class SpecDropSeq {
    public SpecDropSeq(IntakeSubsystem intake, Shoulder shoulder, Elbow elbow) {
        Actions.runBlocking(
                new SequentialAction(
//                        intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),
//                        intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.INIT),
//                        new SleepAction(0.1),

                        intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.CLOSE),
                        new SleepAction(0.2),
                        new ParallelAction(
                        shoulder.ShoulderCommand(Shoulder.ShoulderState.SPECI_PRE_DROP),
                        elbow.ElbowCommand(Elbow.ElbowState.SPECI_PRE_DROP)
                )

                )
        );
    }
//    public SpecDropSeq(IntakeSubsystem intake, Shoulder shoulder, ShoulderAuto shoulderAuto, Elbow elbow) {
//        Actions.runBlocking(
//                new SequentialAction(
//                        intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),
//                        intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.CLOSE),
//                        new ParallelAction(
//                                shoulderAuto.ShoulderAutoCommand(ShoulderAuto.ShoulderAutoState.SPECI_PRE_DROP),
//                                shoulder.ShoulderCommand(Shoulder.ShoulderState.SPECI_PRE_DROP),
//                                elbow.ElbowCommand(Elbow.ElbowState.SPECI_PRE_DROP)
//                      )
//                ));
//    }

}
