package test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class ErrorResponse {

    private final String path;
    private final String error;
    private final Map<String, Object> details;
    private final String errorMessage;
    @Deprecated
    private final String developerMessage;
    @Deprecated
    private final String errorType;

    @JsonCreator
    @Builder(toBuilder = true)
    protected ErrorResponse(
        @JsonProperty("path") final String path,
        @JsonProperty("errorType") final String errorType,
        @JsonProperty("error") final String error,
        @JsonProperty("errorMessage") final String errorMessage,
        @JsonProperty("developerMessage") final String developerMessage,
        @JsonProperty("details") final Map<String, Object> details
    ) {
        this.path = path;
        this.errorMessage = errorMessage != null ? errorMessage : developerMessage;
        this.error = error != null ? error : errorType;
        this.details = details;
        this.developerMessage = errorMessage;
        this.errorType = error;
    }

    public Map<String, Object> getDetails() {
        return details == null ? Collections.emptyMap() : details;
    }

    public String getPath() {
        return path;
    }

    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    public Optional<String> getError() {
        return Optional.ofNullable(error);
    }

    /**
     *
     * @return
     * @deprecated Use 'getError'
     */
    @Deprecated
    public Optional<String> getErrorType() {
        return Optional.ofNullable(errorType);
    }

    /**
     *
     * @return
     * @deprecated Use 'getErrorMessage'
     */
    @Deprecated
    public Optional<String> getDeveloperMessage() {
        return Optional.ofNullable(developerMessage);
    }
}
