# consul-client-app

## Consul

I am using a docker container to run consul (https://hub.docker.com/r/progrium/consul/)

### Start Consul Agent

To start the container, just run:

```
docker pull progrium/consul
```

then:

```
docker run -p 8400:8400 -p 8500:8500 -p 8600:53/udp -h node1 progrium/consul -server -bootstrap -ui-dir /ui
```

Once the container is up and running, open a browser pointing to the following URL:

```
http://localhost:8500/ui/
```

To test the KV store, go to KV tab and create the following KV structure:

config/consul-client-app,dev/server.port=9090
config/consul-client-app,dev/message=application message (dev)
config/consul-client-app/server.port=9091
config/consul-client-app/message=application message
config/application,dev/message=default message (dev)
config/application/message=default message


## Spring App

To configure Spring to use Consul Configuration, there are a couple of steps to follow.

First add the corresponding dependency:

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-consul-config</artifactId>
</dependency>
```

Then create a bootstrap.yml under src/main/resources with the following content:

```
spring:
  application:
    name: consul-client-app
```

Finally add the following annotations to your main class:

```
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RefreshScope
public class ConsulClientAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsulClientAppApplication.class, args);
    }

    @Value("${message:default}")
    private String message;

    @RequestMapping(value = "/message", method = GET)
    public String message() {
        return message;
    }
}
```

## Testing the app.

If you run the application activating the dev profile, the application should be up and running in the port 9090. You can test if the configuration works consuming the endpoint:

```
curl -X GET "http://localhost:9090/message"
```

if no profile is active, the application should be listening on port 9091.
