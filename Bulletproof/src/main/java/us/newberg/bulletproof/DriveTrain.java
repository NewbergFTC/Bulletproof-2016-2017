package us.newberg.bulletproof;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import us.newberg.bulletproof.math.MathUtil;

/**
 * FTC team 6712 Bulletproof
 */

public class DriveTrain
{
    /**
     * All the motors involved in the drive train
     */
    public enum MOTORS
    {
        FRONT_LEFT(0),
        FRONT_RIGHT(1),
        BACK_LEFT(2),
        BACK_RIGHT(3);

        private final int _value;

        MOTORS(int value)
        {
            _value = value;
        }

        public int GetValue()
        {
            return _value;
        }
    }

    /** The number of motors the drive train contains */
    public static final int MOTOR_COUNT = MOTORS.values().length;

    private DcMotor _frontLeft;
    private DcMotor _frontRight;
    private DcMotor _backLeft;
    private DcMotor _backRight;

    public DriveTrain(HardwareMap hardwareMap)
    {
        _frontLeft = hardwareMap.dcMotor.get("frontLeft");
        _frontRight = hardwareMap.dcMotor.get("frontRight");
        _backLeft = hardwareMap.dcMotor.get("backLeft");
        _backRight = hardwareMap.dcMotor.get("backRight");

        _frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        _frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        _backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        _backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        _frontLeft.setPower(0);
        _frontRight.setPower(0);
        _backLeft.setPower(0);
        _backRight.setPower(0);
    }

    /**
     * Get the primary encoder value
     *
     * The front left motor encoder in this case
     *
     * @return The current reading of the encoder
     */
    public int GetEncoderValue()
    {
        int result = _frontLeft.getCurrentPosition();

        return result;
    }

    /**
     * Get all the encoder values of all the motors within the drive train
     * See {@link MOTORS} for a list of all the motors. Use the enum {@link MOTORS#GetValue()} for access indices
     *
     * @return The current readings of all the encoders
     */
    public int[] GetEncoderValues()
    {
        int[] values = new int[MOTOR_COUNT];

        values[MOTORS.FRONT_LEFT.GetValue()] = _frontLeft.getCurrentPosition();
        values[MOTORS.FRONT_RIGHT.GetValue()] = _frontRight.getCurrentPosition();
        values[MOTORS.BACK_LEFT.GetValue()] = _backLeft.getCurrentPosition();
        values[MOTORS.BACK_RIGHT.GetValue()] = _backRight.getCurrentPosition();

        return values;
    }

    /**
     * Drive straight, either forward or back depending on the sign of {@param speed}
     *
     * Currently doesn't do any compensation or checking to see the the motors are running at the same speed
     *
     * @param power The target power, from -1.0 to 1.0. Where 1.0 is full speed ahead, and -1.0 is full speed back
     */
    public void DriveStraight(float power)
    {
        // TODO(Garrison): If the motors are not consistent on both sides, compare encoder values and compensate

        _frontLeft.setPower(power);
        _frontRight.setPower(power);
        _backLeft.setPower(power);
        _backRight.setPower(power);
    }

    /**
     * Rotate the front of the robot by {@param deg}
     * Uses {@link #GetEncoderValue()} for all encoder checking
     *
     * Currently doesn't convert between deg and encoder ticks, but will in the future
     *
     * @param deg The angle to rotate in degrees
     * @param power The target power to turn at 1.0 is full power forward, -1.0 is full power back(inverts {@param deg})
     */
    public void Rotate(float deg, float power)
    {
        // TODO(Garrison): Measure how many encoder ticks are in a single degree
        // Update javadoc
        float DEG_TO_TICKS = 1;

        float ticksToMove = deg * DEG_TO_TICKS;
        float currentTicks = GetEncoderValue();
        float targetTicks = currentTicks + ticksToMove;

        // TODO(Garrison): See how precise we can make this
        while (MathUtil.FuzzyEquals(currentTicks, targetTicks, 10)) // Give of take 10 ticks
        {
            if (targetTicks > currentTicks)
            {
                _frontLeft.setPower(power);
                _backRight.setPower(power);
                _frontRight.setPower(-power);
                _backRight.setPower(-power);
            }
            else if (targetTicks < currentTicks)
            {
                _frontLeft.setPower(-power);
                _backRight.setPower(-power);
                _frontRight.setPower(power);
                _backRight.setPower(power);
            }
            else
            {
                break;
            }

            currentTicks = GetEncoderValue();
        }

        _frontLeft.setPower(0);
        _backRight.setPower(0);
        _frontRight.setPower(0);
        _backRight.setPower(0);
    }
}
