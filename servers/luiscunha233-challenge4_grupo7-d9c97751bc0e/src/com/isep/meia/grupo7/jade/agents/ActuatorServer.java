package com.isep.meia.grupo7.jade.agents;

import com.isep.meia.grupo7.jade.agents.Models.*;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.ArrayList;


public class ActuatorServer extends jade.core.Agent {

    protected void setup() {
        ArrayList<AuctionScoreResponse> responses = new ArrayList<>();
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = blockingReceive();
                try {
                    if (msg.getContentObject() instanceof HighResScanResponse) {
                        HighResScanResponse highResResponse = (HighResScanResponse) msg.getContentObject();
                        System.out.println(highResResponse.getResult() + " received from " + highResResponse.getOriginId());
                        if (highResResponse.getResult()) {
                            for (int i = 0; i < 2; i++) {
                                ACLMessage message = new ACLMessage(ACLMessage.INFORM);

                                AID agent = new AID();
                                agent.setLocalName("ActuatorDrone" + i);

                                message.addReceiver(agent);

                                AuctionScoreRequest request = new AuctionScoreRequest(5, 5, this.getAgent().getLocalName());
                                try {
                                    message.setContentObject(request);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                send(message);
                                System.out.println("Sent a call to check area to Actuator Drone" + i);
                            }
                        }
                    }
                    if (msg.getContentObject() instanceof AuctionScoreResponse) {
                        AuctionScoreResponse response = (AuctionScoreResponse) msg.getContentObject();
                        System.out.println(response.getResult() + " received");
                        responses.add(response);
                        if (responses.size() == 2) {
                            float min = Float.MAX_VALUE;
                            AuctionScoreResponse minResp = null;
                            for (AuctionScoreResponse auctionScoreResponse : responses) {
                                if (auctionScoreResponse.getResult() < min) {
                                    minResp = auctionScoreResponse;
                                }
                            }
                            ACLMessage message = new ACLMessage(ACLMessage.INFORM);

                            AID agent = new AID();
                            if (minResp != null) {
                                agent.setLocalName(minResp.getOriginId());
                            }

                            message.addReceiver(agent);

                            FireSuppressionRequest fireSuppressionRequest = new FireSuppressionRequest(5, 5, this.getAgent().getLocalName());
                            try {
                                message.setContentObject(fireSuppressionRequest);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            send(message);
                            System.out.println("Sent a call to check the area to " + (minResp != null ? minResp.getOriginId() : "null"));
                        }
                    }
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}


