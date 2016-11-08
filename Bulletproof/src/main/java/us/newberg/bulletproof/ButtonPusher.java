package us.newberg.bulletproof;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * FTC team 6712 Bulletproof
 */
public class ButtonPusher
{
    private Servo _leftSide;
    private Servo _rightSide;

    private Telemetry.Item _leftPosition;
    private Telemetry.Item _rightPosition;

    public ButtonPusher(Telemetry telemetry)
    {
        _leftPosition = telemetry.addData("Left Pusher", "%.2f", )
    }
}
