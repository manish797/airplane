package com.squareShift.airplaneSeatingplan.Exceptions;

public class WrongSeatingConfigurationException  extends AirplaneException{

	
	private static final long serialVersionUID = 969448800912744223L;

	public WrongSeatingConfigurationException(String errorMessage) {
		    super(errorMessage);
	}

}
