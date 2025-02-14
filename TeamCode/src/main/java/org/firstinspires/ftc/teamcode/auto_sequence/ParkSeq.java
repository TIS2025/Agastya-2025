package org.firstinspires.ftc.teamcode.auto_sequence;//package org.firstinspires.ftc.teamcode.sequences;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

public class ParkSeq {
    public ParkSeq(IntakeSubsystem intake, Shoulder shoulder, Elbow elbow) {
        Actions.runBlocking(
                new SequentialAction(
                        intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),
                        new ParallelAction(
                                shoulder.ShoulderCommand(Shoulder.ShoulderState.INIT),
                                elbow.ElbowCommand(Elbow.ElbowState.SPECI_DROP)
                        )
                ));
    }

}
