module Server {
    requires Common;
    requires spring.web;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    opens sp4;
    opens java.lang;
}