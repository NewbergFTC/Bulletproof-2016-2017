package us.newberg.bulletproof.lib;

/**
 * FTC team 6712 Bulletproof
 */

import com.qualcomm.robotcore.hardware.HardwareMap;

import us.newberg.bulletproof.Motor;

public class Motors
{
    public static final double WHEEL_CIRCUMFRENCE = Math.PI * 4;               // inches
    public static final double TICKS_PER_ROTATION = 140;                       // ticks
    public static final double TICKS_TO_DEG       = 360 / TICKS_PER_ROTATION;  // deg / ticks
    public static final double DEG_TO_TICKS       = TICKS_PER_ROTATION / 360;  // ticks / deg
    public static final double TICKS_TO_INCHES    = 140 / WHEEL_CIRCUMFRENCE;  // in / ticks
    public static final double INCHES_TO_TICKS    = WHEEL_CIRCUMFRENCE / 140;  // ticks / in
    public static final double WHEEL_DISTANCE_CORRECTION =
            WHEEL_CIRCUMFRENCE / (Math.sin(Math.PI / 4) * 4 * Math.PI);

    private static Motor _frontLeft;
    private static Motor _frontRight;
    private static Motor _backLeft;
    private static Motor _backRight;

    public static void Init(HardwareMap hardwareMap)
    {
        _frontLeft = new Motor(hardwareMap.dcMotor.get("frontLeft"), true);
        _frontRight = new Motor(hardwareMap.dcMotor.get("frontRight"), true);
        _backLeft = new Motor(hardwareMap.dcMotor.get("backLeft"), true);
        _backRight = new Motor(hardwareMap.dcMotor.get("backRight"), true);
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
