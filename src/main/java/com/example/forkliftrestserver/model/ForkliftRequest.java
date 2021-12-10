package com.example.forkliftrestserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.awt.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class ForkliftRequest {
    private String serialNumber = "";
    private Point coords = new Point();
}
