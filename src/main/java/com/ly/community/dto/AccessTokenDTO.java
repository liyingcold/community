package com.ly.community.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * dto data transform do
 */

@Getter
@Setter
public class AccessTokenDTO {

    private  String client_id;
    private  String client_secret;
    private  String code;
    private  String redirect_uri;
    private  String state;



}
