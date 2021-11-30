package com.example.capstone.models.cards;

import com.example.capstone.models.resources.Brick;
import com.example.capstone.models.resources.Resource;
import com.example.capstone.models.resources.Wood;

public class InstructionGive extends Assignment
{

    public InstructionGive(int direction, Resource resource)
    {
        this.direction=direction;
        //this.initialize(resource);
    }
}
