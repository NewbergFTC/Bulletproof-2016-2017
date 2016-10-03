package us.newberg.bulletproof.lib;

/**
 * FTC team 6712 Bulletproof
 */

import com.qualcomm.robotcore.hardware.HardwareMap;

import us.newberg.bulletproof.Motor;

public class Motors
{
    // TODO(Garrison): Find some better way to do this
    public static final int MOTOR_COUNT = 4;

    // TODO(Garrison): Find/Calculate these
    public static final float TICKS_PER_ROTATION = 1;
    public static final float TICKS_TO_DEG       = 1;
    public static final float DEG_TO_TICKS       = 1;
    public static final float TICKS_TO_INCHES    = 1;
    public static final float INCHES_TO_TICKS    = 1;

    private static Motor _frontLeft;
    private static Motor _frontRight;
    private static Motor _backLeft;
    private static Motor _backRight;

    public static void Init(HardwareMap hardwareMap)
    {
        _frontLeft = (Motor) hardwareMap.dcMotor.get("frontLeft");
        _frontRight = (Motor) hardwareMap.dcMotor.get("frontRight");
        _backLeft = (Motor) hardwareMap.dcMotor.get("backLeft");
        _backRight = (Motor) hardwareMap.dcMotor.get("backRight");

        _frontLeft.SetHasEncoder(true);
        _frontRight.SetHasEncoder(false);
        _backLeft.SetHasEncoder(false);
        _backRight.SetHasEncoder(true);
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
