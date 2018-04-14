/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.sync.v1.service.synclist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;
import com.twilio.base.Resource;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.Objects;

/**
 * PLEASE NOTE that this class contains beta products that are subject to
 * change. Use them with caution.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SyncListItem extends Resource {
    private static final long serialVersionUID = 156990163506527L;

    public enum QueryResultOrder {
        ASC("asc"),
        DESC("desc");

        private final String value;

        private QueryResultOrder(final String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }

        /**
         * Generate a QueryResultOrder from a string.
         * @param value string value
         * @return generated QueryResultOrder
         */
        @JsonCreator
        public static QueryResultOrder forValue(final String value) {
            return Promoter.enumFromString(value, QueryResultOrder.values());
        }
    }

    public enum QueryFromBoundType {
        INCLUSIVE("inclusive"),
        EXCLUSIVE("exclusive");

        private final String value;

        private QueryFromBoundType(final String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }

        /**
         * Generate a QueryFromBoundType from a string.
         * @param value string value
         * @return generated QueryFromBoundType
         */
        @JsonCreator
        public static QueryFromBoundType forValue(final String value) {
            return Promoter.enumFromString(value, QueryFromBoundType.values());
        }
    }

    /**
     * Create a SyncListItemFetcher to execute fetch.
     * 
     * @param pathServiceSid The service_sid
     * @param pathListSid The list_sid
     * @param pathIndex The index
     * @return SyncListItemFetcher capable of executing the fetch
     */
    public static SyncListItemFetcher fetcher(final String pathServiceSid, 
                                              final String pathListSid, 
                                              final Integer pathIndex) {
        return new SyncListItemFetcher(pathServiceSid, pathListSid, pathIndex);
    }

    /**
     * Create a SyncListItemDeleter to execute delete.
     * 
     * @param pathServiceSid The service_sid
     * @param pathListSid The list_sid
     * @param pathIndex The index
     * @return SyncListItemDeleter capable of executing the delete
     */
    public static SyncListItemDeleter deleter(final String pathServiceSid, 
                                              final String pathListSid, 
                                              final Integer pathIndex) {
        return new SyncListItemDeleter(pathServiceSid, pathListSid, pathIndex);
    }

    /**
     * Create a SyncListItemCreator to execute create.
     * 
     * @param pathServiceSid The service_sid
     * @param pathListSid The list_sid
     * @param data The data
     * @return SyncListItemCreator capable of executing the create
     */
    public static SyncListItemCreator creator(final String pathServiceSid, 
                                              final String pathListSid, 
                                              final Map<String, Object> data) {
        return new SyncListItemCreator(pathServiceSid, pathListSid, data);
    }

    /**
     * Create a SyncListItemReader to execute read.
     * 
     * @param pathServiceSid The service_sid
     * @param pathListSid The list_sid
     * @return SyncListItemReader capable of executing the read
     */
    public static SyncListItemReader reader(final String pathServiceSid, 
                                            final String pathListSid) {
        return new SyncListItemReader(pathServiceSid, pathListSid);
    }

    /**
     * Create a SyncListItemUpdater to execute update.
     * 
     * @param pathServiceSid The service_sid
     * @param pathListSid The list_sid
     * @param pathIndex The index
     * @return SyncListItemUpdater capable of executing the update
     */
    public static SyncListItemUpdater updater(final String pathServiceSid, 
                                              final String pathListSid, 
                                              final Integer pathIndex) {
        return new SyncListItemUpdater(pathServiceSid, pathListSid, pathIndex);
    }

    /**
     * Converts a JSON String into a SyncListItem object using the provided
     * ObjectMapper.
     * 
     * @param json Raw JSON String
     * @param objectMapper Jackson ObjectMapper
     * @return SyncListItem object represented by the provided JSON
     */
    public static SyncListItem fromJson(final String json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, SyncListItem.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON InputStream into a SyncListItem object using the provided
     * ObjectMapper.
     * 
     * @param json Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return SyncListItem object represented by the provided JSON
     */
    public static SyncListItem fromJson(final InputStream json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, SyncListItem.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    private final Integer index;
    private final String accountSid;
    private final String serviceSid;
    private final String listSid;
    private final URI url;
    private final String revision;
    private final Map<String, Object> data;
    private final DateTime dateExpires;
    private final DateTime dateCreated;
    private final DateTime dateUpdated;
    private final String createdBy;

    @JsonCreator
    private SyncListItem(@JsonProperty("index")
                         final Integer index, 
                         @JsonProperty("account_sid")
                         final String accountSid, 
                         @JsonProperty("service_sid")
                         final String serviceSid, 
                         @JsonProperty("list_sid")
                         final String listSid, 
                         @JsonProperty("url")
                         final URI url, 
                         @JsonProperty("revision")
                         final String revision, 
                         @JsonProperty("data")
                         final Map<String, Object> data, 
                         @JsonProperty("date_expires")
                         final String dateExpires, 
                         @JsonProperty("date_created")
                         final String dateCreated, 
                         @JsonProperty("date_updated")
                         final String dateUpdated, 
                         @JsonProperty("created_by")
                         final String createdBy) {
        this.index = index;
        this.accountSid = accountSid;
        this.serviceSid = serviceSid;
        this.listSid = listSid;
        this.url = url;
        this.revision = revision;
        this.data = data;
        this.dateExpires = DateConverter.iso8601DateTimeFromString(dateExpires);
        this.dateCreated = DateConverter.iso8601DateTimeFromString(dateCreated);
        this.dateUpdated = DateConverter.iso8601DateTimeFromString(dateUpdated);
        this.createdBy = createdBy;
    }

    /**
     * Returns The The index.
     * 
     * @return The index
     */
    public final Integer getIndex() {
        return this.index;
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
     * Returns The The list_sid.
     * 
     * @return The list_sid
     */
    public final String getListSid() {
        return this.listSid;
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
     * Returns The The revision.
     * 
     * @return The revision
     */
    public final String getRevision() {
        return this.revision;
    }

    /**
     * Returns The The data.
     * 
     * @return The data
     */
    public final Map<String, Object> getData() {
        return this.data;
    }

    /**
     * Returns The The date_expires.
     * 
     * @return The date_expires
     */
    public final DateTime getDateExpires() {
        return this.dateExpires;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SyncListItem other = (SyncListItem) o;

        return Objects.equals(index, other.index) && 
               Objects.equals(accountSid, other.accountSid) && 
               Objects.equals(serviceSid, other.serviceSid) && 
               Objects.equals(listSid, other.listSid) && 
               Objects.equals(url, other.url) && 
               Objects.equals(revision, other.revision) && 
               Objects.equals(data, other.data) && 
               Objects.equals(dateExpires, other.dateExpires) && 
               Objects.equals(dateCreated, other.dateCreated) && 
               Objects.equals(dateUpdated, other.dateUpdated) && 
               Objects.equals(createdBy, other.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index,
                            accountSid,
                            serviceSid,
                            listSid,
                            url,
                            revision,
                            data,
                            dateExpires,
                            dateCreated,
                            dateUpdated,
                            createdBy);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("index", index)
                          .add("accountSid", accountSid)
                          .add("serviceSid", serviceSid)
                          .add("listSid", listSid)
                          .add("url", url)
                          .add("revision", revision)
                          .add("data", data)
                          .add("dateExpires", dateExpires)
                          .add("dateCreated", dateCreated)
                          .add("dateUpdated", dateUpdated)
                          .add("createdBy", createdBy)
                          .toString();
    }
}