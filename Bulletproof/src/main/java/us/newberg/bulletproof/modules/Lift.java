package us.newberg.bulletproof.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import us.newberg.bulletproof.lib.Motors;

public class Lift
{
    private DcMotor _motor;

    public Lift(DcMotor motor, Telemetry telemetry)
    {
        _motor = motor;   
    }

    public void UpdateMotor(DcMotor motor)
    {
        _motor = motor;
    }

    public void Raise()
    {
        _motor.setPower(-1.0);
    }
    
    public void Raise(float power)
    {
        _motor.setPower(-1 * Math.abs(power));
    }

    public void Lower()
    {
        _motor.setPower(1.0f);
    }
    
    public void Lower(float power)
    {   
        _motor.setPower(Math.abs(power));
    }
}
