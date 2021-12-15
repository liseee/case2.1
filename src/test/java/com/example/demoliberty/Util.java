package com.example.demoliberty;

import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;

public final class Util {

    private Util() { }

    public static File[] pomDependency(String groupId, String artifactId) {
        return Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve(groupId + ":" + artifactId)
                .withTransitivity()
                .asFile();
    }
}
