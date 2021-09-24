package io.github.samuelebistoletti.webnettools.payload;

import io.github.samuelebistoletti.webnettools.annotation.FixedParam;
import io.github.samuelebistoletti.webnettools.annotation.MainParameter;
import io.github.samuelebistoletti.webnettools.annotation.ParameterType;
import io.github.samuelebistoletti.webnettools.annotation.Tool;
import lombok.Data;

@Data
@Tool(name="mtr", displayName="MTR", cmd="mtr", description="Network diagnostic tool combining 'traceroute' and 'ping'")
public class MtrPayload implements Payload {
    @MainParameter(displayName ="Hostname")
    private String hostname;

    @FixedParam(param = "--report", paramType = ParameterType.ONLY_PARAM)
    private Boolean report = true;

    @Override
    public String getCacheString() {
        return hostname;
    }
}