package us.newberg.bulletproof.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;

import us.newberg.bulletproof.opmodes.BulletproofOpMode;

/**
 * FTC team 6712 Bulletproof
 */
@Autonomous(name = "ServoTest", group = "Test")
public class TestServoOpMode extends BulletproofOpMode
{
    private Servo _servo;

    @Override
    protected void Init()
    {
        super.Init();

        _servo = hardwareMap.servo.get("test");
        _servo.setPosition(1);
    }

    @Override
    protected void Run() throws InterruptedException
    {
        for (float i = 0; i <= 1.0f; i += 0.1)
        {
            _servo.setPosition(i);

            sleep(500);
            idle();
        }
    }
}
