package com.isep.meia.grupo7;

import com.isep.meia.grupo7.jade.agents.*;
import com.isep.meia.grupo7.otimization.*;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        int[] dronesRange = {100,100,100,100,100,150};
        ArrayList<Drone> drones =  Optimizer.run(600, 600, dronesRange);
        System.out.println(drones);

        /*jade.core.Runtime rt = jade.core.Runtime.instance();
        rt.setCloseVM(true);
        Profile pMain = new ProfileImpl();
        pMain.setParameter(Profile.MTPS, null);
        pMain.setParameter(Profile.LOCAL_HOST, "localhost");
        pMain.setParameter(Profile.LOCAL_PORT, "5038");
        pMain.setParameter(Profile.GUI, "false");
        AgentContainer mc = rt.createMainContainer(pMain);

        Object[] agentArgs = {};
        try {

            for (int i = 0; i < 4; i++) {
                mc.createNewAgent("HighResolutionSensor" + i, HighResolutionAgent.class.getCanonicalName(), agentArgs).start();

            }
            for (int i = 0; i < 4; i++) {
                mc.createNewAgent("ActuatorDrone" + i, ActuatorAgent.class.getCanonicalName(), agentArgs).start();

            }


            mc.createNewAgent("SensorServer", SensorServer.class.getCanonicalName(), agentArgs).start();
            mc.createNewAgent("ActuatorServer", ActuatorServer.class.getCanonicalName(), agentArgs).start();
            for (int i = 0; i < 2; i++) {
                mc.createNewAgent("LowResolutionSensor" + i, LowResolutionSensor.class.getCanonicalName(), agentArgs).start();
                Thread.sleep(10000);
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }*/
    }

}