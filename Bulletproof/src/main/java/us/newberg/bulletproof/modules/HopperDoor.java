package us.newberg.bulletproof.modules;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * FTC team 6712 Bulletproof
 */
public class HopperDoor
{
    public static float DEPLOY_POS = 0.8f;
    public static float CLOSE_POS = 0.4f;

    private Servo _hopperDoor;

    private Telemetry.Item _teleCurrentPos;

    public HopperDoor(Servo hopperDoor, Telemetry telemetry)
    {
        _hopperDoor = hopperDoor;
        _teleCurrentPos = telemetry.addData("Door pos:", _hopperDoor.getPosition());
    }

    public void UpdateServo(Servo hopperDoor)
    {
        _hopperDoor = hopperDoor;
    }

    public void UpdateTelemetry()
    {
        _teleCurrentPos.setValue(_hopperDoor.getPosition());
    }

    public void Deploy()
    {
        _hopperDoor.setPosition(DEPLOY_POS);
    }

    public void Close()
    {
        _hopperDoor.setPosition(CLOSE_POS);
    }

    public void Toggle()
    {
        if (_hopperDoor.getPosition() == CLOSE_POS)
        {
            Deploy();
        }
        else
        {
            Close();
        }
    }
}
