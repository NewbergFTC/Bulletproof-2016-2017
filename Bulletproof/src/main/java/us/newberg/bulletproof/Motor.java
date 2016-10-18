package us.newberg.bulletproof;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * FTC team 6712 Bulletproof
 */

public class Motor
{
    private final DcMotor _dcMotor;
    private final boolean _hasEncoder;

    public Motor(DcMotor motor)
    {
        this(motor, false);
    }

    public Motor(DcMotor motor, boolean hasEncoder)
    {
        _dcMotor = motor;
        _hasEncoder = hasEncoder;

        _dcMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        _dcMotor.setMode(hasEncoder ? DcMotor.RunMode.RUN_USING_ENCODER : DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void SetPower(double power)
    {
        _dcMotor.setPower(power);
    }

    public double GetPower()
    {
        double result = _dcMotor.getPower();

        return result;
    }

    public int GetCurrentTicks()
    {
        int result = _dcMotor.getCurrentPosition();

        return result;
    }

    /**
     * This shit does magic
     * and will brake everything
     */
    public void ResetTicks()
    {
        double power = _dcMotor.getPower();

        _dcMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        _dcMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        _dcMotor.setPower(power);
    }
}
