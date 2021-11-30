package com.example.capstone.models.cards;
import com.example.capstone.models.resources.Resource;
import com.example.capstone.models.resources.Wood;

public class BluePrints extends Assignment {

    private int victoryPoints;

    public BluePrints(int victoryPoints,int direction, Resource resource) {
        this.victoryPoints=victoryPoints;
        this.direction = direction;
        this.victoryPoints = 0;
       // this.initialize(resource);
    }
}
