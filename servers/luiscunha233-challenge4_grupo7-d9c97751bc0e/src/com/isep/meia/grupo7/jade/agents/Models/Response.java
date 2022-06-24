package com.isep.meia.grupo7.jade.agents.Models;

import java.io.Serializable;

public class Response implements Serializable {

    protected String originId;
    protected Object result;

    public Response(String originId) {
        this.originId = originId;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }
}
