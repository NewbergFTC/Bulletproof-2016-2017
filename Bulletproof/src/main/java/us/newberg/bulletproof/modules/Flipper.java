package us.newberg.bulletproof.modules;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import us.newberg.bulletproof.lib.Motors;

/**
 * FTC team 6712 Bulletproof
 */
public class Flipper
{
    public static float GEAR_RATIO = 10.55f;

    public enum State
    {
        NOTHING,
        MANUAL,
        AUTO
    }

    private float _targetTicks;
    private Flipper.State _state;

    private DcMotor _flipperMotor;

    private Telemetry.Item _telState;

    public Flipper(DcMotor motor, Telemetry telemetry)
    {
        _flipperMotor = motor;
        _telState = telemetry.addData("Flipper State: ", _state);
    }

    public void UpdateMotor(DcMotor motor)
    {
        _flipperMotor = motor;
    }

    public void StartAutoMove()
    {
        float targetTicks = (float) _flipperMotor.getCurrentPosition() + ((float)Motors.TICKS_PER_ROTATION * GEAR_RATIO);

        _targetTicks = targetTicks;
        _state = State.AUTO;

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (_flipperMotor.getCurrentPosition() < _targetTicks)
                {
                    SetPower(1.0f);
                }

                SetPower(0);
                _state = State.NOTHING;
            }
        }).run();
    }

    public void SetPower(double power)
    {
        _state = State.NOTHING;
        _flipperMotor.setPower(power);
    }

    public void UpdateTelemetry()
    {
        _telState.setValue(_state);
    }

    public Flipper.State GetState()
    {
        return _state;
    }
}
