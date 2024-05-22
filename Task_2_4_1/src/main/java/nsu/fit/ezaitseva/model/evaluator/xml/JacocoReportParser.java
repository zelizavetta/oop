package nsu.fit.ezaitseva.model.evaluator.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "name")
public class JacocoReportParser {
    private JacocoReportParser() {
    }

    private static final XmlMapper xmlMapper
            = new XmlMapper();
    @Getter
    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "counter")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Counter> counterList;

    public static JacocoReportParser parse(File reportXml) throws IOException {
        return xmlMapper.readValue(
                reportXml,
                JacocoReportParser.class);
    }

    public Optional<Counter> getCounterByType(CounterType counterType) {
        return counterList.stream()
                .filter(counter -> counter.type == counterType)
                .findFirst();
    }

    public Optional<Double> getCoverageByType(CounterType counterType) {
        return getCounterByType(counterType)
                .map(counter ->
                        (double) counter.covered / (counter.covered + counter.missed)
                );
    }

    public enum CounterType {
        INSTRUCTION,
        BRANCH,
        LINE,
        COMPLEXITY,
        METHOD,
        CLASS
    }

    public record Counter(
            @JsonProperty("type") CounterType type,
            @JsonProperty("missed") int missed,
            @JsonProperty("covered") int covered) {
    }
}