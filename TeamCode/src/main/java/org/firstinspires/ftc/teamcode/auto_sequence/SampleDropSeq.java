package org.firstinspires.ftc.teamcode.auto_sequence;//package org.firstinspires.ftc.teamcode.sequences;

import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.instantcommands.ElbowCommand;
import org.firstinspires.ftc.teamcode.instantcommands.IntakeGripperCommand;
import org.firstinspires.ftc.teamcode.instantcommands.IntakeRollerCommand;
import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

public class SampleDropSeq {
    public SampleDropSeq(IntakeSubsystem intake, Shoulder shoulder, Elbow elbow) {
        Actions.runBlocking(
                new SequentialAction(
//                        new InstantAction(() -> new ElbowCommand(elbow, Elbow.ElbowState.HIGH_BUCKET)),
                        new InstantAction(() -> new IntakeRollerCommand(intake, IntakeSubsystem.IntakeRollerState.RELEASE))
        ));
    }

}