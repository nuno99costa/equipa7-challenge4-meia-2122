package com.isep.meia.grupo7.jade.agents;

import com.isep.meia.grupo7.jade.agents.Models.AuctionScoreRequest;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;


public class LowResolutionSensor extends Agent {

    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                AuctionScoreRequest auctionScoreRequest = new AuctionScoreRequest(5, 5, this.getAgent().getLocalName());

                ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
                AID aid = new AID();
                aid.setLocalName("SensorServer");
                aclMessage.addReceiver(aid);

                try {
                    aclMessage.setContentObject(auctionScoreRequest);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                send(aclMessage);
                System.out.println("Sent from " + this.getAgent().getLocalName() + " a High Res Request to Server  Sensor ");
            }
        });


    }
}


