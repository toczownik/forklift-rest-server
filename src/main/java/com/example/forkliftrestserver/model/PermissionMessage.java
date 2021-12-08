package com.example.forkliftrestserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class PermissionMessage {
    private RegionState status;
    private String forkliftSerialNumber;
}
