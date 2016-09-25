package us.newberg.bulletproof;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import us.newberg.bulletproof.math.MathUtil;
import us.newberg.bulletproof.math.Vector2f;

/**
 * FTC team 6712 Bulletproof
 */

public class DriveTrain
{
    /**
     * All the motors involved in the drive train
     */
    public enum Motors
    {
        FRONT_LEFT(0),
        FRONT_RIGHT(1),
        BACK_LEFT(2),
        BACK_RIGHT(3);

        private final int _value;

        Motors(int value)
        {
            _value = value;
        }

        public int GetValue()
        {
            return _value;
        }
    }

    /** The number of motors the drive train contains */
    public static final int MOTOR_COUNT = Motors.values().length;

    private DcMotor _frontLeft;
    private DcMotor _frontRight;
    private DcMotor _backLeft;
    private DcMotor _backRight;

    private DriveTrainHelper _driveTrainHelper;

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

        _driveTrainHelper = new DriveTrainHelper(this);
    }

    /**
     * Stop all drive train motors
     */
    public void StopAll()
    {
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
     * See {@link Motors} for a list of all the motors. Use the enum {@link Motors#GetValue()} for access indices
     *
     * @return The current readings of all the encoders
     */
    public int[] GetEncoderValues()
    {
        int[] values = new int[MOTOR_COUNT];

        values[Motors.FRONT_LEFT.GetValue()] = _frontLeft.getCurrentPosition();
        values[Motors.FRONT_RIGHT.GetValue()] = _frontRight.getCurrentPosition();
        values[Motors.BACK_LEFT.GetValue()] = _backLeft.getCurrentPosition();
        values[Motors.BACK_RIGHT.GetValue()] = _backRight.getCurrentPosition();

        return values;
    }

    /**
     *  The the power of the left side wheels
     *
     * @return The x is the front power and the y is the back power
     */
    public Vector2f GetLeftSidePower()
    {
        Vector2f result = new Vector2f((float) _frontLeft.getPower(), (float) _backLeft.getPower());

        return result;
    }

    /**
     *  The the power of the right side wheels
     *
     * @return The x is the front power and the y is the back power
     */
    public Vector2f GetRightSidePower()
    {
        Vector2f result = new Vector2f((float) _frontRight.getPower(), (float) _backRight.getPower());

        return result;
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
     * Drive with the whole left side at the same power, and the whole right at another constant power
     *
     * The power must be between -1.0 and 1.0
     *
     * @param leftPower The power for the left wheels
     * @param rightPower The power for the right wheels
     */
    public void Drive(float leftPower, float rightPower)
    {
        Drive(new Vector2f(leftPower), new Vector2f(rightPower));
    }

    /**
     * Drive with a left side power and a right side power
     *
     * The x is the front wheel power
     * The y is the back wheel power
     *
     * @param leftSidePower  The x component is the front wheel power, and the y component the back wheel power
     * @param rightSidePower The x component is the front wheel power, and the y component the back wheel power
     */
    public void Drive(Vector2f leftSidePower, Vector2f rightSidePower)
    {
        // TODO(Garrison): Should we just pass a left float and a right float?

        _frontLeft.setPower(leftSidePower.x);
        _backLeft.setPower(leftSidePower.y);
        _frontRight.setPower(rightSidePower.x);
        _backRight.setPower(rightSidePower.y);
    }

    /**
     * Rotate the front of the robot by {@param deg}
     * Uses {@link #GetEncoderValue()} for all encoder checking
     *
     * Currently doesn't convert between deg and encoder ticks, but will in the future
     *
     * @throws InterruptedException For ftc_app to catch, if something should go <em>very</em> wrong
     * @param deg The angle to rotate in degrees
     * @param power The target power to turn at 1.0 is full power forward, -1.0 is full power back(inverts {@param deg})
     */
    public void Rotate(float deg, float power) throws InterruptedException
    {
        // TODO(Garrison): Measure how many encoder ticks are in a single degree
        // Update javadoc
        float DEG_TO_TICKS = 1;

        float ticksToMove = deg * DEG_TO_TICKS;
        float currentTicks = GetEncoderValue();
        float targetTicks = currentTicks + ticksToMove;

        _driveTrainHelper.SetTask(HelperTask.STOP_ALL);

        // TODO(Garrison): Calculate how long it should take to rotate some ticks(or degrees) at some power
        WatchDog.Watch(_driveTrainHelper, 10000);

        // TODO(Garrison): See how precise we can make this
        while (MathUtil.FuzzyEquals(currentTicks, targetTicks, 10)) // Give or take 10 ticks
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

        WatchDog.Stop();

        _frontLeft.setPower(0);
        _backRight.setPower(0);
        _frontRight.setPower(0);
        _backRight.setPower(0);
    }

    // TODO(Peacock): A {@link Telemetry} updater/display

    private enum HelperTask
    {
        NONE,
        STOP_ALL,
    }

    private static final int HELPER_TASK_COUNT = HelperTask.values().length;

    private class DriveTrainHelper implements Runnable
    {
        private DriveTrain _driveTrain;
        private HelperTask _task;

        private DriveTrainHelper(DriveTrain target)
        {
            _driveTrain = target;
            _task = HelperTask.NONE;
        }

        private void SetTask(HelperTask task)
        {
            _task = task;
        }

        private void StopAll()
        {
            _driveTrain.StopAll();
        }

        @Override
        public void run()
        {
            switch (_task)
            {
                default:
                case NONE:
                    break;

                case STOP_ALL:
                    StopAll();
                    break;
            }
        }
    }
}
