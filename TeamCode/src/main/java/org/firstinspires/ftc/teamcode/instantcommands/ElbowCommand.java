package org.firstinspires.ftc.teamcode.instantcommands;

import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.subsystem.Elbow;

public class ElbowCommand {
    // Implementation of the elbow command

    public ElbowCommand(Elbow elbow, Elbow.ElbowState state) {
        // Use Actions.runBlocking to execute the command
        Actions.runBlocking(new SequentialAction(
                new InstantAction( () -> elbow.updateElbowState(state))
        ));

    }
}
