package us.newberg.bulletproof.lib;

import com.elvishew.xlog.XLog;
import com.qualcomm.hardware.hitechnic.HiTechnicNxtLightSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Sensors
{
    public static HiTechnicNxtLightSensor LightSensor;

    public static void Init(HardwareMap hardware)
    {
        LightSensor = (HiTechnicNxtLightSensor) hardware.lightSensor.get("Lightsensor");

        XLog.tag("Sensors").d("Sensors initialized");
    }
}
