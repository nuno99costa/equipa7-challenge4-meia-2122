package com.isep.meia.grupo7.jade.agents;

import com.isep.meia.grupo7.jade.agents.Models.AuctionScoreRequest;
import com.isep.meia.grupo7.jade.agents.Models.MovementRequest;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.Random;


public class LowResolutionSensor extends Agent {
private int x;
private int y;
    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = blockingReceive();

                try {
                    if(msg.getContentObject() instanceof MovementRequest){

                    MovementRequest request = (MovementRequest) msg.getContentObject() ;
                        System.out.println(this.getAgent().getLocalName()+" moving to " + request.getX()+";"+request.getY());
                        x=request.getX();
                        y=request.getY();
                        Random objGenerator = new Random();
                        boolean result = objGenerator.nextBoolean();
                        System.out.println(this.getAgent().getLocalName() + " found fire as " + result);
                        if(result==true){
                            AuctionScoreRequest auctionScoreRequest = new AuctionScoreRequest(request.getX(), request.getY(), this.getAgent().getLocalName());

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
                    }

                } catch (UnreadableException e) {
                    e.printStackTrace();
                }}
        /*addBehaviour(new OneShotBehaviour() {
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
        });*/


    });}}


