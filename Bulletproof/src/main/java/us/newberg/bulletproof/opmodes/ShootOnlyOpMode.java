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
        _hopper.QuickDrop();
        _flipper.AutoMoveBlocking(this);
    }
}
