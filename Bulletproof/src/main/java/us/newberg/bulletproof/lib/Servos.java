package us.newberg.bulletproof.lib;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * FTC team 6712 Bulletproof
 */
public class Servos
{
    public static Servo BeaconLeft;
    public static Servo BeaconRight;
    public static Servo HopperDoor;

    public static void Init(HardwareMap hardwareMap)
    {
        BeaconLeft = hardwareMap.servo.get("leftServo");
        BeaconRight = hardwareMap.servo.get("rightServo");
        HopperDoor = hardwareMap.servo.get("hopperServo");
    }
}
