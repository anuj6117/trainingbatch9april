/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.proxy.v1.service;

import com.twilio.base.Updater;
import com.twilio.converter.Converter;
import com.twilio.converter.DateConverter;
import com.twilio.converter.Promoter;
import com.twilio.exception.ApiConnectionException;
import com.twilio.exception.ApiException;
import com.twilio.exception.RestException;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import com.twilio.http.Response;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.Domains;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

/**
 * PLEASE NOTE that this class contains beta products that are subject to
 * change. Use them with caution.
 */
public class SessionUpdater extends Updater<Session> {
    private final String pathServiceSid;
    private final String pathSid;
    private String uniqueName;
    private DateTime dateExpiry;
    private Integer ttl;
    private Session.Mode mode;
    private Session.Status status;
    private List<Map<String, Object>> participants;

    /**
     * Construct a new SessionUpdater.
     * 
     * @param pathServiceSid Service Sid.
     * @param pathSid A string that uniquely identifies this Session.
     */
    public SessionUpdater(final String pathServiceSid, 
                          final String pathSid) {
        this.pathServiceSid = pathServiceSid;
        this.pathSid = pathSid;
    }

    /**
     * Provides a unique and addressable name to be assigned to this Session,
     * assigned by the developer, to be optionally used in addition to SID..
     * 
     * @param uniqueName A unique, developer assigned name of this Session.
     * @return this
     */
    public SessionUpdater setUniqueName(final String uniqueName) {
        this.uniqueName = uniqueName;
        return this;
    }

    /**
     * The date that this Session was expiry, given in ISO 8601 format..
     * 
     * @param dateExpiry The date this Session was expiry
     * @return this
     */
    public SessionUpdater setDateExpiry(final DateTime dateExpiry) {
        this.dateExpiry = dateExpiry;
        return this;
    }

    /**
     * The Time to Live for a Session, in seconds..
     * 
     * @param ttl TTL for a Session, in seconds.
     * @return this
     */
    public SessionUpdater setTtl(final Integer ttl) {
        this.ttl = ttl;
        return this;
    }

    /**
     * The Mode of this Session. One of `message-only`, `voice-only` or
     * `voice-and-message`..
     * 
     * @param mode The Mode of this Session
     * @return this
     */
    public SessionUpdater setMode(final Session.Mode mode) {
        this.mode = mode;
        return this;
    }

    /**
     * The Status of this Session. One of `in-progress`, `closed`, `failed`,
     * `unknown` or `completed`..
     * 
     * @param status The Status of this Session
     * @return this
     */
    public SessionUpdater setStatus(final Session.Status status) {
        this.status = status;
        return this;
    }

    /**
     * A list of phone numbers to add to this Session..
     * 
     * @param participants A list of phone numbers to add to this Session.
     * @return this
     */
    public SessionUpdater setParticipants(final List<Map<String, Object>> participants) {
        this.participants = participants;
        return this;
    }

    /**
     * A list of phone numbers to add to this Session..
     * 
     * @param participants A list of phone numbers to add to this Session.
     * @return this
     */
    public SessionUpdater setParticipants(final Map<String, Object> participants) {
        return setParticipants(Promoter.listOfOne(participants));
    }

    /**
     * Make the request to the Twilio API to perform the update.
     * 
     * @param client TwilioRestClient with which to make the request
     * @return Updated Session
     */
    @Override
    @SuppressWarnings("checkstyle:linelength")
    public Session update(final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.POST,
            Domains.PROXY.toString(),
            "/v1/Services/" + this.pathServiceSid + "/Sessions/" + this.pathSid + "",
            client.getRegion()
        );

        addPostParams(request);
        Response response = client.request(request);

        if (response == null) {
            throw new ApiConnectionException("Session update failed: Unable to connect to server");
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

        return Session.fromJson(response.getStream(), client.getObjectMapper());
    }

    /**
     * Add the requested post parameters to the Request.
     * 
     * @param request Request to add post params to
     */
    private void addPostParams(final Request request) {
        if (uniqueName != null) {
            request.addPostParam("UniqueName", uniqueName);
        }

        if (dateExpiry != null) {
            request.addPostParam("DateExpiry", dateExpiry.toString());
        }

        if (ttl != null) {
            request.addPostParam("Ttl", ttl.toString());
        }

        if (mode != null) {
            request.addPostParam("Mode", mode.toString());
        }

        if (status != null) {
            request.addPostParam("Status", status.toString());
        }

        if (participants != null) {
            for (Map<String, Object> prop : participants) {
                request.addPostParam("Participants", Converter.mapToJson(prop));
            }
        }
    }
}