package org.firstinspires.ftc.teamcode.Sequence;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;

import org.firstinspires.ftc.teamcode.subsystem.Elbow;
import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

public class InitSeq {
    public static Action InitAction( IntakeSubsystem intake, Elbow elbow, Shoulder shoulder) {
        return new SequentialAction(

                intake.IntakeRollerCommands(IntakeSubsystem.IntakeRollerState.OFF),
                intake.IntakeGripperCommands(IntakeSubsystem.IntakeGripperState.INIT),
                shoulder.ShoulderCommand(Shoulder.ShoulderState.INIT),
//                new SleepAction(0.2),
                elbow.ElbowCommand(Elbow.ElbowState.INIT)

        );
    }

}
