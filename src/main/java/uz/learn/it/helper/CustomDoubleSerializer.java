package uz.learn.it.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

public class CustomDoubleSerializer extends JsonSerializer<Double> {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeString(df.format(value)); // Convert double to string with two decimal places
        }
    }
}
