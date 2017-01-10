package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import us.newberg.bulletproof.lib.Motors;

/**
 * FTC team 6712 Bulletproof
 */
@Autonomous(name = "Shoot only", group = "Common")
public class ShootOnlyOpMode extends BulletproofOpMode
{
    @Override
    protected void Run() throws InterruptedException
    {
        _flipper.AutoMoveBlocking(this);

        Motors.Collector.setPower(-1);
        sleep(3000);
        Motors.Collector.setPower(0);

        _flipper.AutoMoveBlocking(this);
    }
}
