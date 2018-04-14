/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.ipmessaging.v2.service;

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
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Channel extends Resource {
    private static final long serialVersionUID = 275623534063843L;

    public enum ChannelType {
        PUBLIC("public"),
        PRIVATE("private");

        private final String value;

        private ChannelType(final String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }

        /**
         * Generate a ChannelType from a string.
         * @param value string value
         * @return generated ChannelType
         */
        @JsonCreator
        public static ChannelType forValue(final String value) {
            return Promoter.enumFromString(value, ChannelType.values());
        }
    }

    /**
     * Create a ChannelFetcher to execute fetch.
     * 
     * @param pathServiceSid The service_sid
     * @param pathSid The sid
     * @return ChannelFetcher capable of executing the fetch
     */
    public static ChannelFetcher fetcher(final String pathServiceSid, 
                                         final String pathSid) {
        return new ChannelFetcher(pathServiceSid, pathSid);
    }

    /**
     * Create a ChannelDeleter to execute delete.
     * 
     * @param pathServiceSid The service_sid
     * @param pathSid The sid
     * @return ChannelDeleter capable of executing the delete
     */
    public static ChannelDeleter deleter(final String pathServiceSid, 
                                         final String pathSid) {
        return new ChannelDeleter(pathServiceSid, pathSid);
    }

    /**
     * Create a ChannelCreator to execute create.
     * 
     * @param pathServiceSid The service_sid
     * @return ChannelCreator capable of executing the create
     */
    public static ChannelCreator creator(final String pathServiceSid) {
        return new ChannelCreator(pathServiceSid);
    }

    /**
     * Create a ChannelReader to execute read.
     * 
     * @param pathServiceSid The service_sid
     * @return ChannelReader capable of executing the read
     */
    public static ChannelReader reader(final String pathServiceSid) {
        return new ChannelReader(pathServiceSid);
    }

    /**
     * Create a ChannelUpdater to execute update.
     * 
     * @param pathServiceSid The service_sid
     * @param pathSid The sid
     * @return ChannelUpdater capable of executing the update
     */
    public static ChannelUpdater updater(final String pathServiceSid, 
                                         final String pathSid) {
        return new ChannelUpdater(pathServiceSid, pathSid);
    }

    /**
     * Converts a JSON String into a Channel object using the provided ObjectMapper.
     * 
     * @param json Raw JSON String
     * @param objectMapper Jackson ObjectMapper
     * @return Channel object represented by the provided JSON
     */
    public static Channel fromJson(final String json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, Channel.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON InputStream into a Channel object using the provided
     * ObjectMapper.
     * 
     * @param json Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return Channel object represented by the provided JSON
     */
    public static Channel fromJson(final InputStream json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, Channel.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    private final String sid;
    private final String accountSid;
    private final String serviceSid;
    private final String friendlyName;
    private final String uniqueName;
    private final String attributes;
    private final Channel.ChannelType type;
    private final DateTime dateCreated;
    private final DateTime dateUpdated;
    private final String createdBy;
    private final Integer membersCount;
    private final Integer messagesCount;
    private final URI url;
    private final Map<String, String> links;

    @JsonCreator
    private Channel(@JsonProperty("sid")
                    final String sid, 
                    @JsonProperty("account_sid")
                    final String accountSid, 
                    @JsonProperty("service_sid")
                    final String serviceSid, 
                    @JsonProperty("friendly_name")
                    final String friendlyName, 
                    @JsonProperty("unique_name")
                    final String uniqueName, 
                    @JsonProperty("attributes")
                    final String attributes, 
                    @JsonProperty("type")
                    final Channel.ChannelType type, 
                    @JsonProperty("date_created")
                    final String dateCreated, 
                    @JsonProperty("date_updated")
                    final String dateUpdated, 
                    @JsonProperty("created_by")
                    final String createdBy, 
                    @JsonProperty("members_count")
                    final Integer membersCount, 
                    @JsonProperty("messages_count")
                    final Integer messagesCount, 
                    @JsonProperty("url")
                    final URI url, 
                    @JsonProperty("links")
                    final Map<String, String> links) {
        this.sid = sid;
        this.accountSid = accountSid;
        this.serviceSid = serviceSid;
        this.friendlyName = friendlyName;
        this.uniqueName = uniqueName;
        this.attributes = attributes;
        this.type = type;
        this.dateCreated = DateConverter.iso8601DateTimeFromString(dateCreated);
        this.dateUpdated = DateConverter.iso8601DateTimeFromString(dateUpdated);
        this.createdBy = createdBy;
        this.membersCount = membersCount;
        this.messagesCount = messagesCount;
        this.url = url;
        this.links = links;
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
     * Returns The The service_sid.
     * 
     * @return The service_sid
     */
    public final String getServiceSid() {
        return this.serviceSid;
    }

    /**
     * Returns The The friendly_name.
     * 
     * @return The friendly_name
     */
    public final String getFriendlyName() {
        return this.friendlyName;
    }

    /**
     * Returns The The unique_name.
     * 
     * @return The unique_name
     */
    public final String getUniqueName() {
        return this.uniqueName;
    }

    /**
     * Returns The The attributes.
     * 
     * @return The attributes
     */
    public final String getAttributes() {
        return this.attributes;
    }

    /**
     * Returns The The type.
     * 
     * @return The type
     */
    public final Channel.ChannelType getType() {
        return this.type;
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
     * Returns The The created_by.
     * 
     * @return The created_by
     */
    public final String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * Returns The The members_count.
     * 
     * @return The members_count
     */
    public final Integer getMembersCount() {
        return this.membersCount;
    }

    /**
     * Returns The The messages_count.
     * 
     * @return The messages_count
     */
    public final Integer getMessagesCount() {
        return this.messagesCount;
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

        Channel other = (Channel) o;

        return Objects.equals(sid, other.sid) && 
               Objects.equals(accountSid, other.accountSid) && 
               Objects.equals(serviceSid, other.serviceSid) && 
               Objects.equals(friendlyName, other.friendlyName) && 
               Objects.equals(uniqueName, other.uniqueName) && 
               Objects.equals(attributes, other.attributes) && 
               Objects.equals(type, other.type) && 
               Objects.equals(dateCreated, other.dateCreated) && 
               Objects.equals(dateUpdated, other.dateUpdated) && 
               Objects.equals(createdBy, other.createdBy) && 
               Objects.equals(membersCount, other.membersCount) && 
               Objects.equals(messagesCount, other.messagesCount) && 
               Objects.equals(url, other.url) && 
               Objects.equals(links, other.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sid,
                            accountSid,
                            serviceSid,
                            friendlyName,
                            uniqueName,
                            attributes,
                            type,
                            dateCreated,
                            dateUpdated,
                            createdBy,
                            membersCount,
                            messagesCount,
                            url,
                            links);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("sid", sid)
                          .add("accountSid", accountSid)
                          .add("serviceSid", serviceSid)
                          .add("friendlyName", friendlyName)
                          .add("uniqueName", uniqueName)
                          .add("attributes", attributes)
                          .add("type", type)
                          .add("dateCreated", dateCreated)
                          .add("dateUpdated", dateUpdated)
                          .add("createdBy", createdBy)
                          .add("membersCount", membersCount)
                          .add("messagesCount", messagesCount)
                          .add("url", url)
                          .add("links", links)
                          .toString();
    }
}