package io.github.samuelebistoletti.webnettools.payload;

import io.github.samuelebistoletti.webnettools.annotation.*;
import lombok.Data;

@Data
@Tool(name="ping", displayName="Ping", cmd="ping", description="Standard Linux Ping tooling.")
public class PingPayload implements Payload {
    @MainParameter(displayName ="IP / Hostname", description="ping ip")
    private String host;

    @NumberParam(displayName ="Count", param="-c", min=1., max=500., step=1., description="Stop after sending (and receiving) count ECHO_RESPONSE packets. If this option is not specified, ping will operate until interrupted.  If this option is specified in conjunction with ping sweeps, each sweep will consist of count packets.")
    private Integer count = 3;
    @NumberParam(displayName ="Wait", param="-i", min=0.2, max=100., step=1., description="Time in seconds to wait for a reply for each packet sent. If a reply arrives later, the packet is not printed as replied, but considered as replied when calculating statistics.")
    private Double wait = 1.;
    @NumberParam(displayName ="Size", param="-s", min=1., max=10000., step=1., description="Number of data bytes to be sent")
    private Integer size = 56;
    @BooleanParam(displayName = "No dns name resolution", param = "-n", paramType = ParameterType.ONLY_PARAM,description = "No dns name resolution")
    private Boolean numericOutputOnly;
    @NumberParam(displayName ="Deadline", param="-w", min=1., max=100., step=1., description="Specify a timeout, in seconds, before ping exits regardless of how many packets have been sent or received. In this case ping does not stop after count packet are sent, it waits either for deadline expire or until count probes are answered or for some error notification from network.")
    private Integer deadline;
    @NumberParam(displayName ="Timeout", param="-W", min=1., max=100., step=1., description="Time to wait for a response, in seconds. The option affects only timeout in absense of any responses, otherwise ping waits for two RTTs.")
    private Integer timeout;

    @Override
    public String getCacheString() {
        return host;
    }
}