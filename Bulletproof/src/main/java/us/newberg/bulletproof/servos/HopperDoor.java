package us.newberg.bulletproof.servos;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * FTC team 6712 Bulletproof
 */
public class HopperDoor
{
    public static float DEPLOY_POS = 1;
    public static float CLOSE_POS = 0;

    private Servo _hopperDoor;

    public HopperDoor(Servo hopperDoor)
    {
        _hopperDoor = hopperDoor;
    }

    public void UpdateServo(Servo hopperDoor)
    {
        _hopperDoor = hopperDoor;
    }

    public void Deploy()
    {
        _hopperDoor.setPosition(DEPLOY_POS);
    }

    public void Close()
    {
        _hopperDoor.setPosition(CLOSE_POS);
    }
}
