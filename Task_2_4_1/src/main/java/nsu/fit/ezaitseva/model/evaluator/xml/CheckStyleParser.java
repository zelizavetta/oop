package nsu.fit.ezaitseva.model.evaluator.xml;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;


/**
 * The type Check style parser.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
//@JacksonXmlRootElement(localName = "name")
public class CheckStyleParser {
    private CheckStyleParser() {
    }

    private static final XmlMapper xmlMapper
            = new XmlMapper();
    @Getter
    @JacksonXmlProperty(localName = "version")
    private String version;
    @JacksonXmlProperty(localName = "error")
    @JacksonXmlElementWrapper(useWrapping = false)

    private List<error> errorList;

    /**
     * Parse check style results.
     *
     * @param reportXml the report xml
     * @return the check style results
     * @throws IOException the io exception
     */
    public static CheckStyleResults parse(File reportXml) throws IOException {
        return xmlMapper.readValue(
                reportXml,
                CheckStyleResults.class);
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    public Collection<error> getAll() {
        return errorList;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public int getAmount() {
        if (errorList == null) {
            return 0;
        }
        return errorList.size();
    }

    /**
     * The type Check style results.
     */
    public record CheckStyleResults(
            @JsonProperty("version") String version,
            @JacksonXmlProperty(localName = "file")
            @JacksonXmlElementWrapper(useWrapping = false)
            List<file> files
    ) {
        /**
         * Gets warnings amount.
         *
         * @return the warnings amount
         */
        public int getWarningsAmount() {
            if (files == null) {
                return 0;
            }
            return files.stream()
                    .map(file::getErrorAmount)
                    .reduce(0, Integer::sum);
        }

    }

    /**
     * The type File.
     */
    public record file(
            @JsonProperty("name") String name,
            @JacksonXmlProperty(localName = "error")
            @JacksonXmlElementWrapper(useWrapping = false)
            List<error> errors

    ) {
        /**
         * Gets error amount.
         *
         * @return the error amount
         */
        public int getErrorAmount() {
            if (errors == null) {
                return 0;
            }
            return errors.size();
        }

    }

    /**
     * The type Error.
     */
    public record error(
            @JsonProperty("line") int line,
            @JsonProperty("column") int column,
            @JsonProperty("severity") String severity,
            @JsonProperty("message") String message,
            @JsonProperty("source") String source
    ) {
    }
}