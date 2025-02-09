package org.firstinspires.ftc.teamcode.Sequence;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;

import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

public class HangSeq {
    public static Action PreHangAction( IntakeSubsystem intake, Elbow elbow, Shoulder shoulder) {
        return new SequentialAction(

                intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),
                intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.INIT),
                shoulder.ShoulderCommand(Shoulder.ShoulderState.PRE_HANG),
//                new SleepAction(0.2),
                elbow.ElbowCommand(Elbow.ElbowState.PRE_HANG)

        );
    }
    public static Action HangAction( IntakeSubsystem intake, Elbow elbow, Shoulder shoulder) {
        return new SequentialAction(

                intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),
                intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.INIT),
                shoulder.ShoulderCommand(Shoulder.ShoulderState.HANG),
                new SleepAction(2),
                elbow.ElbowCommand(Elbow.ElbowState.INIT)

        );
    }

}
