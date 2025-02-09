package org.firstinspires.ftc.teamcode.instantcommands;

import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;

public class IntakeGripperCommand {

    public IntakeGripperCommand(IntakeSubsystem intake, IntakeSubsystem.IntakeGripperState state){
        // Use Actions.runBlocking to execute the command
        Actions.runBlocking(new SequentialAction(
                new InstantAction( () -> intake.updateGripperState(state))
        ));
    }
}
