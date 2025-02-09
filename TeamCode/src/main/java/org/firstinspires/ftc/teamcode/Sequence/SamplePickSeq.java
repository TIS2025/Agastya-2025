package org.firstinspires.ftc.teamcode.Sequence;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;

import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

public class SamplePickSeq {
    //TODO: LEFT Bumper
    public static Action PreSamplePickAction(IntakeSubsystem intake,Elbow elbow, Shoulder shoulder) {
        return new SequentialAction(
                intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),

                intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.INIT),
                new ParallelAction(
                shoulder.ShoulderCommand(Shoulder.ShoulderState.PRE_INTAKE),
                elbow.ElbowCommand(Elbow.ElbowState.SAMPLE_PRE_PICK)
                        )
        );
    }

    public static Action PickAction( IntakeSubsystem intake, Elbow elbow, Shoulder shoulder) {
        return new SequentialAction(
                intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.INIT),
                intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.ON),
                new ParallelAction(
                        shoulder.ShoulderCommand(Shoulder.ShoulderState.INTAKE),//300
                        elbow.ElbowCommand(Elbow.ElbowState.SAMPLE_PICK)//1550
                )
        );
    }
}
