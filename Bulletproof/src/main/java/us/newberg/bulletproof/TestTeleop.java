package us.newberg.bulletproof;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TestTeleop", group = "Test")
public class TestTeleop extends BulletproofOpMode
{
    @Override
    protected void Init()
    {
        super.Init();
    }

    @Override
    public void runOpMode() throws InterruptedException
    {
        Init();


    }
}
