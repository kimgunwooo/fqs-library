package com.f4.fqs.javaSdk.sdk;

import java.util.List;
import java.util.UUID;

/**
 * FQSSdk provides a set of functionalities to interact with a queue system.
 * This interface allows users to add items to the queue, consume items from the queue,
 * and retrieve the rank of specific items within the queue.
 *
 * Implementations of this interface should handle the underlying logic for
 * communicating with the queue service, including error handling and response processing.
 */
public interface FQSSdk {

    /**
     * Adds an item to the queue.
     *
     * @return The identifier of the added item.
     */
    String enqueue();

    /**
     * Consumes items from the queue.
     *
     * @param size The number of items to consume. If null, the default value will be used.
     * @return A list of consumed items.
     */
    List<String> dequeue(Integer size);

    /**
     * Retrieves the rank of a specific item in the queue.
     *
     * @param identifier The unique identifier of the item whose rank is to be retrieved.
     * @return The rank of the specified item.
     */
    Long retrieveQueueRank(UUID identifier);
}