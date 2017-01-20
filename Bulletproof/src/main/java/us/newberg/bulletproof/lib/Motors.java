package us.newberg.bulletproof.lib;

/**
 * FTC team 6712 Bulletproof
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motors
{
    public static final double WHEEL_CIRCUMFRENCE = Math.PI * 4;               // inches
    public static final double TICKS_PER_ROTATION = 1120;                       // ticks
    public static final double TICKS_TO_DEG       = 360 / TICKS_PER_ROTATION;  // deg / ticks
    public static final double DEG_TO_TICKS       = TICKS_PER_ROTATION / 360;  // ticks / deg
    public static final double TICKS_TO_INCHES    = TICKS_PER_ROTATION / WHEEL_CIRCUMFRENCE;  // ticks / in
    public static final double INCHES_TO_TICKS    = WHEEL_CIRCUMFRENCE / TICKS_PER_ROTATION;  // in / ticks
    public static final double WHEEL_DISTANCE_CORRECTION = 1.414213526373095; // sqrt(2)

    public static DcMotor FrontLeft;
    public static DcMotor FrontRight;
    public static DcMotor BackLeft;
    public static DcMotor BackRight;
    public static DcMotor Collector;
    public static DcMotor Flipper;
   // public static DcMotor Lifter;

    public static void Init(HardwareMap hardwareMap)
    {
        FrontLeft = hardwareMap.dcMotor.get("frontLeft");
        FrontRight = hardwareMap.dcMotor.get("frontRight");
        BackLeft = hardwareMap.dcMotor.get("backLeft");
        BackRight = hardwareMap.dcMotor.get("backRight");
        Collector = hardwareMap.dcMotor.get("collector");
        Flipper = hardwareMap.dcMotor.get("flipper");
       // Lifter = hardwareMap.dcMotor.get("lifter");
    }
}
