package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import us.newberg.bulletproof.lib.Motors;

/**
 * FTC team 6712 Bulletproof
 */
@Autonomous(name = "Fuckin Test", group = "Test")
public class FuckinTestOpMode extends BulletproofOpMode
{
    @Override
    protected void Run() throws InterruptedException
    {
        Motors.FrontLeft.setPower(0.5);
        telemetry.addData("FrontLeft", 1);
        Update();
        sleep(1000);
        Update();
        telemetry.addData("FrontLeft", 0);
        Motors.FrontLeft.setPower(0);
        Update();
        sleep(1000);
        Update();
        telemetry.addData("FrontRight", 1);
        Motors.FrontRight.setPower(0.5);
        Update();
        sleep(1000);
        Update();
        telemetry.addData("FrontRight", 0);
        Motors.FrontRight.setPower(0);
        Update();
        sleep(1000);
        Update();
        telemetry.addData("BackLeft", 1);
        Motors.BackLeft.setPower(0.5);
        Update();
        sleep(1000);
        Update();
        telemetry.addData("BackLeft", 0);
        Motors.BackLeft.setPower(0);
        Update();
        sleep(1000);
        Update();
        telemetry.addData("BackRight", 1);
        Motors.BackRight.setPower(0.5);
        Update();
        sleep(1000);
        Update();
        telemetry.addData("BackRight", 0);
        Motors.BackRight.setPower(0);
    }
}
