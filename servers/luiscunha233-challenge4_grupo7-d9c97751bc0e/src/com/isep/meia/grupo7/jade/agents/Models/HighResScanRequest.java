package com.isep.meia.grupo7.jade.agents.Models;

import java.io.Serializable;

public class HighResScanRequest extends Request implements Serializable {

    public HighResScanRequest(int x, int y, String originId) {
        super(x, y, originId);
    }
}
