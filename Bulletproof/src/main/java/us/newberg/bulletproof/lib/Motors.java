package us.newberg.bulletproof.lib;

/**
 * FTC team 6712 Bulletproof
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import us.newberg.bulletproof.Motor;

public class Motors
{
    public static final double WHEEL_CIRCUMFRENCE = Math.PI * 4;               // inches
    public static final double TICKS_PER_ROTATION = 1120;                       // ticks
    public static final double TICKS_TO_DEG       = 360 / TICKS_PER_ROTATION;  // deg / ticks
    public static final double DEG_TO_TICKS       = TICKS_PER_ROTATION / 360;  // ticks / deg
    public static final double TICKS_TO_INCHES    = TICKS_PER_ROTATION / WHEEL_CIRCUMFRENCE;  // ticks / in
    public static final double INCHES_TO_TICKS    = WHEEL_CIRCUMFRENCE / TICKS_PER_ROTATION;  // in / ticks
    public static final double WHEEL_DISTANCE_CORRECTION = 1.414213526373095; // sqrt(2)

    public static DcMotor _FrontLeft;
    public static DcMotor _FrontRight;
    public static DcMotor _BackLeft;
    public static DcMotor _BackRight;
    public static DcMotor _Collector;
    public static DcMotor _Flipper;

    public static Motor FrontLeft;
    public static Motor FrontRight;
    public static Motor BackLeft;
    public static Motor BackRight;
    public static Motor Collector;
    public static Motor Flipper;

    public static void Init(HardwareMap hardwareMap)
    {
        _FrontLeft = hardwareMap.dcMotor.get("frontLeft");
        _FrontRight = hardwareMap.dcMotor.get("frontRight");
        _BackLeft = hardwareMap.dcMotor.get("backLeft");
        _BackRight = hardwareMap.dcMotor.get("backRight");
        _Collector = hardwareMap.dcMotor.get("collector");
        _Flipper = hardwareMap.dcMotor.get("flipper");

        FrontLeft = new Motor(_FrontLeft, true);
        FrontRight = new Motor(_FrontRight, true);
        BackLeft = new Motor(_BackLeft, false);
        BackRight = new Motor(_BackRight, false);
        Collector = new Motor(_Collector, false);
        Flipper = new Motor(_Flipper, true);
    }
}
