package org.firstinspires.ftc.teamcode.instantcommands;

import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.subsystem.Shoulder;

public class ShoulderCommand {

    public ShoulderCommand(Shoulder shoulder, Shoulder.ShoulderState state) {
        // Use Actions.runBlocking to execute the command
        Actions.runBlocking(new SequentialAction(
                new InstantAction( () -> shoulder.updateShoulderState(state))
        ));
    }
}
