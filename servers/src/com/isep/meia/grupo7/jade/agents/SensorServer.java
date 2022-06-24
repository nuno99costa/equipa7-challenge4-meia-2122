package com.isep.meia.grupo7.jade.agents;

import com.isep.meia.grupo7.jade.agents.Models.AuctionScoreRequest;
import com.isep.meia.grupo7.jade.agents.Models.AuctionScoreResponse;
import com.isep.meia.grupo7.jade.agents.Models.HighResScanRequest;
import com.isep.meia.grupo7.jade.agents.Models.HighResScanResponse;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.ArrayList;


public class SensorServer extends jade.core.Agent {

    private final int highResAgents = 4;
    private ArrayList<AuctionScoreResponse> responses;
    private ArrayList<Integer> usedAgents;

    protected void setup() {
        responses = new ArrayList<>();
        usedAgents = new ArrayList<>();

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = blockingReceive();

                try {
                    if (msg.getContentObject() instanceof AuctionScoreResponse) {
                        AuctionScoreResponse auctionScoreResponse = (AuctionScoreResponse) msg.getContentObject();
                        System.out.println(auctionScoreResponse.getResult() + " received");
                        responses.add(auctionScoreResponse);

                        if (responses.size() == highResAgents - usedAgents.size()) {
                            float min = Float.MAX_VALUE;
                            AuctionScoreResponse minResp = new AuctionScoreResponse(null);
                            for (AuctionScoreResponse response : responses) {
                                if (response.getResult() < min) {
                                    minResp = response;
                                }
                            }

                            ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);

                            AID aid = new AID();
                            aid.setLocalName(minResp.getOriginId());

                            aclMessage.addReceiver(aid);

                            //TODO - This does not actually work correctly (static coordinates)
                            HighResScanRequest highResScanRequest = new HighResScanRequest(5, 5, this.getAgent().getLocalName());
                            usedAgents.add(Integer.parseInt(minResp.getOriginId().replaceAll("\\D", "")));
                            try {
                                aclMessage.setContentObject(highResScanRequest);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            send(aclMessage);
                            System.out.println("Sent a call to check the area to " + minResp.getOriginId());
                        }


                    }
                    if (msg.getContentObject() instanceof AuctionScoreRequest) {

                        for (int i = 0; i < highResAgents; i++) {
                            if (!usedAgents.contains(i)) {
                                AuctionScoreRequest auctionScoreRequest = (AuctionScoreRequest) msg.getContentObject();

                                ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);

                                AID aid = new AID();

                                aid.setLocalName("HighResolutionSensor" + i);


                                aclMessage.addReceiver(aid);

                                AuctionScoreRequest auctionScoreRequest1 = new AuctionScoreRequest(auctionScoreRequest.getX(), auctionScoreRequest.getY(), this.getAgent().getLocalName());

                                try {
                                    aclMessage.setContentObject(auctionScoreRequest1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                send(aclMessage);

                                System.out.println("Sent From " + this.getAgent().getLocalName() + " a High Res Request to High Resolution Sensor " + i);
                            }
                        }

                    }
                    if (msg.getContentObject() instanceof HighResScanResponse) {
                        HighResScanResponse highResScanResponse = (HighResScanResponse) msg.getContentObject();
                        usedAgents.remove(Integer.valueOf(Integer.parseInt(highResScanResponse.getOriginId().replaceAll("\\D", ""))));
                    }
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}


