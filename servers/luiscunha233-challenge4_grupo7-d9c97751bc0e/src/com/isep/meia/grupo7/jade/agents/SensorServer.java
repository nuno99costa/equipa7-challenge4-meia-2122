package com.isep.meia.grupo7.jade.agents;

import com.isep.meia.grupo7.jade.agents.Models.PedidoDeDistância;
import com.isep.meia.grupo7.jade.agents.Models.PedidoDeHighResIsFire;
import com.isep.meia.grupo7.jade.agents.Models.RespostaDistancia;
import com.isep.meia.grupo7.jade.agents.Models.RespostaHighResIsFire;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.ArrayList;


public class SensorServer extends jade.core.Agent {


    private ArrayList<RespostaDistancia> responses ;
    private ArrayList<Integer> used;
    private int numberOfHigh =4;
    protected void setup() {
        //System.out.println("Hello World!");

        Object[] clienteArgs = this.getArguments();

        responses = new ArrayList<RespostaDistancia>();
        used = new ArrayList<Integer>();


        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = blockingReceive();


                try {
                    if (msg.getContentObject() instanceof RespostaDistancia) {

                        RespostaDistancia respostaHighRes = (RespostaDistancia)msg.getContentObject();

                        System.out.println(respostaHighRes.getResultado() + " received");
                       responses.add(respostaHighRes);
                        if(responses.size()==numberOfHigh-used.size()){
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

                            PedidoDeHighResIsFire pedido = new PedidoDeHighResIsFire(5,5,this.getAgent().getLocalName());
                            used.add(Integer.parseInt(minResp.getIdOrigem().replaceAll("[^0-9]", "")));
                        try {
                            msg3.setContentObject(pedido);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        send(msg3);
                        System.out.println("Sent a call to check the area to " + minResp.getIdOrigem());
                        }



                    }
                    if(msg.getContentObject() instanceof PedidoDeDistância){

                        for (int i = 0; i < numberOfHigh; i++) {
                            if(!used.contains(i)){
                            PedidoDeDistância operacao = (PedidoDeDistância)msg.getContentObject();

                            ACLMessage msg4 = new ACLMessage(ACLMessage.INFORM);

                            AID id = new AID();

                                id.setLocalName("HighResolutionSensor" + i);



                            msg4.addReceiver(id);

                            PedidoDeDistância operacao2 = new PedidoDeDistância(operacao.getX(),operacao.getY(),this.getAgent().getLocalName());
                            try {
                                msg4.setContentObject(operacao2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            send(msg4);

                            System.out.println("Sent From " +this.getAgent().getLocalName() + " a High Res Request to High Resolution Sensor " +i );
                        }}

                    }
                    if (msg.getContentObject() instanceof RespostaHighResIsFire) {
                        RespostaHighResIsFire resposta = (RespostaHighResIsFire)msg.getContentObject();
                        used.remove(Integer.valueOf(Integer.parseInt(resposta.getIdOrigem().replaceAll("[^0-9]", ""))));
                    }


                } catch (UnreadableException e) {
                    e.printStackTrace();
                }

            }
        });


    }
    public void removeUsed(Integer i ){
        used.remove(i);
    }
}


