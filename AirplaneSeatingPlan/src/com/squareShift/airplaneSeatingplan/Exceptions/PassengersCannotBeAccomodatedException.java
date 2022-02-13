package com.squareShift.airplaneSeatingplan.Exceptions;

public class PassengersCannotBeAccomodatedException  extends AirplaneException{
	
	
	
	private static final long serialVersionUID = -6374359475340168547L;


	PassengersCannotBeAccomodatedException(){}
	
	
	public PassengersCannotBeAccomodatedException( String message){
		super(message);
		
		
	}
}
