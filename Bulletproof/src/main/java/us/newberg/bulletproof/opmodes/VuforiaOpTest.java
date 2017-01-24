package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name = "VuforiaTest", group = "Test")
public class VuforiaOpTest extends LinearOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        parameters.vuforiaLicenseKey = "AYblTAL/////AAAAGc2wFs8chEe1hqa/sjeckOVI8qu/kWhE0ESBxoph/FRyCgFyL2eg1hARujGog3FFfcV8eUyYvmkyOs/7XIOPidYGVA1ytKIoL/43imlszxrbtZQZVAZYIEm+KRpHDQB72ZoveW3DLq2NWQrBrdn+IFuvW/0EURd5JiV8530Qad/FQ9byPeMRSiG/xKK46vCShxBBrLzS4BZc+cqlCXIN+t1+HDUiav/srIebZLC7yJOVTTXl2EDxmtR4pYmhakxl4+e/aaVrf55+s0ZV8jy+cJGxLi9TvQsIc3iNzTbB3R7L9s/9bJ1XklfemXgSeAaOP+RDUI2uEQQiqLmjIUjYK9AgBSa0jA/UCJII+hVZ8nQX";

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Lego");
        beacons.get(3).setName("Gears");

        waitForStart();

        beacons.activate();

        while (opModeIsActive())
        {
            for (VuforiaTrackable beacon : beacons)
            {
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beacon.getListener()).getPose();

                if (pose != null)
                {
                    VectorF translation = pose.getTranslation();
                    double degreesToTurn = Math.toDegrees(Math.atan2(translation.get(1), translation.get(2)));

                    telemetry.addData(beacon.getName() + "-Translation", translation);
                    telemetry.addData(beacon.getName() + "-Degrees", degreesToTurn);
                }
                else
                {
                    // Does not see the beacon
                    // TODO(Garrison): Error handling
                }
            }

            telemetry.update();
        }
    }
}
