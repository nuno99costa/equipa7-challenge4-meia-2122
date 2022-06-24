package com.isep.meia.grupo7.jade.agents;

import com.isep.meia.grupo7.jade.agents.Models.AuctionScoreRequest;
import com.isep.meia.grupo7.jade.agents.Models.HighResScanRequest;
import com.isep.meia.grupo7.jade.agents.Models.AuctionScoreResponse;
import com.isep.meia.grupo7.jade.agents.Models.HighResScanResponse;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.ArrayList;


public class SensorServer extends jade.core.Agent {


    private ArrayList<AuctionScoreResponse> responses ;
    private ArrayList<Integer> used;
    private int numberOfHigh =4;
    protected void setup() {
        //System.out.println("Hello World!");

        Object[] clienteArgs = this.getArguments();

        responses = new ArrayList<AuctionScoreResponse>();
        used = new ArrayList<Integer>();


        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = blockingReceive();


                try {
                    if (msg.getContentObject() instanceof AuctionScoreResponse) {

                        AuctionScoreResponse respostaHighRes = (AuctionScoreResponse)msg.getContentObject();

                        System.out.println(respostaHighRes.getResult() + " received");
                       responses.add(respostaHighRes);
                        if(responses.size()==numberOfHigh-used.size()){
                            float min =100000;
                            AuctionScoreResponse minResp = new AuctionScoreResponse("");
                            for(AuctionScoreResponse respon : responses){
                                if(respon.getResult()<min){
                                    minResp=respon;
                                }
                            }
                             ACLMessage msg3 = new ACLMessage(ACLMessage.INFORM);

                        AID id3 = new AID();
                        id3.setLocalName(minResp.getOriginId());

                        msg3.addReceiver(id3);

                            HighResScanRequest pedido = new HighResScanRequest(5,5,this.getAgent().getLocalName());
                            used.add(Integer.parseInt(minResp.getOriginId().replaceAll("[^0-9]", "")));
                        try {
                            msg3.setContentObject(pedido);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        send(msg3);
                        System.out.println("Sent a call to check the area to " + minResp.getOriginId());
                        }



                    }
                    if(msg.getContentObject() instanceof AuctionScoreRequest){

                        for (int i = 0; i < numberOfHigh; i++) {
                            if(!used.contains(i)){
                            AuctionScoreRequest operacao = (AuctionScoreRequest)msg.getContentObject();

                            ACLMessage msg4 = new ACLMessage(ACLMessage.INFORM);

                            AID id = new AID();

                                id.setLocalName("HighResolutionSensor" + i);



                            msg4.addReceiver(id);

                            AuctionScoreRequest operacao2 = new AuctionScoreRequest(operacao.getX(),operacao.getY(),this.getAgent().getLocalName());
                            try {
                                msg4.setContentObject(operacao2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            send(msg4);

                            System.out.println("Sent From " +this.getAgent().getLocalName() + " a High Res Request to High Resolution Sensor " +i );
                        }}

                    }
                    if (msg.getContentObject() instanceof HighResScanResponse) {
                        HighResScanResponse resposta = (HighResScanResponse)msg.getContentObject();
                        used.remove(Integer.valueOf(Integer.parseInt(resposta.getOriginId().replaceAll("[^0-9]", ""))));
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


