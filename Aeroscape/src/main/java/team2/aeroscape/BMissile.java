/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package team2.aeroscape;

/**
 *
 * Ballistic Missile Class.
 * Type of missile used by enemy platforms
 */
public class BMissile extends Missile {
    BMissile(Tile pos, Building target) {
        super(pos);
        double az = getTargetHeading(target.x, target.y);
        this.alignVel2Heading(burnoutVel,az);
    }
}
