package com.awssns;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.InvalidParameterException;
/**
 * 
 * AWS SNS FETCH
 *
 */
public class AWSSNSOperationsFetch {

	public static String fecthMessages() {
		AmazonSNS snsClient = AmazonSNSClientBuilder.standard()
		        .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
		        .withRegion(Regions.US_EAST_1)
		        .build();
		String endpointArn = null;
		try {
			String token = null;
			System.out.println("Creating platform endpoint with token " + token);
			String applicationArn = null;
			CreatePlatformEndpointRequest cpeReq =
					new CreatePlatformEndpointRequest()
					.withPlatformApplicationArn(applicationArn)
					.withToken(token);
			CreatePlatformEndpointResult cpeRes =snsClient.createPlatformEndpoint(cpeReq);
			endpointArn = cpeRes.getEndpointArn();
		} catch (InvalidParameterException ipe) {
			String message = ipe.getErrorMessage();
			System.out.println("Exception message: " + message);
			Pattern p = Pattern
					.compile(".*Endpoint (arn:aws:sns[^ ]+) already exists " +
							"with the same [Tt]oken.*");
			Matcher m = p.matcher(message);
			if (m.matches()) {
				endpointArn = m.group(1);
			} else {
				throw ipe;
			}
		}
		storeEndpointArn(endpointArn);
		return endpointArn;
	}

	/**
	 * @return the ARN the app was registered under previously, or null if no
	 *         platform endpoint ARN is stored.
	 */
	private String retrieveEndpointArn() {
		String arnStorage = null;
		// Retrieve the platform endpoint ARN from permanent storage,
		// or return null if null is stored.
		return arnStorage;
	}

	/**
	 * Stores the platform endpoint ARN in permanent storage for lookup next time.
	 * */
	private static void storeEndpointArn(String endpointArn) {

		String arnStorage = endpointArn;
		
		System.out.println("arnStorage "+arnStorage);
	}
}