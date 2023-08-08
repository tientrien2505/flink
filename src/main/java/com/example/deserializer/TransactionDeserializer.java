package com.example.deserializer;

import com.example.model.Transaction;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.flink.util.Collector;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class TransactionDeserializer implements DeserializationSchema<Transaction> {
    private ObjectMapper mapper;

    @Override
    public void open(InitializationContext context) throws Exception {
        DeserializationSchema.super.open(context);
        this.mapper = new ObjectMapper();
    }

    @Override
    public Transaction deserialize(byte[] bytes) throws IOException {
        String strMessage = new String(bytes, StandardCharsets.UTF_8);
        JsonNode jsonMessage = mapper.readTree(strMessage);
        JsonNode after = jsonMessage.get("payload").get("after");
        long id = after.get("id").asLong();
        String customer = after.get("customer").asText();
        LocalDateTime tranAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(after.get("tran_at").asLong()), TimeZone.getDefault().toZoneId());
        String tranType = after.get("tran_type").asText();
        double amount = after.get("amount").asDouble();
        return new Transaction(id, customer, tranAt, tranType, amount);
    }

    @Override
    public void deserialize(byte[] message, Collector<Transaction> out) throws IOException {
        DeserializationSchema.super.deserialize(message, out);
    }

    @Override
    public boolean isEndOfStream(Transaction transaction) {
        return false;
    }

    @Override
    public TypeInformation<Transaction> getProducedType() {
        return TypeInformation.of(Transaction.class);
    }
}
