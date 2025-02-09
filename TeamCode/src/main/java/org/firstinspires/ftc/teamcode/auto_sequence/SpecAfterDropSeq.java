package org.firstinspires.ftc.teamcode.auto_sequence;//package org.firstinspires.ftc.teamcode.sequences;

import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

public class SpecAfterDropSeq {
    public SpecAfterDropSeq(IntakeSubsystem intake, Shoulder shoulder, Elbow elbow) {
        Actions.runBlocking(
                new SequentialAction(
                        //intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),
//                        intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.CLOSE),
//                        new ParallelAction(
//                                shoulder.ShoulderCommand(Shoulder.ShoulderState.AFTER_DROP),
//                                elbow.ElbowCommand(Elbow.ElbowState.SPECI_DROP)
//                        ),
//                        new SleepAction(0.5),
                        intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.OPEN)
//                elbow.ElbowCommand(Elbow.ElbowState.SPECI_DROP)
                ));
    }

}
