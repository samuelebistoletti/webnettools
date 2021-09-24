package io.github.samuelebistoletti.webnettools;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.reflections.Reflections;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import java.util.List;

public class Producer {
    @ConfigProperty(name = "AVAILABLE_TOOLS", defaultValue = "ping,traceroute,mtr,nmap,whois,nslookup,dig,host")
    List<String> availableTools;

    @Singleton
    @Produces
    public Reflections produceReflections() {
        return new Reflections("io.github.samuelebistoletti.webnettools");
    }


    @Produces
    @Singleton
    @AvailableToolsQualifier
    public List<String> getAvailableTools() {
        return availableTools;
    }

}