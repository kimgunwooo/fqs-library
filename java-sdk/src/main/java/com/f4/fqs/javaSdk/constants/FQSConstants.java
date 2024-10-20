package com.f4.fqs.javaSdk.constants;

public interface FQSConstants {

  String SECRET_KEY = "X-Secret-Key";
  String FQS_SERVER_URL = "http://localhost:19095";

  // HEADER TYPE
  String CONTENT_TYPE = "Content-Type";
  String APPLICATION_JSON = "application/json";

  // ENDPOINT
  String VALIDATE_ENDPOINT = "/api/queue/validate";
  String QUEUE_MANAGE_ENDPOINT = "queue";
  String ENQUEUE_ENDPOINT = "/add";
  String DEQUEUE_ENDPOINT = "/consume";
  String RETRIEVE_QUEUE_ENDPOINT = "/ranks";

  // HTTP METHOD
  String POST = "POST";
  String GET = "GET";

  // PARAM
  String QUEUE_NAME = "queueName";
  String SIZE = "size";
  String IDENTIFIER = "identifier";
}
