package com.rear_admirals.york_pirates.Attacks;

public class Attacks {

	public Attack attackMain = new Attack();
	public Attack attackFlee = new Flee();
	public Attack attackGrape = new GrapeShot();
	public Attack attackRam = new Ram();
	public Attack attackSwivel = new Swivel();

	public Attacks() {}

	public static final Attacks list = new Attacks();

}
