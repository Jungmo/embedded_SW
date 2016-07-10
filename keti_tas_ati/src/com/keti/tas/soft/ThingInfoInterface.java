/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keti.tas.soft;

/**
 *
 * @author ChenNan
 */
public interface ThingInfoInterface {
    String createRegistMsg();
    void checkRegistMsg(String msg);
    String getThingName();
    boolean getRegistStatus();
    boolean equals(ThingInfoInterface t);
}
