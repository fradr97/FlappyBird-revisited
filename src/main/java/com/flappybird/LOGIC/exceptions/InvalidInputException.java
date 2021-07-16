package com.flappybird.LOGIC.exceptions;



public class InvalidInputException extends Exception {
    
    public InvalidInputException () {
       
        super("Invalid input");
    }
    
    public InvalidInputException (String pMessage){
        
        super(pMessage);
    }
}