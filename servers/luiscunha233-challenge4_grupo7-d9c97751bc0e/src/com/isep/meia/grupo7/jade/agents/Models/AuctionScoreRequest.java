package com.isep.meia.grupo7.jade.agents.Models;

import java.io.Serializable;

public class AuctionScoreRequest extends Request implements Serializable {

    public AuctionScoreRequest(int x, int y, String originId) {
        super(x, y, originId);
    }
}
