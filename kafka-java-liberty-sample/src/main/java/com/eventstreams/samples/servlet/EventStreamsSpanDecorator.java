package com.eventstreams.samples.servlet;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;

import io.opentracing.Span;
import io.opentracing.contrib.kafka.SpanDecorator;

// SpanDecorator to insert the tags required for external apps tracing using the Operations Dashboard
public class EventStreamsSpanDecorator implements SpanDecorator {

  // Operations Dashboard requires a tag "externalAppType"
  private static final String EXTERNAL_APP_TYPE_TAG = "externalAppType";
  private final String externalAppTypeTag;

  // Also generate a second tag "businessId" to use in the Operations Dashbaord filters
  private static final String BUSINESS_ID_TAG = "businessId";
  private final String businessIdTag;

  public EventStreamsSpanDecorator(final String externalAppTypeTag, final String businessIdTag) {
   this.externalAppTypeTag = externalAppTypeTag;
   this.businessIdTag = businessIdTag;
  }

  @Override
  public <K, V> void onSend(final ProducerRecord<K, V> record, final Span span) {
    span.setTag(EXTERNAL_APP_TYPE_TAG, externalAppTypeTag);
    span.setTag(BUSINESS_ID_TAG, businessIdTag);
  }

  @Override
  public <K, V> void onResponse(final ConsumerRecord<K, V> record, final Span span) {
    span.setTag(EXTERNAL_APP_TYPE_TAG, externalAppTypeTag);
    span.setTag(BUSINESS_ID_TAG, businessIdTag);
  }

  @Override
  public void onError(final Exception exception, final Span span) {
    // Do nothing
  }
}
