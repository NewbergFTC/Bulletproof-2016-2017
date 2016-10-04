package us.newberg.bulletproof;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

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

    private DcMotor _motorFrontLeft;
    private DcMotor _motorFrontRight;
    private DcMotor _motorBackLeft;
    private DcMotor _motorBackRight;

    private DriveTrainHelper _driveTrainHelper;

    private Telemetry.Item _teleFrontLeft;
    private Telemetry.Item _teleFrontRight;
    private Telemetry.Item _teleBackRight;
    private Telemetry.Item _teleBackLeft;

    public DriveTrain(HardwareMap hardwareMap, Telemetry telemetry)
    {
        _motorFrontLeft  = hardwareMap.dcMotor.get("frontLeft");
        _motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        _motorBackLeft   = hardwareMap.dcMotor.get("backLeft");
        _motorBackRight  = hardwareMap.dcMotor.get("backRight");

        _motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        _motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        _motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        _motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        _motorFrontLeft.setPower(0);
        _motorFrontRight.setPower(0);
        _motorBackLeft.setPower(0);
        _motorBackRight.setPower(0);

        _teleFrontLeft  = telemetry.addData("FL Wheel", "%.2f", _motorFrontLeft.getPower());
        _teleFrontRight = telemetry.addData("FR Wheel", "%.2f", _motorFrontRight.getPower());
        _teleBackLeft   = telemetry.addData("BL Wheel", "%.2f", _motorBackLeft.getPower());
        _teleBackRight  = telemetry.addData("BR Wheel", "%.2f", _motorBackRight.getPower());

        _driveTrainHelper = new DriveTrainHelper(this);
    }

    /**
     * Stop all drive train motors
     */
    public void StopAll()
    {
        _motorFrontLeft.setPower(0);
        _motorFrontRight.setPower(0);
        _motorBackLeft.setPower(0);
        _motorBackRight.setPower(0);
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
        int result = _motorFrontLeft.getCurrentPosition();

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

        values[Motors.FRONT_LEFT.GetValue()] = _motorFrontLeft.getCurrentPosition();
        values[Motors.FRONT_RIGHT.GetValue()] = _motorFrontRight.getCurrentPosition();
        values[Motors.BACK_LEFT.GetValue()] = _motorBackLeft.getCurrentPosition();
        values[Motors.BACK_RIGHT.GetValue()] = _motorBackRight.getCurrentPosition();

        return values;
    }

    /**
     *  The the power of the left side wheels
     *
     * @return The x is the front power and the y is the back power
     */
    public Vector2f GetLeftSidePower()
    {
        Vector2f result = new Vector2f((float) _motorFrontLeft.getPower(), (float) _motorBackLeft.getPower());

        return result;
    }

    /**
     *  The the power of the right side wheels
     *
     * @return The x is the front power and the y is the back power
     */
    public Vector2f GetRightSidePower()
    {
        Vector2f result = new Vector2f((float) _motorFrontRight.getPower(), (float) _motorBackRight.getPower());

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

        _motorFrontLeft.setPower(power);
        _motorFrontRight.setPower(power);
        _motorBackLeft.setPower(power);
        _motorBackRight.setPower(power);
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

        _motorFrontLeft.setPower(leftSidePower.x);
        _motorBackLeft.setPower(leftSidePower.y);
        _motorFrontRight.setPower(rightSidePower.x);
        _motorBackRight.setPower(rightSidePower.y);
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
                _motorFrontLeft.setPower(power);
                _motorBackRight.setPower(power);
                _motorFrontRight.setPower(-power);
                _motorBackRight.setPower(-power);
            }
            else if (targetTicks < currentTicks)
            {
                _motorFrontLeft.setPower(-power);
                _motorBackRight.setPower(-power);
                _motorFrontRight.setPower(power);
                _motorBackRight.setPower(power);
            }
            else
            {
                break;
            }

            currentTicks = GetEncoderValue();
        }

        WatchDog.Stop();

        _motorFrontLeft.setPower(0);
        _motorBackRight.setPower(0);
        _motorFrontRight.setPower(0);
        _motorBackRight.setPower(0);
    }

    /**
     * Display telemetry data
     *
     * Displays the power to each wheel
     */
    public void UpdateTelemetry()
    {
        _teleFrontLeft.setValue("%.2f",  _motorFrontLeft.getPower());
        _teleFrontRight.setValue("%.2f", _motorFrontRight.getPower());
        _teleBackLeft.setValue("%.2f", _motorBackLeft.getPower());
        _teleBackRight.setValue("%.2f", _motorBackRight.getPower());
    }

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
