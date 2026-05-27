package br.com.altoalegremercado.aamuserapi.controller.dto;

public class LoginResponse {

    private String token;
    private String type;
    private long expiresIn;

    public LoginResponse(String token, long expiresIn) {
        this.token = token;
        this.type = "Bearer";
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public long getExpiresIn() {
        return expiresIn;
    }
}
