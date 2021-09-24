package io.github.samuelebistoletti.webnettools.filter.ratelimit;

import io.github.samuelebistoletti.webnettools.payload.Payload;
import io.vertx.core.http.HttpServerRequest;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Provider
public class RateLimitFilter implements ContainerRequestFilter {
    @Inject
    @RateLimitCacheQualifier
    Map<String, Boolean> rateLimitCache;

    @Inject
    Jsonb jsonb;

    @Context
    private UriInfo info;

    @Context
    private HttpServerRequest request;

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        try {
            if (!info.getPath().startsWith("/tools/")) {
                return;
            }
            // check if client exceeded rate limit
            if (isOverRateLimit(context)) {
                context.abortWith(Response.status(429).entity("Too Many Requests").build());
            }
        } catch (Exception e) {
            context.abortWith(Response.status(500).entity(e.getMessage()).build());
        }
    }

    private boolean isOverRateLimit(ContainerRequestContext context) {
        String content = "";
        content += info.getPath();
        content += " ";
        content += request.remoteAddress().host();
        content += " ";
        content += getCacheValue(context);

        if(rateLimitCache.containsKey(content)) {
            return true;
        }

        rateLimitCache.put(content, true);
        return false;
    }

    private String getCacheValue(ContainerRequestContext request) {
        try {
            String json = IOUtils.toString(request.getEntityStream(), StandardCharsets.UTF_8);
            InputStream in = IOUtils.toInputStream(json, StandardCharsets.UTF_8);
            request.setEntityStream(in);

            Payload payload = jsonb.fromJson(json, Payload.class);
            return payload.getCacheString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}