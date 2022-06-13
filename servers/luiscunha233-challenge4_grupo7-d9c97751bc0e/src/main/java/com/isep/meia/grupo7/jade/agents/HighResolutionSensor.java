package com.isep.meia.grupo7.jade.agents;

import com.isep.meia.grupo7.jade.agents.Models.*;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;

public class HighResolutionSensor extends jade.core.Agent {

    protected void setup() {
        //System.out.println("Hello World!");


        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = blockingReceive();
                try {
                    Object a = msg.getContentObject();
                    if(a instanceof PedidoDeDistância){
                        PedidoDeDistância operacao = (PedidoDeDistância)a;
                        System.out.println("Received high res request");
                        ACLMessage msgResp = new ACLMessage(ACLMessage.INFORM);
                        AID id =  new AID();
                        id.setLocalName(operacao.getIdOrigem());
                        msgResp.addReceiver(id);
                        RespostaDistancia resposta = new RespostaDistancia(this.getAgent().getLocalName());
                        msgResp.setContentObject(resposta);

                        send(msgResp);
                        System.out.println("Sent From " + this.getAgent().getLocalName() + " an answer with value "+ resposta.getResultado() + " to " + operacao.getIdOrigem());
                    }

                    if(msg.getContentObject() instanceof PedidoDeHighResIsFire){
                        PedidoDeHighResIsFire pedido = (PedidoDeHighResIsFire)msg.getContentObject();
                        System.out.println("Received a request to go check the fire on " + this.getAgent().getLocalName());
                        ACLMessage msgResp = new ACLMessage(ACLMessage.INFORM);
                        AID id =  new AID();
                        id.setLocalName("ActuatorServer");
                        msgResp.addReceiver(id);
                        RespostaHighResIsFire resposta = new RespostaHighResIsFire(this.getAgent().getLocalName());
                        msgResp.setContentObject(resposta);

                        send(msgResp);
                        System.out.println("Sent a response to the " +pedido.getIdOrigem() + " with an info");

                        ACLMessage msgResp2 = new ACLMessage(ACLMessage.INFORM);
                        AID id2 =  new AID();
                        id2.setLocalName("SensorServer");
                        msgResp2.addReceiver(id2);

                        msgResp2.setContentObject(resposta);

                        send(msgResp2);

                    }


                } catch (UnreadableException | IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }


}


