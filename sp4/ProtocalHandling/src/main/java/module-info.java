module ProtocalHandling {
    requires Common;
    requires spring.boot.starter.webflux;
    requires spring.boot.starter.web;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.webflux;
    requires spring.core;
    requires io.netty.handler;
    requires java.net.http;
    requires io.netty.transport;
    requires reactor.netty.http;
    requires reactor.netty.core;
    requires org.junit.jupiter.api;
    exports services;
}