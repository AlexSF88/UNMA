package ru.al.model.parameters;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Alex on 19.12.2015.
 */
public class Skill extends Property {
    private int price;
    private String currency;

    private HashSet<Class> targetClasses;
    private HashMap<String, Integer> effects;
}
