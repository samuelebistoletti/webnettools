package io.github.samuelebistoletti.webnettools.payload;

import io.github.samuelebistoletti.webnettools.annotation.*;
import lombok.Data;

@Data
@Tool(name="host", displayName="Host", cmd="host", description="Find the IP address of a particular domain name")
public class HostPayload implements Payload {
    @MainParameter(displayName ="Hostname", description="host hostname")
    private String host;

    @Override
    public String getCacheString() {
        return host;
    }
}