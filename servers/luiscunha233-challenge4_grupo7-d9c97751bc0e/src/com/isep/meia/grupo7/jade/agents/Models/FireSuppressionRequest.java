package com.isep.meia.grupo7.jade.agents.Models;

import java.io.Serializable;

public class FireSuppressionRequest extends Request implements Serializable {

    public FireSuppressionRequest(int x, int y, String originId) {
        super(x, y, originId);
    }
}
