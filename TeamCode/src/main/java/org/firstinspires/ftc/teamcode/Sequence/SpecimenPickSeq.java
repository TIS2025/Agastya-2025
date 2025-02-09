package org.firstinspires.ftc.teamcode.Sequence;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;

import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

public class SpecimenPickSeq {
    //TODO: LEFT Bumper



    public static Action PreSpecimenPickAction(IntakeSubsystem intake, Elbow elbow, Shoulder shoulder) {
        return new SequentialAction(
                intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),
                intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.OPEN),
                new ParallelAction(
                        shoulder.ShoulderCommand(Shoulder.ShoulderState.SPECI_PICK),
                        elbow.ElbowCommand(Elbow.ElbowState.SPECI_PICK)
                )
        );
    }

    public static Action PickAction( IntakeSubsystem intake, Elbow elbow, Shoulder shoulder) {
        return new SequentialAction(
                intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),
                intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.CLOSE),
                new ParallelAction(
                shoulder.ShoulderCommand(Shoulder.ShoulderState.SPECI_PRE_DROP),
                elbow.ElbowCommand(Elbow.ElbowState.SPECI_PRE_DROP))
        );
    }
}
