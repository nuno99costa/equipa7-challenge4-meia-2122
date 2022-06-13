package com.isep.meia.grupo7.jade.agents;

import com.isep.meia.grupo7.jade.agents.Models.PedidoDeDistância;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;


public class LowResolutionSensor extends jade.core.Agent {

    private PedidoDeDistância operacao;
    protected void setup() {
        //System.out.println("Hello World!");

        Object[] clienteArgs = this.getArguments();






        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                    PedidoDeDistância operacao = new PedidoDeDistância(5,5,this.getAgent().getLocalName());
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

                    AID id = new AID();
                    id.setLocalName("SensorServer");

                    msg.addReceiver(id);


                    try {
                        msg.setContentObject(operacao);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    send(msg);
                    System.out.println("Sent From " +this.getAgent().getLocalName() + " a High Res Request to Server  Sensor ");
                }
        });



    }
}


