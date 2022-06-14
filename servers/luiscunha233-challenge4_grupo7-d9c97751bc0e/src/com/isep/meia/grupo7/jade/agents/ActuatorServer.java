package com.isep.meia.grupo7.jade.agents;

import com.isep.meia.grupo7.jade.agents.Models.*;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.ArrayList;


public class ActuatorServer extends jade.core.Agent {


    private ArrayList<RespostaDistancia> responses ;
    private int numberOfHigh =2;
    protected void setup() {
        //System.out.println("Hello World!");

        Object[] clienteArgs = this.getArguments();
        responses = new ArrayList<RespostaDistancia>();




        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = blockingReceive();


                try {

                    if (msg.getContentObject() instanceof RespostaHighResIsFire) {
                        RespostaHighResIsFire respostaHighRes = (RespostaHighResIsFire) msg.getContentObject();
                        System.out.println(respostaHighRes.getResultado() + " received from " + respostaHighRes.getIdOrigem());
                        if(respostaHighRes.getResultado()==true){
                            for(int i = 0 ; i<2;i++) {
                                ACLMessage msg3 = new ACLMessage(ACLMessage.INFORM);

                                AID id3 = new AID();
                                id3.setLocalName("ActuatorDrone"+i);

                                msg3.addReceiver(id3);

                                PedidoDeDistância pedido = new PedidoDeDistância(5, 5, this.getAgent().getLocalName());
                                try {
                                    msg3.setContentObject(pedido);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                send(msg3);
                                System.out.println("Sent a call to check the area to Actuator Drone" +i);
                            }
                        }
                    }
                    if (msg.getContentObject() instanceof RespostaDistancia) {
                        RespostaDistancia respostaHighRes = (RespostaDistancia)msg.getContentObject();
                        System.out.println(respostaHighRes.getResultado() + " received");
                        responses.add(respostaHighRes);
                        if(responses.size()==2){
                            float min =100000;
                            RespostaDistancia minResp = new RespostaDistancia("");
                            for(RespostaDistancia respon : responses){
                                if(respon.getResultado()<min){
                                    minResp=respon;
                                }
                            }
                            ACLMessage msg3 = new ACLMessage(ACLMessage.INFORM);

                            AID id3 = new AID();
                            id3.setLocalName(minResp.getIdOrigem());

                            msg3.addReceiver(id3);

                            PedidoDeApagarFogo pedido = new PedidoDeApagarFogo(5,5,this.getAgent().getLocalName());
                            try {
                                msg3.setContentObject(pedido);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            send(msg3);
                            System.out.println("Sent a call to check the area to " + minResp.getIdOrigem());
                        }




                    }



                } catch (UnreadableException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}


