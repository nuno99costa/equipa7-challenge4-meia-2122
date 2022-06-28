package com.isep.meia.grupo7.jade.agents;

import com.isep.meia.grupo7.jade.agents.Models.*;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;

public class HighResolutionAgent extends Agent {

    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = blockingReceive();

                try {
                    if(msg.getContentObject() instanceof AuctionScoreRequest){
                        AuctionScoreRequest auctionScoreRequest = (AuctionScoreRequest)msg.getContentObject();
                        System.out.println("Received high res request");

                        ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
                        AID aid =  new AID();
                        aid.setLocalName(auctionScoreRequest.getOriginId());
                        aclMessage.addReceiver(aid);

                        AuctionScoreResponse auctionScoreResponse = new AuctionScoreResponse(this.getAgent().getLocalName(), auctionScoreRequest.getX(), auctionScoreRequest.getY());
                        aclMessage.setContentObject(auctionScoreResponse);

                        send(aclMessage);
                        System.out.println("Sent from " + this.getAgent().getLocalName() + " an answer with values "+ auctionScoreResponse.getResult() + " and " + auctionScoreResponse.battery + " to " + auctionScoreRequest.getOriginId() );
                    }

                    if(msg.getContentObject() instanceof HighResScanRequest){
                        HighResScanRequest highResScanRequest = (HighResScanRequest)msg.getContentObject();
                        System.out.println("Received a request to go check the fire on " + this.getAgent().getLocalName());

                        ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
                        AID aid =  new AID();
                        aid.setLocalName("ActuatorServer");
                        aclMessage.addReceiver(aid);
                        HighResScanResponse highResScanResponse = new HighResScanResponse(this.getAgent().getLocalName(), highResScanRequest.getX(), highResScanRequest.getY());
                        aclMessage.setContentObject(highResScanResponse);

                        send(aclMessage);
                        System.out.println("Sent a response to the " +highResScanRequest.getOriginId() + " with an info");

                        aclMessage = new ACLMessage(ACLMessage.INFORM);
                        aid =  new AID();
                        aid.setLocalName("SensorServer");
                        aclMessage.addReceiver(aid);

                        aclMessage.setContentObject(highResScanResponse);
                        send(aclMessage);
                    }
                } catch (UnreadableException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}


