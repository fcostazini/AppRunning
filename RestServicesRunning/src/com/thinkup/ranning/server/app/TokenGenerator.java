package com.thinkup.ranning.server.app;

import java.math.BigInteger;
import java.security.SecureRandom;

public class TokenGenerator {

	private static  TokenGenerator instance;
	private SecureRandom random;

	private TokenGenerator(){
		this.random = new SecureRandom();
	}
	
	public static TokenGenerator getInstance(){
		if(instance== null){
			instance = new TokenGenerator();
		}
		return instance;
	}
	public String nextTokenId() {
		return new BigInteger(100, random).toString(32);
	}

}
