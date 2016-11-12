package us.newberg.bulletproof.lib;

/**
 * FTC team 6712 Bulletproof
 */

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
    public static final double WHEEL_DISTANCE_CORRECTION =
            WHEEL_CIRCUMFRENCE / (Math.sin(Math.PI / 4) * 4 * Math.PI);

    private static Motor _frontLeft;
    private static Motor _frontRight;
    private static Motor _backLeft;
    private static Motor _backRight;
    private static Motor _collector;
    private static Motor _flipper;

    public static void Init(HardwareMap hardwareMap)
    {
        _frontLeft = new Motor(hardwareMap.dcMotor.get("frontLeft"), true);
        _frontRight = new Motor(hardwareMap.dcMotor.get("frontRight"), true);
        _backLeft = new Motor(hardwareMap.dcMotor.get("backLeft"), false);
        _backRight = new Motor(hardwareMap.dcMotor.get("backRight"), false);
        _collector = new Motor(hardwareMap.dcMotor.get("collector"), false);
        _flipper = new Motor(hardwareMap.dcMotor.get("flipper"), true);
    }

    public static Motor Flipper()
    {
        if (_flipper == null)
        {
            throw new AssertionError("Flipper motor null");
        }

        return _flipper;
    }

    public static Motor Collector()
    {
        if (_collector == null)
        {
            throw new AssertionError("Collector motor null");
        }

        return _collector;
    }

    public static Motor FrontLeft()
    {
        if (_frontLeft == null)
        {
            throw new AssertionError("Front left motor null");
        }

        return _frontLeft;
    }

    public static Motor FrontRight()
    {
        if (_frontRight == null)
        {
            throw new AssertionError("Front right motor null");
        }

        return _frontRight;
    }

    public static Motor BackLeft()
    {
        if (_backLeft == null)
        {
            throw new AssertionError("Back left motor null");
        }

        return _backLeft;
    }

    public static Motor BackRight()
    {
        if (_backRight == null)
        {
            throw new AssertionError("Back right motor null");
        }

        return _backRight;
    }
}
