/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.taskrouter.v1.workspace;

import com.twilio.base.Fetcher;
import com.twilio.exception.ApiConnectionException;
import com.twilio.exception.ApiException;
import com.twilio.exception.RestException;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import com.twilio.http.Response;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.Domains;

public class ActivityFetcher extends Fetcher<Activity> {
    private final String pathWorkspaceSid;
    private final String pathSid;

    /**
     * Construct a new ActivityFetcher.
     * 
     * @param pathWorkspaceSid The workspace_sid
     * @param pathSid The sid
     */
    public ActivityFetcher(final String pathWorkspaceSid, 
                           final String pathSid) {
        this.pathWorkspaceSid = pathWorkspaceSid;
        this.pathSid = pathSid;
    }

    /**
     * Make the request to the Twilio API to perform the fetch.
     * 
     * @param client TwilioRestClient with which to make the request
     * @return Fetched Activity
     */
    @Override
    @SuppressWarnings("checkstyle:linelength")
    public Activity fetch(final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.GET,
            Domains.TASKROUTER.toString(),
            "/v1/Workspaces/" + this.pathWorkspaceSid + "/Activities/" + this.pathSid + "",
            client.getRegion()
        );

        Response response = client.request(request);

        if (response == null) {
            throw new ApiConnectionException("Activity fetch failed: Unable to connect to server");
        } else if (!TwilioRestClient.SUCCESS.apply(response.getStatusCode())) {
            RestException restException = RestException.fromJson(response.getStream(), client.getObjectMapper());
            if (restException == null) {
                throw new ApiException("Server Error, no content");
            }

            throw new ApiException(
                restException.getMessage(),
                restException.getCode(),
                restException.getMoreInfo(),
                restException.getStatus(),
                null
            );
        }

        return Activity.fromJson(response.getStream(), client.getObjectMapper());
    }
}