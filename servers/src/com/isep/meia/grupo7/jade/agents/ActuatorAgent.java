package com.isep.meia.grupo7.jade.agents;

import com.isep.meia.grupo7.jade.agents.Models.*;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;

public class ActuatorAgent extends Agent {

    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = blockingReceive();
                try {
                    Object contentObject = msg.getContentObject();

                    if (contentObject instanceof AuctionScoreRequest) {
                        AuctionScoreRequest request = (AuctionScoreRequest) contentObject;
                        System.out.println("Received high res request");
                        ACLMessage aclResponse = new ACLMessage(ACLMessage.INFORM);
                        AID id = new AID();
                        id.setLocalName(request.getOriginId());
                        aclResponse.addReceiver(id);
                        AuctionScoreResponse answer = new AuctionScoreResponse(this.getAgent().getLocalName(), request.getX(), request.getY());
                        aclResponse.setContentObject(answer);

                        send(aclResponse);
                        System.out.println("Sent From " + this.getAgent().getLocalName() + " an answer with value " + answer.getResult() + " to " + request.getOriginId());
                    }

                    if (msg.getContentObject() instanceof HighResScanRequest) {
                        HighResScanRequest request = (HighResScanRequest) msg.getContentObject();
                        System.out.println("Received a request for high res scan on " + this.getAgent().getLocalName());

                        ACLMessage aclResponse = new ACLMessage(ACLMessage.INFORM);
                        AID id = new AID();
                        id.setLocalName("ActuatorServer");
                        aclResponse.addReceiver(id);
                        HighResScanResponse answer = new HighResScanResponse(this.getAgent().getLocalName(), request.getX(), request.getY());
                        aclResponse.setContentObject(answer);

                        send(aclResponse);
                        System.out.println("Sent a response to the " + request.getOriginId() + " with an info");

                    }
                    if (msg.getContentObject() instanceof FireSuppressionRequest) {
                        FireSuppressionRequest request = (FireSuppressionRequest) msg.getContentObject();
                        System.out.println("Fire Suppression request by drone " + this.getAgent().getLocalName() + " on coords: " + request.getX() + "," + request.getY());
                    }


                } catch (UnreadableException | IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }


}


