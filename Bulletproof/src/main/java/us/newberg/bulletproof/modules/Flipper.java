package us.newberg.bulletproof.modules;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import us.newberg.bulletproof.Motor;
import us.newberg.bulletproof.lib.Motors;

/**
 * FTC team 6712 Bulletproof
 */
public class Flipper
{
    public static float GEAR_RATIO = 7.2f;

    public enum State
    {
        NOTHING,
        MANUAL,
        AUTO
    }

    private float _targetTicks;
    private Flipper.State _state;

    private Motor _flipperMotor;

    private Telemetry.Item _telState;

    public Flipper(Motor motor, Telemetry telemetry)
    {
        _flipperMotor = motor;
        _telState = telemetry.addData("Flipper State: ", _state);
    }

    public void UpdateMotor(Motor motor)
    {
        _flipperMotor = motor;
    }

    public void StartAutoMove()
    {
        float targetTicks = (float) _flipperMotor.GetCurrentTicks() + ((float)Motors.TICKS_PER_ROTATION * GEAR_RATIO);

        _targetTicks = targetTicks;
        _state = State.AUTO;
    }

    public void SetPower(double power)
    {
        _state = State.NOTHING;
        _flipperMotor.SetPower(power);
    }

    public void Update()
    {
        if (_state == State.AUTO)
        {
            int currentTicks = _flipperMotor.GetCurrentTicks();

            if (currentTicks < _targetTicks)
            {
                SetPower(1.0f);
            }
            else
            {
                SetPower(0);
                _state = State.NOTHING;
            }
        }
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
