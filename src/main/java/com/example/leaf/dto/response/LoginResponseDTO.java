package com.example.leaf.dto.response;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class LoginResponseDTO<T> extends ResponseObject{
    private String token;
    private final String type = "Bearer";
    private T userInfo;

    public LoginResponseDTO(String token, T userInfo) {
        super(HttpStatus.OK, "");
        this.token = token;
        this.userInfo = userInfo;
    }
}
