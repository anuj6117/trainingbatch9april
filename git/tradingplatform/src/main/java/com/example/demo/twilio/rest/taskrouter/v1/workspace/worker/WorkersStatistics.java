/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.taskrouter.v1.workspace.worker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;
import com.twilio.base.Resource;
import com.twilio.converter.Converter;
import com.twilio.exception.ApiConnectionException;
import com.twilio.exception.ApiException;
import com.twilio.exception.RestException;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import com.twilio.http.Response;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.Domains;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkersStatistics extends Resource {
    private static final long serialVersionUID = 181724864721054L;

    /**
     * Create a WorkersStatisticsFetcher to execute fetch.
     * 
     * @param pathWorkspaceSid The workspace_sid
     * @return WorkersStatisticsFetcher capable of executing the fetch
     */
    public static WorkersStatisticsFetcher fetcher(final String pathWorkspaceSid) {
        return new WorkersStatisticsFetcher(pathWorkspaceSid);
    }

    /**
     * Converts a JSON String into a WorkersStatistics object using the provided
     * ObjectMapper.
     * 
     * @param json Raw JSON String
     * @param objectMapper Jackson ObjectMapper
     * @return WorkersStatistics object represented by the provided JSON
     */
    public static WorkersStatistics fromJson(final String json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, WorkersStatistics.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON InputStream into a WorkersStatistics object using the
     * provided ObjectMapper.
     * 
     * @param json Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return WorkersStatistics object represented by the provided JSON
     */
    public static WorkersStatistics fromJson(final InputStream json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, WorkersStatistics.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    private final Map<String, Object> realtime;
    private final Map<String, Object> cumulative;
    private final String accountSid;
    private final String workspaceSid;
    private final URI url;

    @JsonCreator
    private WorkersStatistics(@JsonProperty("realtime")
                              final Map<String, Object> realtime, 
                              @JsonProperty("cumulative")
                              final Map<String, Object> cumulative, 
                              @JsonProperty("account_sid")
                              final String accountSid, 
                              @JsonProperty("workspace_sid")
                              final String workspaceSid, 
                              @JsonProperty("url")
                              final URI url) {
        this.realtime = realtime;
        this.cumulative = cumulative;
        this.accountSid = accountSid;
        this.workspaceSid = workspaceSid;
        this.url = url;
    }

    /**
     * Returns The The realtime.
     * 
     * @return The realtime
     */
    public final Map<String, Object> getRealtime() {
        return this.realtime;
    }

    /**
     * Returns The The cumulative.
     * 
     * @return The cumulative
     */
    public final Map<String, Object> getCumulative() {
        return this.cumulative;
    }

    /**
     * Returns The The account_sid.
     * 
     * @return The account_sid
     */
    public final String getAccountSid() {
        return this.accountSid;
    }

    /**
     * Returns The The workspace_sid.
     * 
     * @return The workspace_sid
     */
    public final String getWorkspaceSid() {
        return this.workspaceSid;
    }

    /**
     * Returns The The url.
     * 
     * @return The url
     */
    public final URI getUrl() {
        return this.url;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkersStatistics other = (WorkersStatistics) o;

        return Objects.equals(realtime, other.realtime) && 
               Objects.equals(cumulative, other.cumulative) && 
               Objects.equals(accountSid, other.accountSid) && 
               Objects.equals(workspaceSid, other.workspaceSid) && 
               Objects.equals(url, other.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(realtime,
                            cumulative,
                            accountSid,
                            workspaceSid,
                            url);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("realtime", realtime)
                          .add("cumulative", cumulative)
                          .add("accountSid", accountSid)
                          .add("workspaceSid", workspaceSid)
                          .add("url", url)
                          .toString();
    }
}