package us.newberg.bulletproof;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * FTC team 6712 Bulletproof
 */
@TeleOp(name = "motor", group = "Motor")
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
}
