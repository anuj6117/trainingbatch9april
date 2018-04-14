/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.api.v2010.account.recording.addonresult;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;
import com.twilio.base.Resource;
import com.twilio.converter.DateConverter;
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
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload extends Resource {
    private static final long serialVersionUID = 217671696000636L;

    /**
     * Create a PayloadFetcher to execute fetch.
     * 
     * @param pathAccountSid The account_sid
     * @param pathReferenceSid The reference_sid
     * @param pathAddOnResultSid The add_on_result_sid
     * @param pathSid Fetch by unique payload Sid
     * @return PayloadFetcher capable of executing the fetch
     */
    public static PayloadFetcher fetcher(final String pathAccountSid, 
                                         final String pathReferenceSid, 
                                         final String pathAddOnResultSid, 
                                         final String pathSid) {
        return new PayloadFetcher(pathAccountSid, pathReferenceSid, pathAddOnResultSid, pathSid);
    }

    /**
     * Create a PayloadFetcher to execute fetch.
     * 
     * @param pathReferenceSid The reference_sid
     * @param pathAddOnResultSid The add_on_result_sid
     * @param pathSid Fetch by unique payload Sid
     * @return PayloadFetcher capable of executing the fetch
     */
    public static PayloadFetcher fetcher(final String pathReferenceSid, 
                                         final String pathAddOnResultSid, 
                                         final String pathSid) {
        return new PayloadFetcher(pathReferenceSid, pathAddOnResultSid, pathSid);
    }

    /**
     * Create a PayloadReader to execute read.
     * 
     * @param pathAccountSid The account_sid
     * @param pathReferenceSid The reference_sid
     * @param pathAddOnResultSid The add_on_result_sid
     * @return PayloadReader capable of executing the read
     */
    public static PayloadReader reader(final String pathAccountSid, 
                                       final String pathReferenceSid, 
                                       final String pathAddOnResultSid) {
        return new PayloadReader(pathAccountSid, pathReferenceSid, pathAddOnResultSid);
    }

    /**
     * Create a PayloadReader to execute read.
     * 
     * @param pathReferenceSid The reference_sid
     * @param pathAddOnResultSid The add_on_result_sid
     * @return PayloadReader capable of executing the read
     */
    public static PayloadReader reader(final String pathReferenceSid, 
                                       final String pathAddOnResultSid) {
        return new PayloadReader(pathReferenceSid, pathAddOnResultSid);
    }

    /**
     * Create a PayloadDeleter to execute delete.
     * 
     * @param pathAccountSid The account_sid
     * @param pathReferenceSid The reference_sid
     * @param pathAddOnResultSid The add_on_result_sid
     * @param pathSid Delete by unique payload Sid
     * @return PayloadDeleter capable of executing the delete
     */
    public static PayloadDeleter deleter(final String pathAccountSid, 
                                         final String pathReferenceSid, 
                                         final String pathAddOnResultSid, 
                                         final String pathSid) {
        return new PayloadDeleter(pathAccountSid, pathReferenceSid, pathAddOnResultSid, pathSid);
    }

    /**
     * Create a PayloadDeleter to execute delete.
     * 
     * @param pathReferenceSid The reference_sid
     * @param pathAddOnResultSid The add_on_result_sid
     * @param pathSid Delete by unique payload Sid
     * @return PayloadDeleter capable of executing the delete
     */
    public static PayloadDeleter deleter(final String pathReferenceSid, 
                                         final String pathAddOnResultSid, 
                                         final String pathSid) {
        return new PayloadDeleter(pathReferenceSid, pathAddOnResultSid, pathSid);
    }

    /**
     * Converts a JSON String into a Payload object using the provided ObjectMapper.
     * 
     * @param json Raw JSON String
     * @param objectMapper Jackson ObjectMapper
     * @return Payload object represented by the provided JSON
     */
    public static Payload fromJson(final String json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, Payload.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON InputStream into a Payload object using the provided
     * ObjectMapper.
     * 
     * @param json Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return Payload object represented by the provided JSON
     */
    public static Payload fromJson(final InputStream json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, Payload.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    private final String sid;
    private final String addOnResultSid;
    private final String accountSid;
    private final String label;
    private final String addOnSid;
    private final String addOnConfigurationSid;
    private final String contentType;
    private final DateTime dateCreated;
    private final DateTime dateUpdated;
    private final String referenceSid;
    private final Map<String, String> subresourceUris;

    @JsonCreator
    private Payload(@JsonProperty("sid")
                    final String sid, 
                    @JsonProperty("add_on_result_sid")
                    final String addOnResultSid, 
                    @JsonProperty("account_sid")
                    final String accountSid, 
                    @JsonProperty("label")
                    final String label, 
                    @JsonProperty("add_on_sid")
                    final String addOnSid, 
                    @JsonProperty("add_on_configuration_sid")
                    final String addOnConfigurationSid, 
                    @JsonProperty("content_type")
                    final String contentType, 
                    @JsonProperty("date_created")
                    final String dateCreated, 
                    @JsonProperty("date_updated")
                    final String dateUpdated, 
                    @JsonProperty("reference_sid")
                    final String referenceSid, 
                    @JsonProperty("subresource_uris")
                    final Map<String, String> subresourceUris) {
        this.sid = sid;
        this.addOnResultSid = addOnResultSid;
        this.accountSid = accountSid;
        this.label = label;
        this.addOnSid = addOnSid;
        this.addOnConfigurationSid = addOnConfigurationSid;
        this.contentType = contentType;
        this.dateCreated = DateConverter.rfc2822DateTimeFromString(dateCreated);
        this.dateUpdated = DateConverter.rfc2822DateTimeFromString(dateUpdated);
        this.referenceSid = referenceSid;
        this.subresourceUris = subresourceUris;
    }

    /**
     * Returns The A string that uniquely identifies this payload.
     * 
     * @return A string that uniquely identifies this payload
     */
    public final String getSid() {
        return this.sid;
    }

    /**
     * Returns The A string that uniquely identifies the result.
     * 
     * @return A string that uniquely identifies the result
     */
    public final String getAddOnResultSid() {
        return this.addOnResultSid;
    }

    /**
     * Returns The The unique sid that identifies this account.
     * 
     * @return The unique sid that identifies this account
     */
    public final String getAccountSid() {
        return this.accountSid;
    }

    /**
     * Returns The A string that describes the payload..
     * 
     * @return A string that describes the payload.
     */
    public final String getLabel() {
        return this.label;
    }

    /**
     * Returns The A string that uniquely identifies the Add-on..
     * 
     * @return A string that uniquely identifies the Add-on.
     */
    public final String getAddOnSid() {
        return this.addOnSid;
    }

    /**
     * Returns The A string that uniquely identifies the Add-on configuration..
     * 
     * @return A string that uniquely identifies the Add-on configuration.
     */
    public final String getAddOnConfigurationSid() {
        return this.addOnConfigurationSid;
    }

    /**
     * Returns The The MIME type of the payload..
     * 
     * @return The MIME type of the payload.
     */
    public final String getContentType() {
        return this.contentType;
    }

    /**
     * Returns The The date this resource was created.
     * 
     * @return The date this resource was created
     */
    public final DateTime getDateCreated() {
        return this.dateCreated;
    }

    /**
     * Returns The The date this resource was last updated.
     * 
     * @return The date this resource was last updated
     */
    public final DateTime getDateUpdated() {
        return this.dateUpdated;
    }

    /**
     * Returns The A string that uniquely identifies the recording..
     * 
     * @return A string that uniquely identifies the recording.
     */
    public final String getReferenceSid() {
        return this.referenceSid;
    }

    /**
     * Returns The The subresource_uris.
     * 
     * @return The subresource_uris
     */
    public final Map<String, String> getSubresourceUris() {
        return this.subresourceUris;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Payload other = (Payload) o;

        return Objects.equals(sid, other.sid) && 
               Objects.equals(addOnResultSid, other.addOnResultSid) && 
               Objects.equals(accountSid, other.accountSid) && 
               Objects.equals(label, other.label) && 
               Objects.equals(addOnSid, other.addOnSid) && 
               Objects.equals(addOnConfigurationSid, other.addOnConfigurationSid) && 
               Objects.equals(contentType, other.contentType) && 
               Objects.equals(dateCreated, other.dateCreated) && 
               Objects.equals(dateUpdated, other.dateUpdated) && 
               Objects.equals(referenceSid, other.referenceSid) && 
               Objects.equals(subresourceUris, other.subresourceUris);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sid,
                            addOnResultSid,
                            accountSid,
                            label,
                            addOnSid,
                            addOnConfigurationSid,
                            contentType,
                            dateCreated,
                            dateUpdated,
                            referenceSid,
                            subresourceUris);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("sid", sid)
                          .add("addOnResultSid", addOnResultSid)
                          .add("accountSid", accountSid)
                          .add("label", label)
                          .add("addOnSid", addOnSid)
                          .add("addOnConfigurationSid", addOnConfigurationSid)
                          .add("contentType", contentType)
                          .add("dateCreated", dateCreated)
                          .add("dateUpdated", dateUpdated)
                          .add("referenceSid", referenceSid)
                          .add("subresourceUris", subresourceUris)
                          .toString();
    }
}