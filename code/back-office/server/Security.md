- Client application will be deployed on the same domain as the API Gateway, so no CORS issues are expected (Reduced risk of misconfigurations that could lead to security vulnerabilities but still vulnerable to Host Header Injection, JWT alongside would do the work).

- CORS specific configuration:

  - No domain will be allowed to access the API Gateway, except the "/ceni/app" for the mobile application (Only GET and POST methods are allowed).

- As Angular is deployed on the same server, direct navigation through the app is not possible due to spring security configuration.
