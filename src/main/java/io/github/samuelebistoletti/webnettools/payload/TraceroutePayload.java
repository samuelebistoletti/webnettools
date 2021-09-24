package io.github.samuelebistoletti.webnettools.payload;

import io.github.samuelebistoletti.webnettools.annotation.MainParameter;
import io.github.samuelebistoletti.webnettools.annotation.Tool;
import lombok.Data;

@Data
@Tool(name="traceroute", displayName="Trace Route", cmd="traceroute", description="Standard Linux traceroute tooling.")
public class TraceroutePayload implements Payload {
    @MainParameter(displayName ="IP / Hostname", description="trace route hostname")
    private String host;

    @Override
    public String getCacheString() {
        return host;
    }
}