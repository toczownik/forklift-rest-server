package com.example.forkliftrestserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PermissionMessage {
    private RegionState status;
    private String forkliftSerialNumber;
}
