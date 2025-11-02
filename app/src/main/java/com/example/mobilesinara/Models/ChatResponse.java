package com.example.mobilesinara.Models;

import com.google.gson.annotations.SerializedName;

public class ChatResponse {

    @SerializedName("ok")
    private boolean ok;

    @SerializedName("agent")
    private String agent;

    @SerializedName("session_id")
    private String sessionId;

    @SerializedName("contexts")
    private String[] contexts;

    @SerializedName("answer")
    private String answer;

    public boolean isOk() { return ok; }
    public String getAgent() { return agent; }
    public String getSessionId() { return sessionId; }
    public String[] getContexts() { return contexts; }
    public String getAnswer() { return answer; }

    public void setOk(boolean ok) { this.ok = ok; }
    public void setAgent(String agent) { this.agent = agent; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public void setContexts(String[] contexts) { this.contexts = contexts; }
    public void setAnswer(String answer) { this.answer = answer; }
}
