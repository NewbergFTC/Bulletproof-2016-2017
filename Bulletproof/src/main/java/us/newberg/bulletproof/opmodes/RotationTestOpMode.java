package us.newberg.bulletproof.opmodes;

import android.hardware.Sensor;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CompassSensor;

import us.newberg.bulletproof.math.Vector3f;

/**
 * FTC team 6712 Bulletproof
 */
@Autonomous(name = "Rotation Test", group = "Test")
public class RotationTestOpMode extends BulletproofOpMode
{
    @Override
    protected void Run() throws InterruptedException
    {
        _rotation = _rotation.InitRotation(new Vector3f(1, 0, 1), (float) Math.toRadians(90));

        telemetry.addData("Rotation x: ", _rotation.x);
        telemetry.addData("Rotation y: ", _rotation.y);
        telemetry.addData("Rotation z: ", _rotation.z);
        telemetry.addData("Rotation w: ", _rotation.w);
    }
}
