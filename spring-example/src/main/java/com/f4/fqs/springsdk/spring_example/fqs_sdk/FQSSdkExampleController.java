package com.f4.fqs.springsdk.spring_example.fqs_sdk;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/queue")
public class FQSSdkExampleController {

    private final FQSSdkExample fqsSdkExample;

    public FQSSdkExampleController(FQSSdkExample fqsSdkExample) {
        this.fqsSdkExample = fqsSdkExample;
    }

    /**
     * Add an item to the queue.
     *
     * @return The identifier of the added item.
     */
    @PostMapping("/add")
    public ResponseEntity<String> addItemToQueue() {
        String identifier = fqsSdkExample.addItemToQueue();
        return ResponseEntity.ok(identifier);
    }

    /**
     * Consume items from the queue.
     *
     * @param size The number of items to consume.
     * @return A list of consumed items.
     */
    @GetMapping("/consume")
    public ResponseEntity<List<String>> consumeItemsFromQueue(@RequestParam Integer size) {
        List<String> consumedItems = fqsSdkExample.consumeItemsFromQueue(size);
        return ResponseEntity.ok(consumedItems);
    }

    /**
     * Retrieve the rank of a specific item in the queue.
     *
     * @param identifier The unique identifier of the item.
     * @return The rank of the specified item.
     */
    @GetMapping("/rank/{identifier}")
    public ResponseEntity<Long> getQueueRank(@PathVariable UUID identifier) {
        Long rank = fqsSdkExample.getQueueRank(identifier);
        return ResponseEntity.ok(rank);
    }
}

