/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.preview.wireless.sim;

import com.twilio.base.Fetcher;
import com.twilio.exception.ApiConnectionException;
import com.twilio.exception.ApiException;
import com.twilio.exception.RestException;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import com.twilio.http.Response;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.Domains;

/**
 * PLEASE NOTE that this class contains preview products that are subject to
 * change. Use them with caution. If you currently do not have developer preview
 * access, please contact help@twilio.com.
 */
public class UsageFetcher extends Fetcher<Usage> {
    private final String pathSimSid;
    private String end;
    private String start;

    /**
     * Construct a new UsageFetcher.
     * 
     * @param pathSimSid The sim_sid
     */
    public UsageFetcher(final String pathSimSid) {
        this.pathSimSid = pathSimSid;
    }

    /**
     * The end.
     * 
     * @param end The end
     * @return this
     */
    public UsageFetcher setEnd(final String end) {
        this.end = end;
        return this;
    }

    /**
     * The start.
     * 
     * @param start The start
     * @return this
     */
    public UsageFetcher setStart(final String start) {
        this.start = start;
        return this;
    }

    /**
     * Make the request to the Twilio API to perform the fetch.
     * 
     * @param client TwilioRestClient with which to make the request
     * @return Fetched Usage
     */
    @Override
    @SuppressWarnings("checkstyle:linelength")
    public Usage fetch(final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.GET,
            Domains.PREVIEW.toString(),
            "/wireless/Sims/" + this.pathSimSid + "/Usage",
            client.getRegion()
        );

        addQueryParams(request);
        Response response = client.request(request);

        if (response == null) {
            throw new ApiConnectionException("Usage fetch failed: Unable to connect to server");
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

        return Usage.fromJson(response.getStream(), client.getObjectMapper());
    }

    /**
     * Add the requested query string arguments to the Request.
     * 
     * @param request Request to add query string arguments to
     */
    private void addQueryParams(final Request request) {
        if (end != null) {
            request.addQueryParam("End", end);
        }

        if (start != null) {
            request.addQueryParam("Start", start);
        }
    }
}