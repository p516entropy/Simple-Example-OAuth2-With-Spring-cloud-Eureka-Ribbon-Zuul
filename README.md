Run all applications:
- service-reg (first in order!) (8761 port)
- auth-server (9090 port)
- resource-server (can run several instances for different ports, will be load balancing)
- web-server (7070 port)

Go to http://localhost:7070/resource/swagger-ui.html