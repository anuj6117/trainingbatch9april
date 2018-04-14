/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.preview.hostedNumbers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;
import com.twilio.base.Resource;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * PLEASE NOTE that this class contains preview products that are subject to
 * change. Use them with caution. If you currently do not have developer preview
 * access, please contact help@twilio.com.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorizationDocument extends Resource {
    private static final long serialVersionUID = 156014201571905L;

    public enum Status {
        OPENED("opened"),
        SIGNING("signing"),
        SIGNED("signed"),
        CANCELED("canceled"),
        FAILED("failed");

        private final String value;

        private Status(final String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }

        /**
         * Generate a Status from a string.
         * @param value string value
         * @return generated Status
         */
        @JsonCreator
        public static Status forValue(final String value) {
            return Promoter.enumFromString(value, Status.values());
        }
    }

    /**
     * Create a AuthorizationDocumentFetcher to execute fetch.
     * 
     * @param pathSid AuthorizationDocument sid.
     * @return AuthorizationDocumentFetcher capable of executing the fetch
     */
    public static AuthorizationDocumentFetcher fetcher(final String pathSid) {
        return new AuthorizationDocumentFetcher(pathSid);
    }

    /**
     * Create a AuthorizationDocumentUpdater to execute update.
     * 
     * @param pathSid The sid
     * @return AuthorizationDocumentUpdater capable of executing the update
     */
    public static AuthorizationDocumentUpdater updater(final String pathSid) {
        return new AuthorizationDocumentUpdater(pathSid);
    }

    /**
     * Create a AuthorizationDocumentReader to execute read.
     * 
     * @return AuthorizationDocumentReader capable of executing the read
     */
    public static AuthorizationDocumentReader reader() {
        return new AuthorizationDocumentReader();
    }

    /**
     * Create a AuthorizationDocumentCreator to execute create.
     * 
     * @param hostedNumberOrderSids A list of HostedNumberOrder sids.
     * @param addressSid Address sid.
     * @param email Email.
     * @return AuthorizationDocumentCreator capable of executing the create
     */
    public static AuthorizationDocumentCreator creator(final List<String> hostedNumberOrderSids, 
                                                       final String addressSid, 
                                                       final String email) {
        return new AuthorizationDocumentCreator(hostedNumberOrderSids, addressSid, email);
    }

    /**
     * Converts a JSON String into a AuthorizationDocument object using the provided
     * ObjectMapper.
     * 
     * @param json Raw JSON String
     * @param objectMapper Jackson ObjectMapper
     * @return AuthorizationDocument object represented by the provided JSON
     */
    public static AuthorizationDocument fromJson(final String json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, AuthorizationDocument.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON InputStream into a AuthorizationDocument object using the
     * provided ObjectMapper.
     * 
     * @param json Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return AuthorizationDocument object represented by the provided JSON
     */
    public static AuthorizationDocument fromJson(final InputStream json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, AuthorizationDocument.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    private final String sid;
    private final String addressSid;
    private final AuthorizationDocument.Status status;
    private final String email;
    private final List<String> ccEmails;
    private final DateTime dateCreated;
    private final DateTime dateUpdated;
    private final URI url;
    private final Map<String, String> links;

    @JsonCreator
    private AuthorizationDocument(@JsonProperty("sid")
                                  final String sid, 
                                  @JsonProperty("address_sid")
                                  final String addressSid, 
                                  @JsonProperty("status")
                                  final AuthorizationDocument.Status status, 
                                  @JsonProperty("email")
                                  final String email, 
                                  @JsonProperty("cc_emails")
                                  final List<String> ccEmails, 
                                  @JsonProperty("date_created")
                                  final String dateCreated, 
                                  @JsonProperty("date_updated")
                                  final String dateUpdated, 
                                  @JsonProperty("url")
                                  final URI url, 
                                  @JsonProperty("links")
                                  final Map<String, String> links) {
        this.sid = sid;
        this.addressSid = addressSid;
        this.status = status;
        this.email = email;
        this.ccEmails = ccEmails;
        this.dateCreated = DateConverter.iso8601DateTimeFromString(dateCreated);
        this.dateUpdated = DateConverter.iso8601DateTimeFromString(dateUpdated);
        this.url = url;
        this.links = links;
    }

    /**
     * Returns The AuthorizationDocument sid..
     * 
     * @return AuthorizationDocument sid.
     */
    public final String getSid() {
        return this.sid;
    }

    /**
     * Returns The Address sid..
     * 
     * @return Address sid.
     */
    public final String getAddressSid() {
        return this.addressSid;
    }

    /**
     * Returns The The Status of this AuthorizationDocument..
     * 
     * @return The Status of this AuthorizationDocument.
     */
    public final AuthorizationDocument.Status getStatus() {
        return this.status;
    }

    /**
     * Returns The Email..
     * 
     * @return Email.
     */
    public final String getEmail() {
        return this.email;
    }

    /**
     * Returns The A list of emails..
     * 
     * @return A list of emails.
     */
    public final List<String> getCcEmails() {
        return this.ccEmails;
    }

    /**
     * Returns The The date this AuthorizationDocument was created..
     * 
     * @return The date this AuthorizationDocument was created.
     */
    public final DateTime getDateCreated() {
        return this.dateCreated;
    }

    /**
     * Returns The The date this AuthorizationDocument was updated..
     * 
     * @return The date this AuthorizationDocument was updated.
     */
    public final DateTime getDateUpdated() {
        return this.dateUpdated;
    }

    /**
     * Returns The The url.
     * 
     * @return The url
     */
    public final URI getUrl() {
        return this.url;
    }

    /**
     * Returns The The links.
     * 
     * @return The links
     */
    public final Map<String, String> getLinks() {
        return this.links;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthorizationDocument other = (AuthorizationDocument) o;

        return Objects.equals(sid, other.sid) && 
               Objects.equals(addressSid, other.addressSid) && 
               Objects.equals(status, other.status) && 
               Objects.equals(email, other.email) && 
               Objects.equals(ccEmails, other.ccEmails) && 
               Objects.equals(dateCreated, other.dateCreated) && 
               Objects.equals(dateUpdated, other.dateUpdated) && 
               Objects.equals(url, other.url) && 
               Objects.equals(links, other.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sid,
                            addressSid,
                            status,
                            email,
                            ccEmails,
                            dateCreated,
                            dateUpdated,
                            url,
                            links);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("sid", sid)
                          .add("addressSid", addressSid)
                          .add("status", status)
                          .add("email", email)
                          .add("ccEmails", ccEmails)
                          .add("dateCreated", dateCreated)
                          .add("dateUpdated", dateUpdated)
                          .add("url", url)
                          .add("links", links)
                          .toString();
    }
}