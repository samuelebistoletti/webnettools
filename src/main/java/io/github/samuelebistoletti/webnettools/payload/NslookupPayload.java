package io.github.samuelebistoletti.webnettools.payload;

import io.github.samuelebistoletti.webnettools.annotation.*;
import lombok.Data;

@Data
@Tool(name="nslookup", displayName="NSlookup", cmd="nslookup", description="Find name servers associated with domain")
public class NslookupPayload implements Payload {
    @MainParameter(displayName ="Domain", description="domain")
    private String domain;

    @Override
    public String getCacheString() {
        return domain;
    }
}