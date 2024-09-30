export class Endpoints {
  public static readonly SIGN_IN = 'auth/sign-in';
  public static readonly SIGN_OUT = 'auth/sign-out';
  public static readonly REFRESH_TOKEN = 'auth/refresh-token';

  public static readonly USERS = 'api/users';
  public static readonly ROLES = 'api/roles';

  public static readonly ADMINISTRATIVE_DIVISION =
    'api/administrative-divisions';
  public static readonly POLLING_STATIONS = 'api/polling-stations';

  public static readonly ELECTIONS = 'api/elections';
  public static readonly ELECTION_RESULTS = `${this.ELECTIONS}/results`;

  public static publicEndpoints = [
    Endpoints.SIGN_IN,
    Endpoints.SIGN_OUT,
    Endpoints.REFRESH_TOKEN,
  ];
}
