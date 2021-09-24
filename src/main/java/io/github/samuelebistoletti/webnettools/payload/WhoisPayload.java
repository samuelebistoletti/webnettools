package io.github.samuelebistoletti.webnettools.payload;

import io.github.samuelebistoletti.webnettools.annotation.*;
import lombok.Data;

@Data
@Tool(name="whois", displayName="Whois", cmd="whois", description="Client for the whois directory service")
public class WhoisPayload implements Payload {
    @MainParameter(displayName ="Domain", description="domain")
    private String domain;

    @Override
    public String getCacheString() {
        return domain;
    }
}