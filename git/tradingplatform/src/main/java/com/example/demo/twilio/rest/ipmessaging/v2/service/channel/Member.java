/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.ipmessaging.v2.service.channel;

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
import java.net.URI;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Member extends Resource {
    private static final long serialVersionUID = 102574959310114L;

    /**
     * Create a MemberFetcher to execute fetch.
     * 
     * @param pathServiceSid The service_sid
     * @param pathChannelSid The channel_sid
     * @param pathSid The sid
     * @return MemberFetcher capable of executing the fetch
     */
    public static MemberFetcher fetcher(final String pathServiceSid, 
                                        final String pathChannelSid, 
                                        final String pathSid) {
        return new MemberFetcher(pathServiceSid, pathChannelSid, pathSid);
    }

    /**
     * Create a MemberCreator to execute create.
     * 
     * @param pathServiceSid The service_sid
     * @param pathChannelSid The channel_sid
     * @param identity The identity
     * @return MemberCreator capable of executing the create
     */
    public static MemberCreator creator(final String pathServiceSid, 
                                        final String pathChannelSid, 
                                        final String identity) {
        return new MemberCreator(pathServiceSid, pathChannelSid, identity);
    }

    /**
     * Create a MemberReader to execute read.
     * 
     * @param pathServiceSid The service_sid
     * @param pathChannelSid The channel_sid
     * @return MemberReader capable of executing the read
     */
    public static MemberReader reader(final String pathServiceSid, 
                                      final String pathChannelSid) {
        return new MemberReader(pathServiceSid, pathChannelSid);
    }

    /**
     * Create a MemberDeleter to execute delete.
     * 
     * @param pathServiceSid The service_sid
     * @param pathChannelSid The channel_sid
     * @param pathSid The sid
     * @return MemberDeleter capable of executing the delete
     */
    public static MemberDeleter deleter(final String pathServiceSid, 
                                        final String pathChannelSid, 
                                        final String pathSid) {
        return new MemberDeleter(pathServiceSid, pathChannelSid, pathSid);
    }

    /**
     * Create a MemberUpdater to execute update.
     * 
     * @param pathServiceSid The service_sid
     * @param pathChannelSid The channel_sid
     * @param pathSid The sid
     * @return MemberUpdater capable of executing the update
     */
    public static MemberUpdater updater(final String pathServiceSid, 
                                        final String pathChannelSid, 
                                        final String pathSid) {
        return new MemberUpdater(pathServiceSid, pathChannelSid, pathSid);
    }

    /**
     * Converts a JSON String into a Member object using the provided ObjectMapper.
     * 
     * @param json Raw JSON String
     * @param objectMapper Jackson ObjectMapper
     * @return Member object represented by the provided JSON
     */
    public static Member fromJson(final String json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, Member.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON InputStream into a Member object using the provided
     * ObjectMapper.
     * 
     * @param json Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return Member object represented by the provided JSON
     */
    public static Member fromJson(final InputStream json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, Member.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    private final String sid;
    private final String accountSid;
    private final String channelSid;
    private final String serviceSid;
    private final String identity;
    private final DateTime dateCreated;
    private final DateTime dateUpdated;
    private final String roleSid;
    private final Integer lastConsumedMessageIndex;
    private final DateTime lastConsumptionTimestamp;
    private final URI url;

    @JsonCreator
    private Member(@JsonProperty("sid")
                   final String sid, 
                   @JsonProperty("account_sid")
                   final String accountSid, 
                   @JsonProperty("channel_sid")
                   final String channelSid, 
                   @JsonProperty("service_sid")
                   final String serviceSid, 
                   @JsonProperty("identity")
                   final String identity, 
                   @JsonProperty("date_created")
                   final String dateCreated, 
                   @JsonProperty("date_updated")
                   final String dateUpdated, 
                   @JsonProperty("role_sid")
                   final String roleSid, 
                   @JsonProperty("last_consumed_message_index")
                   final Integer lastConsumedMessageIndex, 
                   @JsonProperty("last_consumption_timestamp")
                   final String lastConsumptionTimestamp, 
                   @JsonProperty("url")
                   final URI url) {
        this.sid = sid;
        this.accountSid = accountSid;
        this.channelSid = channelSid;
        this.serviceSid = serviceSid;
        this.identity = identity;
        this.dateCreated = DateConverter.iso8601DateTimeFromString(dateCreated);
        this.dateUpdated = DateConverter.iso8601DateTimeFromString(dateUpdated);
        this.roleSid = roleSid;
        this.lastConsumedMessageIndex = lastConsumedMessageIndex;
        this.lastConsumptionTimestamp = DateConverter.iso8601DateTimeFromString(lastConsumptionTimestamp);
        this.url = url;
    }

    /**
     * Returns The The sid.
     * 
     * @return The sid
     */
    public final String getSid() {
        return this.sid;
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
     * Returns The The channel_sid.
     * 
     * @return The channel_sid
     */
    public final String getChannelSid() {
        return this.channelSid;
    }

    /**
     * Returns The The service_sid.
     * 
     * @return The service_sid
     */
    public final String getServiceSid() {
        return this.serviceSid;
    }

    /**
     * Returns The The identity.
     * 
     * @return The identity
     */
    public final String getIdentity() {
        return this.identity;
    }

    /**
     * Returns The The date_created.
     * 
     * @return The date_created
     */
    public final DateTime getDateCreated() {
        return this.dateCreated;
    }

    /**
     * Returns The The date_updated.
     * 
     * @return The date_updated
     */
    public final DateTime getDateUpdated() {
        return this.dateUpdated;
    }

    /**
     * Returns The The role_sid.
     * 
     * @return The role_sid
     */
    public final String getRoleSid() {
        return this.roleSid;
    }

    /**
     * Returns The The last_consumed_message_index.
     * 
     * @return The last_consumed_message_index
     */
    public final Integer getLastConsumedMessageIndex() {
        return this.lastConsumedMessageIndex;
    }

    /**
     * Returns The The last_consumption_timestamp.
     * 
     * @return The last_consumption_timestamp
     */
    public final DateTime getLastConsumptionTimestamp() {
        return this.lastConsumptionTimestamp;
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

        Member other = (Member) o;

        return Objects.equals(sid, other.sid) && 
               Objects.equals(accountSid, other.accountSid) && 
               Objects.equals(channelSid, other.channelSid) && 
               Objects.equals(serviceSid, other.serviceSid) && 
               Objects.equals(identity, other.identity) && 
               Objects.equals(dateCreated, other.dateCreated) && 
               Objects.equals(dateUpdated, other.dateUpdated) && 
               Objects.equals(roleSid, other.roleSid) && 
               Objects.equals(lastConsumedMessageIndex, other.lastConsumedMessageIndex) && 
               Objects.equals(lastConsumptionTimestamp, other.lastConsumptionTimestamp) && 
               Objects.equals(url, other.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sid,
                            accountSid,
                            channelSid,
                            serviceSid,
                            identity,
                            dateCreated,
                            dateUpdated,
                            roleSid,
                            lastConsumedMessageIndex,
                            lastConsumptionTimestamp,
                            url);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("sid", sid)
                          .add("accountSid", accountSid)
                          .add("channelSid", channelSid)
                          .add("serviceSid", serviceSid)
                          .add("identity", identity)
                          .add("dateCreated", dateCreated)
                          .add("dateUpdated", dateUpdated)
                          .add("roleSid", roleSid)
                          .add("lastConsumedMessageIndex", lastConsumedMessageIndex)
                          .add("lastConsumptionTimestamp", lastConsumptionTimestamp)
                          .add("url", url)
                          .toString();
    }
}