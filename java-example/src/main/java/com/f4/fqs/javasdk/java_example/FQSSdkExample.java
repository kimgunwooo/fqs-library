package com.f4.fqs.javasdk.java_example;

import com.f4.fqs.javaSdk.sdk.FQSSdk;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FQSSdkExample {

    private final FQSSdk fqsSdk;

    public FQSSdkExample(FQSSdk fqsSdk) {
        this.fqsSdk = fqsSdk;
    }

    /**
     * Add an item to the queue.
     *
     * @return The identifier of the added item.
     */
    public String addItemToQueue() {
        // TODO: Add your custom logic here (e.g., prepare data to be added)
        return fqsSdk.enqueue(); // Add item to the queue
    }

    /**
     * Consume items from the queue.
     *
     * @param size The number of items to consume.
     * @return A list of consumed items.
     */
    public List<String> consumeItemsFromQueue(Integer size) {
        // TODO: Add your custom logic here (e.g., validate the number of items to consume)
        return fqsSdk.dequeue(size); // Consume items from the queue
    }

    /**
     * Retrieve the rank of a specific item in the queue.
     *
     * @param identifier The unique identifier of the item.
     * @return The rank of the specified item.
     */
    public Long getQueueRank(UUID identifier) {
        // TODO: Add your custom logic here (e.g., check if the item exists)
        return fqsSdk.retrieveQueueRank(identifier); // Retrieve the rank of the specific item
    }
}
