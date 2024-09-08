## CORS problem

- The preflight request is not succeeding. Changed the spring boot version to 3.3.1 solved the issue. Still do not understand why it was not working with other version (even newer version)

=> Put the CORS configuration directly inside the security filter chain. Found out that the order of configuration matters, no configuration in the security filter chain means that the default configuration will be used.
