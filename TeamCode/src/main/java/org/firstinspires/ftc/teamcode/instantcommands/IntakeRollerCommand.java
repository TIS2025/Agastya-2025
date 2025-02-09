package org.firstinspires.ftc.teamcode.instantcommands;

import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.subsystem.IntakeSubsystem;

public class IntakeRollerCommand {

    public IntakeRollerCommand(IntakeSubsystem intake, IntakeSubsystem.IntakeRollerState state){
        // Use Actions.runBlocking to execute the command
        Actions.runBlocking(new SequentialAction(
                new InstantAction( () -> intake.updateState(state))
        ));
    }
}
