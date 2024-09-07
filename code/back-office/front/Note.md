# CanActivate vs CanActivateChild usage

- Both may be used in the same route configuration. but they differ in how they are applied and executed.

## Order of execution

Documentation:[https://v17.angular.io/guide/router-tutorial-toh#milestone-5-route-guards]

- `CanActivate` is executed before `CanActivateChild` when first mouting navigation (page refresh or not related route navigation).
- only `CanActivateChild` is executed when navigating between child routes (Experimented).

# To test

- same user multiple device (to see how it works, specifically the refresh token)
