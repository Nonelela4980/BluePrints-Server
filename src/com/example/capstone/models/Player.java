package com.example.capstone.models;

import com.example.capstone.models.cards.Assignment;
import com.example.capstone.models.resources.Resource;
import java.util.List;

public class Player {
    int victoryPoints;
    List<Resource> resourceList;
    List<Assignment> assignmentCards;
    public Player(int victoryPoints, List<Resource> resourceList, List<Assignment> assignmentCards)
    {
        this.victoryPoints = victoryPoints;
        this.resourceList = resourceList;
        this.assignmentCards = assignmentCards;
    }
}
